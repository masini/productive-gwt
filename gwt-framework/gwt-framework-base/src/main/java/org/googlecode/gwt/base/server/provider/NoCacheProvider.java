package org.googlecode.gwt.base.server.provider;


import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.jaxrs.Annotations;
import org.codehaus.jackson.jaxrs.MapperConfigurator;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.type.ClassKey;
import org.codehaus.jackson.map.util.JSONPObject;
import org.codehaus.jackson.type.JavaType;

import javax.ws.rs.core.*;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.Providers;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashSet;

@Provider
public class NoCacheProvider implements MessageBodyWriter<Object> {

    public final static HashSet<ClassKey> _untouchables = new HashSet<ClassKey>();
    static {
        // First, I/O things (direct matches)
        _untouchables.add(new ClassKey(java.io.InputStream.class));
        _untouchables.add(new ClassKey(java.io.Reader.class));
        _untouchables.add(new ClassKey(OutputStream.class));
        _untouchables.add(new ClassKey(Writer.class));

        // then some primitive types
        _untouchables.add(new ClassKey(byte[].class));
        _untouchables.add(new ClassKey(char[].class));
        // 24-Apr-2009, tatu: String is an edge case... let's leave it out
        _untouchables.add(new ClassKey(String.class));

        // Then core JAX-RS things
        _untouchables.add(new ClassKey(StreamingOutput.class));
        _untouchables.add(new ClassKey(Response.class));
    }
    public final static Class<?>[] _unwritableClasses = new Class<?>[] {
            OutputStream.class, Writer.class,
            StreamingOutput.class, Response.class
    };
    public final static Annotations[] BASIC_ANNOTATIONS = {
            Annotations.JACKSON
    };



    protected final MapperConfigurator _mapperConfig;
    @Context
    protected Providers _providers;
    protected String _jsonpFunctionName;

    public NoCacheProvider(ObjectMapper mapper, Annotations[] annotationsToUse)
    {
        _mapperConfig = new MapperConfigurator(mapper, annotationsToUse);
    }

    public NoCacheProvider()
    {
        this(null, BASIC_ANNOTATIONS);
    }


    @Override
    public long getSize(Object o, Class<?> rawType, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    /**
     * Method that JAX-RS container calls to try to check whether
     * given value (of specified type) can be serialized by
     * this provider.
     * Implementation will first check that expected media type is
     * a JSON type (via call to {@link #isJsonType}; then verify
     * that type is not one of "untouchable" types (types we will never
     * automatically handle), and finally that there is a serializer
     * for type (iff {@link #checkCanSerialize} has been called with
     * true argument -- otherwise assumption is there will be a handler)
     */
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
    {
        if (!isJsonType(mediaType)) {
            return false;
        }

        /* Ok: looks like we must weed out some core types here; ones that
         * make no sense to try to bind from JSON:
         */
        if (_untouchables.contains(new ClassKey(type))) {
            return false;
        }
        // but some are interface/abstract classes, so
        for (Class<?> cls : _unwritableClasses) {
            if (cls.isAssignableFrom(type)) {
                return false;
            }
        }
        return true;
    }

    protected boolean isJsonType(MediaType mediaType)
    {
        /* As suggested by Stephen D, there are 2 ways to check: either
         * being as inclusive as possible (if subtype is "json"), or
         * exclusive (major type "application", minor type "json").
         * Let's start with inclusive one, hard to know which major
         * types we should cover aside from "application".
         */
        if (mediaType != null) {
            // Ok: there are also "xxx+json" subtypes, which count as well
            String subtype = mediaType.getSubtype();
            return "json".equalsIgnoreCase(subtype) || subtype.endsWith("+json");
        }
        /* Not sure if this can happen; but it seems reasonable
         * that we can at least produce json without media type?
         */
        return true;
    }

    public ObjectMapper locateMapper(Class<?> type, MediaType mediaType)
    {
        // First: were we configured with a specific instance?
        ObjectMapper m = _mapperConfig.getConfiguredMapper();
        if (m == null) {
            // If not, maybe we can get one configured via context?
            if (_providers != null) {
                ContextResolver<ObjectMapper> resolver = _providers.getContextResolver(ObjectMapper.class, mediaType);
                /* Above should work as is, but due to this bug
                 *   [https://jersey.dev.java.net/issues/show_bug.cgi?id=288]
                 * in Jersey, it doesn't. But this works until resolution of
                 * the issue:
                 */
                if (resolver == null) {
                    resolver = _providers.getContextResolver(ObjectMapper.class, null);
                }
                if (resolver != null) {
                    m = resolver.getContext(type);
                }
            }
            if (m == null) {
                // If not, let's get the fallback default instance
                m = _mapperConfig.getDefaultMapper();
            }
        }
        return m;
    }

    @Override
    public void writeTo(Object value, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException {
        NoCache noCache = FindAnnotation.findAnnotation(type, annotations, NoCache.class);

        if ( noCache!=null ) {
            httpHeaders.putSingle(HttpHeaders.CACHE_CONTROL, initCacheControl(noCache));
        }

        /* 27-Feb-2009, tatu: Where can we find desired encoding? Within
         *   HTTP headers?
         */
        ObjectMapper mapper = locateMapper(type, mediaType);
        JsonEncoding enc = JsonEncoding.UTF8;
        JsonGenerator jg = mapper.getJsonFactory().createJsonGenerator(entityStream, enc);
        jg.disable(JsonGenerator.Feature.AUTO_CLOSE_TARGET);

        // Want indentation?
        if (mapper.getSerializationConfig().isEnabled(SerializationConfig.Feature.INDENT_OUTPUT)) {
            jg.useDefaultPrettyPrinter();
        }
        // 04-Mar-2010, tatu: How about type we were given? (if any)
        JavaType rootType = null;

        if (genericType != null && value != null) {
            /* 10-Jan-2011, tatu: as per [JACKSON-456], it's not safe to just force root
             *    type since it prevents polymorphic type serialization. Since we really
             *    just need this for generics, let's only use generic type if it's truly
             *    generic.
             */
            if (genericType.getClass() != Class.class) { // generic types are other impls of 'java.lang.reflect.Type'
                /* This is still not exactly right; should root type be further
                 * specialized with 'value.getClass()'? Let's see how well this works before
                 * trying to come up with more complete solution.
                 */
                rootType = mapper.getTypeFactory().constructType(genericType);
                /* 26-Feb-2011, tatu: To help with [JACKSON-518], we better recognize cases where
                 *    type degenerates back into "Object.class" (as is the case with plain TypeVariable,
                 *    for example), and not use that.
                 */
                if (rootType.getRawClass() == Object.class) {
                    rootType = null;
                }
            }
        }
        // [JACKSON-245] Allow automatic JSONP wrapping
        if (_jsonpFunctionName != null) {
            mapper.writeValue(jg, new JSONPObject(_jsonpFunctionName, value, rootType));
        } else if (rootType != null) {
            mapper.typedWriter(rootType).writeValue(jg, value);
        } else {
            mapper.writeValue(jg, value);
        }
    }

    private CacheControl initCacheControl(NoCache value)
    {
        CacheControl cacheControl = new CacheControl();
        cacheControl.setNoCache(true);
        cacheControl.setNoTransform(false);
        for (String field : value.fields()) cacheControl.getNoCacheFields().add(field);

        return cacheControl;
    }

}