package org.googlecode.gwt.bootstrap.server.security;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

public class PDASecurityExtractor extends DefaultJavaEESecurityExtractor {

	private DataSource ds; 

	private final static String sql = 
		" SELECT N.SIGLA " +
		" FROM NEGOZI N , IP_SOCIETA I " +
		" WHERE I.SOCIETA = N.SOCIETA AND N.IP_MAP_NEG IS NOT NULL " +
		" and (I.IP_MAP_SOCIETA || '.' || N.IP_MAP_NEG) =  substr(?,1,instr(?,'.',1,3)-1) ";

	public PDASecurityExtractor(RoleDefinitionExtractor extractionStrategy) {
		super(extractionStrategy);

	}

	public String getCurrentUsername(HttpServletRequest request) {
		String ipAddress=request.getRemoteAddr();
		if (request.getRemoteUser()==null){

			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/fidpublic");
			} catch (NamingException e) {
				throw new RuntimeException(e);
			}
			try{	
				conn = ds.getConnection();
				ps = conn.prepareStatement(sql);
				ps.setString(1, ipAddress);
				ps.setString(2, ipAddress);

				rs = ps.executeQuery();

				if(rs.next()) {
					return rs.getString(1);
				}
			}
			catch(SQLException sql) {
				throw new RuntimeException(sql);
			}
			finally {
				closeRs(rs);
				closePs(ps);
				closeConn(conn);
			}

			if("127.0.0.1".equals(ipAddress)) {
				return "PIO";			
			}
			else {
				return null;
			}

		}
		return request.getRemoteUser();
	}


	private void closeConn(Connection conn) {
		try {
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void closeRs(ResultSet rs) {
		try {
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void closePs(PreparedStatement ps) {
		try {
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}




	//	public String[] getJavaEERoles(HttpServletRequest request) {
	//		return new String[] {"PDAROLE","PDAROLE"};
	//	}


}
