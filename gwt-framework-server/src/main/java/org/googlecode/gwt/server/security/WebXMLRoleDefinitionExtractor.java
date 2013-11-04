package org.googlecode.gwt.server.security;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

@Resource(name="web.xml")
public class WebXMLRoleDefinitionExtractor implements RoleDefinitionExtractor {

	
	private static final String[] EMPTYSTRINGARRAY = new String[0];
	private String[] extractedRoles;
	
	public WebXMLRoleDefinitionExtractor() {
	}

	public WebXMLRoleDefinitionExtractor(InputStream webXML) {
		setWebXML(webXML);
	}
	
	public String[] extractAllRoles() {
		return extractedRoles;
	}

	private static String[] extractRolesFromWebXML(InputStream webXML) throws SAXException, IOException, ParserConfigurationException {
		final Set<String> roleList = new HashSet<String>();
		
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setValidating(false);

		factory.newSAXParser().parse(webXML, new DefaultHandler(){

			boolean isRoleName = false;
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
		return roleList.toArray(EMPTYSTRINGARRAY);
	}

	public void setWebXML(InputStream webXML) {
		try {			
			extractedRoles = extractRolesFromWebXML(webXML);
		} catch (Exception e) {
			extractedRoles = EMPTYSTRINGARRAY;
		}
		
	}  			
}
