package org.googlecode.gwt.bootstrap.server.dummy;

import it.iconmedialab.esselunga.auth.mock.StaticValidationAccessor;
import it.iconmedialab.esselunga.auth.validation.ValidationData;
import it.iconmedialab.esselunga.auth.validation.ValidationPrincipal;
import it.iconmedialab.esselunga.auth.validation.ValidationProfile;
import it.iconmedialab.esselunga.auth.validation.ValidationRole;

import java.util.HashSet;
import java.util.Set;

public class DummyValidationAccessor extends StaticValidationAccessor {

	public DummyValidationAccessor() {
		super();
		
		StaticValidationAccessor testValidationAccessor = this;
		
		ValidationPrincipal validationPrincipal = new ValidationPrincipal();
		validationPrincipal.setUsername("ictlm1");
		validationPrincipal.setFirstName("Luca");
		validationPrincipal.setLastName("Masini");
		validationPrincipal.setSoc("4");
		validationPrincipal.setCodaz("56848");
		testValidationAccessor.setPrincipal(validationPrincipal);

		ValidationRole role = new ValidationRole();
		role.setCode("DIRNEG");
		role.setDescr("Ruolo direttore negozio");
		
		ValidationProfile profile = new ValidationProfile();
		profile.setCode("prof_DIRNEG");
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

}
