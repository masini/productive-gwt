package org.googlecode.gwt.bootstrap.server.pda;

import it.iconmedialab.esselunga.auth.mock.StaticValidationAccessor;
import it.iconmedialab.esselunga.auth.validation.ValidationData;
import it.iconmedialab.esselunga.auth.validation.ValidationPrincipal;
import it.iconmedialab.esselunga.auth.validation.ValidationProfile;
import it.iconmedialab.esselunga.auth.validation.ValidationRole;

import java.util.HashSet;
import java.util.Set;

public class PDAValidationAccessor extends StaticValidationAccessor {

	public PDAValidationAccessor() {
		super();
		
		StaticValidationAccessor testValidationAccessor = this;
		
		ValidationPrincipal validationPrincipal = new ValidationPrincipal();
		validationPrincipal.setUsername("utentePDA");
		validationPrincipal.setFirstName("LucaPDA");
		validationPrincipal.setLastName("MasiniPDA");
		validationPrincipal.setSoc("1");
		validationPrincipal.setCodaz("56848");
		testValidationAccessor.setPrincipal(validationPrincipal);

		ValidationRole role = new ValidationRole();
		role.setCode("PDAROLE");
		role.setDescr("Ruolo PDA");
		
		ValidationProfile profile = new ValidationProfile();
		profile.setCode("PDAROLE");
		profile.setDescr("Profilo direttore negozio");

		profile.addParameter("CODNEG", "112");
		profile.addParameter("LOCALITA", "MI");
		role.addProfile(profile);
		
		Set<ValidationRole> roles = new HashSet<ValidationRole>();
		roles.add(role);		
		
		ValidationData data = new ValidationData();		
		data.setRoles(roles);
		testValidationAccessor.setData(data);
		
	}
	@Override
	public ValidationPrincipal getPrincipal(String username) {
		return super.getPrincipal(username);
	}

}
