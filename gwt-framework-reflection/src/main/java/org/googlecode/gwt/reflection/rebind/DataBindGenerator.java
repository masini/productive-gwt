package org.googlecode.gwt.reflection.rebind;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.googlecode.gwt.reflection.client.converters.Converter;
import org.googlecode.gwt.reflection.client.converters.NoConverter;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class DataBindGenerator extends Generator {
	
	private static final String INTEGER_CONVERTER_NAME = "org.googlecode.gwt.reflection.client.converters.IntegerConverter";
	private static final String LONG_CONVERTER_NAME = "org.googlecode.gwt.reflection.client.converters.LongConverter";
	private static final String BOOLEAN_CONVERTER_NAME = "org.googlecode.gwt.reflection.client.converters.BooleanConverter";

	private static final String FIELDS_KEY = "fields";
	private static final String PREFIX_GET = "get";
	private static final String PREFIX_IS = "is";

	private static final String POJO_CLASS_NAME_PROPERTY = "pojoClassName";
	private static final String IS_PRIMITIVE_PROPERTY = "isPrimitive";
	private static final String PROPERTY_TYPE_PROPERTY = "propertyType";
	private static final String PROPERTY_NAME_PROPERTY = "propertyName";
	private static final String PREFIX_PROPERTY = "prefix";
	private static final String CAMEL_CASE_PN_PROPERTY = "camelCasePropertyName";
	private static final String CONVERTER_PROPERTY = "converter";

	private static final String TEMPLATE_NAME = "reflectionTmp.ftl";
	private static final String CLASS_SUFFIX = "_Wrapper";
	
	private static final Map<String, String> converterMap = new HashMap<String, String>();
	
	static{
		converterMap.put(Integer.class.getName(), INTEGER_CONVERTER_NAME);
		converterMap.put(Long.class.getName(), LONG_CONVERTER_NAME);
		converterMap.put(Boolean.class.getName(), BOOLEAN_CONVERTER_NAME);
		converterMap.put("int", INTEGER_CONVERTER_NAME);
		converterMap.put("long", LONG_CONVERTER_NAME);
		converterMap.put("boolean", BOOLEAN_CONVERTER_NAME);
	}

	private TreeLogger logger;
	private GeneratorContext context;
	private String pojoClassName;

	@Override
	public String generate(TreeLogger logger, GeneratorContext context,
			String typeName) throws UnableToCompleteException {

		this.context = context;
		this.logger = logger;
		this.pojoClassName = typeName;

		try {
			
			TypeOracle typeOracle = context.getTypeOracle();
			JClassType requestedClass = typeOracle.getType(typeName);
			
			String packageName = requestedClass.getPackage().getName();
			String wrapperClassName = requestedClass.getSimpleSourceName()
					+ CLASS_SUFFIX;

			PrintWriter printWriter = context.tryCreate(logger, packageName,
					wrapperClassName);

			String qualifiedWrapperClassName = packageName + "." + wrapperClassName;

			
			if(printWriter!=null) {
				Map<String, Object> rootMap = createRootMap(requestedClass);

				StringWriter generatedClassSource = processTemplate(rootMap);

				writeSource(requestedClass, getSourceWriter(logger, context, packageName,
						wrapperClassName, this.pojoClassName, printWriter), generatedClassSource.toString());				
			}

			return qualifiedWrapperClassName;

		} catch (Exception e) {
			logger.log(Type.ERROR, "DataBindGenerator::generate", e);
			throw new UnableToCompleteException();
		}
	}

	private void writeSource(JClassType requestedClass, SourceWriter writer, 
			String classSourceString) {

		writer.println(classSourceString);
		writer.commit(logger);
	}

	private StringWriter processTemplate(Map<String, Object> rootMap)
			throws IOException, TemplateException {
		Configuration cfg = new Configuration();
		cfg.setClassForTemplateLoading(DataBindGenerator.class, "");
		cfg.setObjectWrapper(new DefaultObjectWrapper());

		Template temp = cfg.getTemplate(TEMPLATE_NAME);

		StringWriter returnCode = new StringWriter();

		temp.process(rootMap, returnCode);
		returnCode.flush();

		return returnCode;
	}

	private Map<String, Object> createRootMap(JClassType classType) {
		Map<String, Object> root = new HashMap<String, Object>();

		root.put(POJO_CLASS_NAME_PROPERTY, pojoClassName);

		List<Map<String, Object>> fields = new ArrayList<Map<String, Object>>();

		root.put(FIELDS_KEY, fields);

		for (JField jField : classType.getFields()) {
			Map<String, Object> field = new HashMap<String, Object>();

			field.put(IS_PRIMITIVE_PROPERTY,
					jField.getType().isPrimitive() != null);
			field.put(PROPERTY_NAME_PROPERTY, jField.getName());
			
			String type_property = (jField.getType().isPrimitive() != null) ? jField.getType()
					.isPrimitive().getQualifiedBoxedSourceName()
					: jField.getType().getQualifiedSourceName();
			field.put(PROPERTY_TYPE_PROPERTY, type_property);

			StringBuilder camelCase = new StringBuilder(jField.getName());
			camelCase.replace(0, 1, jField.getName().substring(0, 1)
					.toUpperCase());
			field.put(CAMEL_CASE_PN_PROPERTY, camelCase.toString());

			String prefix = PREFIX_GET;

			try {
				classType.getMethod(PREFIX_IS + camelCase, new JType[] {});

				prefix = PREFIX_IS;
			} catch (Exception e) {
			}

			field.put(PREFIX_PROPERTY, prefix);
			Converter c = jField.getAnnotation(Converter.class);
			if(c != null){
				field.put(CONVERTER_PROPERTY, c.value());
			}
			else if(converterMap.get(type_property) != null){
				field.put(CONVERTER_PROPERTY, converterMap.get(type_property));	
			}else{
				field.put(CONVERTER_PROPERTY, NoConverter.class.getName());
			}
			

			
			fields.add(field);
		}
		return root;

	}

	protected SourceWriter getSourceWriter(TreeLogger logger,
			GeneratorContext context, String packageName, String className,
			String typeName, PrintWriter printWriter) {
		ClassSourceFileComposerFactory composerFactory = new ClassSourceFileComposerFactory(
				packageName, className);

		composerFactory.addImplementedInterface("WrapperFactory<" + typeName
				+ ">");
		composerFactory.addImport(Date.class.getName());
		composerFactory.addImport(Map.class.getName());
		composerFactory.addImport(HashMap.class.getName());

		return composerFactory.createSourceWriter(context, printWriter);
	}

}
