package org.googlecode.gwt.menu.generator;

import com.google.gwt.core.ext.BadPropertyValueException;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.NotFoundException;

public class MenuGenerator extends Generator {

	private static final String MENU_LOCATION = "org.googlecode.Menu";
	
	public String generate(TreeLogger logger, GeneratorContext context, String typeName) throws UnableToCompleteException {
		try {
			ClassMenuGenerator classMenuBar = new ClassMenuGenerator(logger, context);

			String location = context.getPropertyOracle().getPropertyValue(logger, MENU_LOCATION);
			location = location.replaceAll("_", ".");
			
			if(location!=null && !location.equals("")){
				return classMenuBar.create(location);
			}
			else{
				String msg = "Property  '" + MENU_LOCATION + "' find without value.";
				logger.log(TreeLogger.ERROR, msg, new MenuGeneratorException(msg));
				return "";
			}

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
		} catch (BadPropertyValueException e) {
			logger.log(TreeLogger.ERROR, "Property  '" + MENU_LOCATION + "' Not Found", e);
			throw new UnableToCompleteException();
		}

	}

}
