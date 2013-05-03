package org.googlecode.gwt.base.server.provider;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 *
 * COPIATA SPUDORATAMENTE DA RESTEASY !!!!!
 *
 */
@SuppressWarnings("unchecked")
public final class FindAnnotation
{

    /**
     *
     */
    private static final Class<? extends Annotation>[] JAXRS_ANNOTATIONS =
            (Class<? extends Annotation>[]) new Class[]{
                    QueryParam.class,
                    HeaderParam.class,
                    CookieParam.class,
                    PathParam.class,
                    MatrixParam.class,
                    Context.class
            };

    private static final Class[] findJaxRSAnnotations_TYPE = new Class[]{};


    private FindAnnotation()
    {
    }

    /**
     * FIXME Comment this
     *
     * @param <T>
     * @param searchList
     * @param annotation
     * @return
     */
    public static <T> T findAnnotation(Annotation[] searchList, Class<T> annotation)
    {
        if (searchList == null) return null;
        for (Annotation ann : searchList)
        {
            if (ann.annotationType().equals(annotation))
            {
                return (T) ann;
            }
        }
        return null;
    }

    /**
     * Look for an annotation in a list of annotations.  If not there, see if it is on the type provided
     *
     * @param type
     * @param annotations
     * @return
     */
    public static <T extends Annotation> T findAnnotation(Class<?> type, Annotation[] annotations, Class<T> annotation)
    {
        T config = FindAnnotation.findAnnotation(annotations, annotation);
        if (config == null)
        {
            config = type.getAnnotation(annotation);
        }
        return config;
    }


}
