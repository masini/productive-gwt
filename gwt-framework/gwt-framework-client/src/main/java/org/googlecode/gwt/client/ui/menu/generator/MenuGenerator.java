package org.googlecode.gwt.client.ui.menu.generator;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.NotFoundException;

public class MenuGenerator extends Generator {

	public String generate(TreeLogger logger, GeneratorContext context, String typeName) throws UnableToCompleteException {
		try {
			ClassMenuGenerator classMenuBar = new ClassMenuGenerator(logger, context);
			return classMenuBar.create(typeName);

		} catch (ClassNotFoundException e) {
			logger.log(TreeLogger.ERROR, "Reflaction java: Class '" + typeName + "' Not Found", e);
			throw new UnableToCompleteException();
		} catch (NotFoundException e) {
			logger.log(TreeLogger.ERROR, "Reflaction gwt: Class '" + typeName + "' Not Found", e);
			throw new UnableToCompleteException();
		} catch (MenuGeneratorException e) {
			logger.log(TreeLogger.ERROR, "Errore nella creazione dell'oggetto Menu. Errore:" + e.getMessage(), e);
			throw new UnableToCompleteException();
		} catch (IllegalAccessException e) {
			logger.log(TreeLogger.ERROR, "Class '" + typeName + "' Not Found", e);
			throw new UnableToCompleteException();
		} catch (IllegalArgumentException e) {
			logger.log(TreeLogger.ERROR, "Class '" + typeName + "' Not Found", e);
			throw new UnableToCompleteException();
		} 
	}
}
