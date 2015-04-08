package org.googlecode.gwt.server.security;


import org.googlecode.gwt.server.MenuInterfaceRegistry;
import org.googlecode.gwt.server.exception.BootstrapModelException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReflectionRoleDefinitionExtractor implements RoleDefinitionExtractor{

    public String[] extractAllRoles() {
		return getAllDeclaredRoles(MenuInterfaceRegistry.getMenuInterfaceClass());
	}

	public static String[] getAllDeclaredRoles(String menuInterfaceName) throws BootstrapModelException {
		if(menuInterfaceName!=null) {
			String cleanedMenuInterfaceName = cleanName(menuInterfaceName);
			
			try {
				Class<?> menuInterface = Class.forName(cleanedMenuInterfaceName);
				List<String> pannelli = new ArrayList<String>();
				elabClass(menuInterface, pannelli);
				
				return pannelli.toArray(new String[pannelli.size()]);
			} catch (ClassNotFoundException e) {
				String msg = "Non trovata la classe dei menu: " + cleanedMenuInterfaceName;
				throw new BootstrapModelException(msg);
			} catch (BootstrapModelException e) {
				throw new BootstrapModelException(e.getMessage());
			}
			
		}
		else {
			return new String[0];
		}
	}

	@SuppressWarnings("unchecked")
	private static void elabClass(Class menuInterface, List<String> pannelli) throws BootstrapModelException {
//		Class[] classi = menuInterface.getClasses();
//
//        for ( Class classe : classi ) { 
//
//            Class<Object> typeClazz = getInterface( classe );
//            if ( typeClazz != null ) {
//                if ( typeClazz.isAssignableFrom( SToolbarSede.SMenuItem.class ) || typeClazz.isAssignableFrom( SToolbarNegozio.SMenuItem.class ) ) {
//                    pannelli.add( classe.getSimpleName() + "Panel" );
//                    log.info( "pannello: " + classe.getSimpleName() + "Panel" );
//                }
//                else {
//                    log.debug( "menu" );
//                    elabClass( classe, pannelli );
//                }
//            }
//            else {
//                String msg = "Le innerClass devono implementare " + SToolbarSede.SMenuItem.class + " oppure " + SMenu.class;
//                BootstrapModelException e = new BootstrapModelException( 0, msg );
//                log.error( msg, e );
//                throw e;
//            }
//        }
	}

	@SuppressWarnings("unchecked")
	private static Class<Object> getInterface(Class<Object> classe) {

//		Class<Object>[] allInterfaces = ( Class<Object>[] )classe.getInterfaces();
//        for ( Class<Object> inter : allInterfaces ) {
//            if ( inter.isAssignableFrom( SToolbarSede.SMenuItem.class ) || inter.isAssignableFrom( SToolbarNegozio.SMenuItem.class ) ||
//                 inter.isAssignableFrom( SMenu.class ) ) {
//                return inter;
//            }
//        }
//	
		return null;
	}

	private static String cleanName(String menuInterfaceName) {
		String[] str = menuInterfaceName.split(" ");
	
		return str[str.length - 1];
	}
	
	public static String[] extractRolesFromWebXML(InputStream webXML) throws SAXException, IOException, ParserConfigurationException {
		final Set<String> roleList = new HashSet<String>();
		
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setValidating(false);

		factory.newSAXParser().parse(webXML, new DefaultHandler(){

			private boolean isRoleName;
			@Override
			public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
				if("role-name".equals(qName)){
					isRoleName = true;							
				}
			}

			@Override
			public void characters(char[] ch, int start, int length) throws SAXException {
				if(isRoleName){
					roleList.add(new String(ch, start, length));
				}
			}

			@Override
			public void endElement(String uri, String localName, String qName) throws SAXException {
				if("role-name".equals(qName)){
					isRoleName = false;							
				}					
			}

		});
		return roleList.toArray(new String[0]);
	}  		
	
}
