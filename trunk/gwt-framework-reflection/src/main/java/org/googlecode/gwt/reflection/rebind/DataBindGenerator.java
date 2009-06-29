package org.googlecode.gwt.reflection.rebind;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.googlecode.gwt.reflection.client.WrapperFactory;
import org.googlecode.gwt.reflection.client.converters.Converter;
import org.googlecode.gwt.reflection.client.converters.NoConverter;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class DataBindGenerator extends Generator {

	private static final String FIELDS_KEY = "fields";

	private static final String POJO_CLASS_NAME_PROPERTY = "pojoClassName";
	private static final String IS_PRIMITIVE_PROPERTY = "isPrimitive";
	private static final String PROPERTY_TYPE_PROPERTY = "propertyType";
	private static final String PROPERTY_NAME_PROPERTY = "propertyName";
	private static final String CONVERTER_PROPERTY = "converter";
	private static final String CAN_READ_PROPERTY = "canRead";
	private static final String CAN_WRITE_PROPERTY = "canWrite";

	private static final String TEMPLATE_NAME = "reflectionTmp.ftl";
	private static final String CLASS_SUFFIX = "_Wrapper";


	private static final Map<Class<?>, Class<?>> primitiveMap = new HashMap<Class<?>, Class<?>>();

	static {
		primitiveMap.put(boolean.class, Boolean.class);
		primitiveMap.put(byte.class, Byte.class);
		primitiveMap.put(char.class, Character.class);
		primitiveMap.put(short.class, Short.class);
		primitiveMap.put(int.class, Integer.class);
		primitiveMap.put(long.class, Long.class);
		primitiveMap.put(float.class, Float.class);
		primitiveMap.put(double.class, Double.class);
	}

	@Override
	public String generate(TreeLogger logger, GeneratorContext context,
			String typeName) throws UnableToCompleteException {

		try {
			
			Properties prop = new Properties();

			prop.load(this.getClass().getResourceAsStream("converters.properties"));
			BeanInfo beanInfo = Introspector.getBeanInfo(Class
					.forName(typeName), Object.class);

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
				Map<String, Object> rootMap = createRootMap(beanInfo, prop, typeName);
				Writer generatedClassSource = processTemplate(rootMap);
				writeSource(getSourceWriter(packageName, wrapperClassName,
						typeName, printWriter, context), generatedClassSource
						.toString(), logger);
			}
			return qualifiedWrapperClassName;
		} catch (Exception e) {
			logger.log(Type.ERROR, "DataBindGenerator::generate", e);
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
		cfg.setClassForTemplateLoading(DataBindGenerator.class, "");
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		Template temp = cfg.getTemplate(TEMPLATE_NAME);
		StringWriter returnCode = new StringWriter();
		temp.process(rootMap, returnCode);
		returnCode.flush();
		return returnCode;
	}

	private Map<String, Object> createRootMap(BeanInfo beanInfo, Properties properties,
			String pojoClassName) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put(POJO_CLASS_NAME_PROPERTY, pojoClassName);
		List<Map<String, Object>> fields = new ArrayList<Map<String, Object>>();
		root.put(FIELDS_KEY, fields);

		for (PropertyDescriptor propDesc : beanInfo.getPropertyDescriptors()) {
			Map<String, Object> field = new HashMap<String, Object>();

			field.put(IS_PRIMITIVE_PROPERTY, propDesc.getPropertyType()
					.isPrimitive());
			field.put(PROPERTY_NAME_PROPERTY, propDesc.getName());

			String type_property = (propDesc.getPropertyType().isPrimitive()) ? primitiveMap
					.get(propDesc.getPropertyType()).getName()
					: propDesc.getPropertyType().getCanonicalName();
			field.put(PROPERTY_TYPE_PROPERTY, type_property);

			boolean canRead = propDesc.getReadMethod() != null;
			boolean canWrite = propDesc.getWriteMethod() != null;

			field.put("propDesc", propDesc);
			field.put(CAN_READ_PROPERTY, canRead);
			field.put(CAN_WRITE_PROPERTY, canWrite);

			if (properties.get(type_property) != null) {
				field.put(CONVERTER_PROPERTY, properties.get(type_property));
			} else {
				field.put(CONVERTER_PROPERTY, NoConverter.class.getName());
			}

			try {
				field.put(CONVERTER_PROPERTY, propDesc.getReadMethod()
						.getAnnotation(Converter.class).value()
						.getCanonicalName());
			} catch (NullPointerException e) {
				try {
					field.put(CONVERTER_PROPERTY, propDesc.getReadMethod()
							.getAnnotation(Converter.class).value()
							.getCanonicalName());
				} catch (Exception ex) {

				}
			}


			fields.add(field);
		}
		return root;
	}

	protected SourceWriter getSourceWriter(String packageName,
			String className, String typeName, PrintWriter printWriter,
			GeneratorContext context) {
		ClassSourceFileComposerFactory composerFactory = new ClassSourceFileComposerFactory(
				packageName, className);
		composerFactory.addImplementedInterface("WrapperFactory<" + typeName
				+ ">");
		composerFactory.addImport(Date.class.getName());
		composerFactory.addImport(Map.class.getName());
		composerFactory.addImport(HashMap.class.getName());
		composerFactory.addImport(ArrayList.class.getName());
		composerFactory.addImport(WrapperFactory.class.getName());
		return composerFactory.createSourceWriter(context, printWriter);
	}
}
