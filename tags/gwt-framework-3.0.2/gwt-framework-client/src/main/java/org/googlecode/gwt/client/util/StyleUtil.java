package org.googlecode.gwt.client.util;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.UIObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * a function collection to handle set various CSS style.
 */
public class StyleUtil {
	public static void setFontSize(UIObject obj, int value) {
		DOM.setStyleAttribute(obj.getElement(), "fontSize", value + "px");
	}

	public static void setFontWeight(UIObject obj, int value) {
		DOM.setIntStyleAttribute(obj.getElement(), "fontWeight", value);
	}

//	public static void setTextColor(UIObject obj, Color color) {
//		DOM.setStyleAttribute(obj.getElement(), "color", color.toString());
//	}
//
//	public static void setBgColor(UIObject obj, Color color) {
//		DOM.setStyleAttribute(obj.getElement(), "backgroundColor", color.toString());
//	}

	public static void setPadding(UIObject obj, int value) {
		DOM.setStyleAttribute(obj.getElement(), "padding", value + "px");
	}
	

	
	public static void setPadding(UIObject obj, int top, int bottom, int left, int right) {
		DOM.setStyleAttribute(obj.getElement(), "paddingTop", top + "px");
		DOM.setStyleAttribute(obj.getElement(), "paddingBottom", bottom + "px");
		DOM.setStyleAttribute(obj.getElement(), "paddingLeft", left + "px");
		DOM.setStyleAttribute(obj.getElement(), "paddingRight", right + "px");
	}

	public static void setMargin(UIObject obj, int value) {
		DOM.setStyleAttribute(obj.getElement(), "margin", value + "px");
	}

	public static void setMargin(UIObject obj, int top, int bottom, int left, int right) {
		DOM.setStyleAttribute(obj.getElement(), "marginTop", top + "px");
		DOM.setStyleAttribute(obj.getElement(), "marginBottom", bottom + "px");
		DOM.setStyleAttribute(obj.getElement(), "marginLeft", left + "px");
		DOM.setStyleAttribute(obj.getElement(), "marginRight", right + "px");

	}

	public static void setBorder(UIObject obj, String stlye) {
		DOM.setStyleAttribute(obj.getElement(), "border", stlye);
	}

	public static void setCursorPointer(UIObject obj) {
		setCursor(obj, "pointer");
	}

	public static void setCursor(UIObject obj, String style) {
		DOM.setStyleAttribute(obj.getElement(), "cursor", style);
	}

	public static void setTextDecoration(UIObject obj, String style) {
		DOM.setStyleAttribute(obj.getElement(), "text-decoration", style);
	}

	public static void setAlign(UIObject obj, String style) {
		DOM.setStyleAttribute(obj.getElement(), "align", style);
	}

	public static void setStyle(UIObject obj, HashMap<String, String> styles) {
		if (styles != null && !styles.isEmpty()) {
			Set<Map.Entry<String, String>> set = styles.entrySet();
			for (Iterator<Map.Entry<String, String>> iter = set.iterator(); iter.hasNext();) {
				Map.Entry<String,String> element = iter.next();
				DOM.setStyleAttribute(obj.getElement(), (String) element.getKey(), (String) element.getValue());
			}
		}
	}

}