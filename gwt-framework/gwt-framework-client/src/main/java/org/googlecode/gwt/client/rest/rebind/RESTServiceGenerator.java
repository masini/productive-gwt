package org.googlecode.gwt.client.rest.rebind;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.ext.*;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.http.client.*;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;
import freemarker.template.*;
import org.googlecode.gwt.client.rest.GwtApplicationPath;
import org.googlecode.gwt.client.rest.GwtPath;
import org.googlecode.gwt.client.rest.RESTCallback;

import javax.ws.rs.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;
import java.util.logging.Logger;

public class RESTServiceGenerator extends Generator {

    private static final String METHODS_KEY = "methods";

    private static final String SERVICE_QUALIFIED_CLASS_NAME = "serviceQualifiedClassName";
    private static final String SERVICE_CLASS_NAME = "serviceClassName";

    private static final String TEMPLATE_NAME = "restServiceTemplate.ftl";
    private static final String CLASS_SUFFIX = "Proxy";

    /*
    static {
        try {
            freemarker.log.Logger.selectLoggerLibrary(freemarker.log.Logger.LIBRARY_COMMONS);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }
    */

    @Override
    public String generate(TreeLogger logger, GeneratorContext context,
                           String typeName) throws UnableToCompleteException {

        try {

            TypeOracle typeOracle = context.getTypeOracle();
            JClassType requestedClass = typeOracle.getType(typeName);

            String packageName = requestedClass.getPackage().getName();
            String wrapperClassName = requestedClass.getSimpleSourceName()
                    + CLASS_SUFFIX;
            PrintWriter printWriter = context.tryCreate(logger, packageName,
                    wrapperClassName);
            String qualifiedWrapperClassName = packageName + "."
                    + wrapperClassName;

            if (printWriter != null) {
                Map<String, Object> rootMap = createRootMap(requestedClass, logger, context.getPropertyOracle());
                logger.log(Type.DEBUG, rootMap.toString());
                Writer generatedClassSource = processTemplate(rootMap);
                writeSource(getSourceWriter(packageName, wrapperClassName,
                        typeName, printWriter, context), generatedClassSource
                        .toString(), logger);
            }
            return qualifiedWrapperClassName;
        } catch (Exception e) {
            logger.log(Type.ERROR, "RESTServiceGenerator.generate", e);
            throw new UnableToCompleteException();
        }
    }

    private void writeSource(SourceWriter writer, String classSourceString,
                             TreeLogger logger) {
        writer.println(classSourceString);
        writer.commit(logger);
    }

    private StringWriter processTemplate(Map<String, Object> rootMap)
            throws IOException, TemplateException {
        Configuration cfg = new Configuration();
        cfg.setClassForTemplateLoading(RESTServiceGenerator.class, "");
        cfg.setObjectWrapper(new DefaultObjectWrapper());
        Template temp = cfg.getTemplate(TEMPLATE_NAME);
        StringWriter returnCode = new StringWriter();
        temp.process(rootMap, returnCode);
        returnCode.flush();
        return returnCode;
    }

