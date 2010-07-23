<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.Properties"%>
<%@page import="java.util.Enumeration"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<ul>
	<%
		Properties props = System.getProperties();
		
		for(Enumeration<Object> enumeration = props.elements();enumeration.hasMoreElements();) {
			String propName = enumeration.nextElement().toString();
			
			%>
			<li><%= propName %>: <%= System.getProperty(propName) %></li>
			<%
		}
	%>
</ul>
</body>
</html>