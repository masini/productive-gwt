package org.googlecode.gwt.bootstrap.server;

import it.esselunga.pos.stores.Store;
import it.esselunga.pos.stores.StoreDAO;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.googlecode.gwt.base.client.UserInfo;
import org.googlecode.gwt.bootstrap.client.DefaultUserInfo;

/**
 * @author Davide Baroncelli <davide.baroncelli@esselunga.it>
 *         Date: 16-ott-2007
 *         Time: 16.50.13
 */
public class BackofficeValidationUserInfoResolver extends BarebonesValidationUserInfoResolver {
    private StoreDAO storeDAO;
    private boolean storeRequired = true;
    private List<String> storeRequiredRoles = null;

	@Override
    public UserInfo getCurrentUserInfo( HttpServletRequest request ) {
        DefaultUserInfo userInfo = ( DefaultUserInfo )super.getCurrentUserInfo( request );
        Map map = userInfo.getUserParameters();
        String location = ( String )map.get( UserInfo.DEFAULT_LOCATION_PARAM_NAME );
        String storeCode = ( String )map.get( UserInfo.DEFAULT_CODNEG_PARAM_NAME );
        if (storeRequired || (location != null && storeCode != null) || hasRoleStoreRequired(userInfo.getRole())) { 
        	Store store = storeDAO.getStoreByCode( location, new BigInteger( storeCode ), true );
        	String sigla = store.getSigla();
            map.put( UserInfo.DEFAULT_SIGLA_PARAM_NAME, sigla );
        }
        return userInfo;
    }
	
	protected boolean hasRoleStoreRequired(String role) {
		boolean hasRoleStoreRequired = false;
		
		if (storeRequiredRoles != null) {
			hasRoleStoreRequired = storeRequiredRoles.contains(role);
		}
		
		return hasRoleStoreRequired;
	}

    public void setStoreDAO( StoreDAO storeDAO ) {
        this.storeDAO = storeDAO;
    }

	public void setStoreRequired(boolean storeRequired) {
		this.storeRequired = storeRequired;
	}
	
    public void setStoreRequiredRoles(List<String> storeRequiredRoles) {
		this.storeRequiredRoles = storeRequiredRoles;
	}
}