    private Map<String, Object> createRootMap(JClassType requestedClass, final TreeLogger logger, PropertyOracle propertyOracle) throws BadPropertyValueException {

        Map<String, Object> root = new HashMap<String, Object>();
        root.put(SERVICE_CLASS_NAME, requestedClass.getSimpleSourceName() + CLASS_SUFFIX);
        root.put(SERVICE_QUALIFIED_CLASS_NAME, requestedClass.getQualifiedBinaryName());
        List<Map<String, Object>> methods = new ArrayList<Map<String, Object>>();
        root.put(METHODS_KEY, methods);

        root.put("logger", new TemplateMethodModel() {
            @Override
            public Object exec(List arguments) throws TemplateModelException {

                logger.log(Type.INFO, "*********" + arguments.get(0));

                return "logged";
            }
        });

        String path = requestedClass.getAnnotation(GwtPath.class)!=null?requestedClass.getAnnotation(GwtPath.class).value():null;
        if( path==null ) {
            path = requestedClass.getAnnotation(Path.class).value();
        }
        String applicationPath = requestedClass.getAnnotation(GwtApplicationPath.class)!=null?requestedClass.getAnnotation(GwtApplicationPath.class).value():null;
        if( applicationPath==null ) {
            applicationPath = requestedClass.getAnnotation(ApplicationPath.class)!=null?requestedClass.getAnnotation(ApplicationPath.class).value():"";
        }

        path = calculateCorrectPath(path);

        ConfigurationProperty a = propertyOracle.getConfigurationProperty("application.path");

        applicationPath = calculateCorrectApplicationPath(applicationPath != null ? applicationPath : a.getValues().get(0));
        root.put("applicationPath", applicationPath);

        int counter = 0;

        for (JMethod method : requestedClass.getMethods()) {

            Map<String, Object> methodValues = new HashMap<String, Object>();

            if( !method.getReturnType().getQualifiedBinaryName().equals("com.google.gwt.http.client.Request") ) {
                throw new RuntimeException("return type must be Request");
            }

            String httpMethod = method.getAnnotation(POST.class)!=null?HttpMethod.POST:method.getAnnotation(GET.class)!=null?HttpMethod.GET:method.getAnnotation(PUT.class)!=null?HttpMethod.PUT:method.getAnnotation(DELETE.class)!=null?HttpMethod.DELETE:null;
            if( httpMethod==null ) {
                throw new RuntimeException("unspecified HTTP method");
            }

            if( method.getAnnotation(Consumes.class)!=null ) {
                methodValues.put("contentType", method.getAnnotation(Consumes.class).value()[0]);
                methodValues.put("hasContentType", true );
            } else {
                methodValues.put("hasContentType", false );
            }

            String methodPath = path;
            if( method.getAnnotation(Path.class)!=null ) {
                methodPath = methodPath + calculateCorrectPath(method.getAnnotation(Path.class).value());
            }

            methodValues.put("path", methodPath);
            methodValues.put("httpMethod", httpMethod);
            methodValues.put("declaration", method.getReadableDeclaration(false, false, false, false, true));
            methodValues.put("name", method.getName());
            methodValues.put("uniqueName", method.getName()+counter);

            Produces produces = method.getAnnotation(Produces.class);
            if( produces!=null ) {
                Set<String> contentTypes = new HashSet<String>();
                Collections.addAll(contentTypes, produces.value());

                methodValues.put("produces", contentTypes);
            }


            JParameter[] parameters = method.getParameters();

            com.google.gwt.dev.javac.typemodel.JParameter callbackParameter = (com.google.gwt.dev.javac.typemodel.JParameter) parameters[parameters.length - 1];

            methodValues.put("callbackType", callbackParameter.getType().getQualifiedSourceName());
            methodValues.put("callbackName", callbackParameter.getName());

            //TODO: vedere come gestire il caso void !!!!
            if( callbackParameter!=null && callbackParameter.getType()!=null && callbackParameter.getType().isParameterized()!=null && callbackParameter.getType().isParameterized().getTypeArgs()[0]!=null) {

                String qualifiedSourceName = callbackParameter.getType().isParameterized().getTypeArgs()[0].getQualifiedSourceName();
                if( !qualifiedSourceName.equals(callbackParameter.getType().isParameterized().getTypeArgs()[0].getParameterizedQualifiedSourceName()) ) {
                    throw new IllegalArgumentException("Callback parameter "+callbackParameter.getType().isParameterized().getTypeArgs()[0].getParameterizedQualifiedSourceName()+" should be: "+ qualifiedSourceName);
                }

                methodValues.put("hasCallbackInnerType", true);
                methodValues.put("callbackInnerType", qualifiedSourceName);
            } else {
                methodValues.put("hasCallbackInnerType", false);
            }

            List<Map<String, Object>> parameterArray = new ArrayList<Map<String, Object>>();
            methodValues.put("parameters", parameterArray);
            for(int i=0;i<parameters.length-1;i++) {

                Map<String, Object> parameterValues = new HashMap<String, Object>();
                parameterArray.add(parameterValues);

                JParameter parameter = parameters[i];

                parameterValues.put("type", parameter.getType().getQualifiedSourceName());
                parameterValues.put("simpleType", parameter.getType().getSimpleSourceName());
                parameterValues.put("nome", parameter.getName());

                logger.log(Type.DEBUG, "parameter name is " + parameter.getName());

                boolean isInputParameter = false;
                boolean isPathParameter = false;
                boolean isQueryParameter = false;
                boolean isHeaderParameter = false;

                if (parameter.getAnnotation(PathParam.class)!=null ) {
                    parameterValues.put("pathParam", "{"+parameter.getAnnotation(PathParam.class).value()+"}");

                    isPathParameter = true;
                } else if (parameter.getAnnotation(QueryParam.class)!=null ) {
                    parameterValues.put("queryParam", parameter.getAnnotation(QueryParam.class).value());

                    isQueryParameter = true;
                } else if (parameter.getAnnotation(HeaderParam.class)!=null ) {
                    parameterValues.put("headerParam", parameter.getAnnotation(HeaderParam.class).value());

                    isHeaderParameter = true;
                } else {

                    isInputParameter = true;
                }

                parameterValues.put("isQueryParameter", isQueryParameter);
                parameterValues.put("isPathParameter", isPathParameter);
                parameterValues.put("isHeaderParameter", isHeaderParameter);
                parameterValues.put("isInputParameter", isInputParameter);
            }

            methods.add(methodValues);
            counter++;
        }
        return root;
    }

    private String calculateCorrectPath(String path) {
        if( !path.startsWith("/") ) {
            path = "/"+path;
        }

        if( path.endsWith("/") ) {
            path = path.substring(0, path.length()-1);
        }
        return path;
    }

    private String calculateCorrectApplicationPath(String applicationPath) {

        if( "".equals(applicationPath) ) {
            return "";
        }

        if( !applicationPath.startsWith("/") ) {
            applicationPath = "/"+applicationPath;
        }

        if( applicationPath.endsWith("/") ) {
            applicationPath = applicationPath.substring(0, applicationPath.length()-1);
        }
        return calculateCorrectPath(applicationPath);
    }

    protected SourceWriter getSourceWriter(String packageName,
                                           String className, String typeName, PrintWriter printWriter,
                                           GeneratorContext context) {
        ClassSourceFileComposerFactory composerFactory = new ClassSourceFileComposerFactory(
                packageName, className);
        composerFactory.addImplementedInterface(typeName);
        composerFactory.addImport(Date.class.getName());
        composerFactory.addImport(Map.class.getName());
        composerFactory.addImport(HashMap.class.getName());
        composerFactory.addImport(ArrayList.class.getName());
        composerFactory.addImport(AutoBean.class.getName());
        composerFactory.addImport(AutoBeanCodex.class.getName());
        composerFactory.addImport(AutoBeanFactory.class.getName());
        composerFactory.addImport(Logger.class.getName());
        composerFactory.addImport(GWT.class.getName());
        composerFactory.addImport(Request.class.getName());
        composerFactory.addImport(Response.class.getName());
        composerFactory.addImport(RequestCallback.class.getName());
        composerFactory.addImport(RequestException.class.getName());
        composerFactory.addImport(RequestBuilder.class.getName());
        composerFactory.addImport(AutoBeanUtils.class.getName());
        composerFactory.addImport(RESTCallback.class.getName());

        return composerFactory.createSourceWriter(context, printWriter);
    }
}