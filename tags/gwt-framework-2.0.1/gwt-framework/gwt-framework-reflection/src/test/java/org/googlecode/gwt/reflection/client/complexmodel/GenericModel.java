package org.googlecode.gwt.reflection.client.complexmodel;

import java.io.Serializable;

import org.googlecode.gwt.reflection.client.Reflectable;

public class GenericModel implements Serializable, Reflectable  {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String codice;
	private String descrizione;
	
	
	
	public final static GenericModel EMPTY = new GenericModel(new Long(-1), "-1", " ");
	
	public GenericModel(Long id, String codice, String descrizione) {
		super();
		setId(id);
		setCodice(codice);
		setDescrizione(descrizione);
	}	

	public GenericModel() {
	}
	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String denominazione) {
		this.descrizione = denominazione;
	}
	

}
