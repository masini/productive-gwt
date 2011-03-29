package com.mycompany.project.shared;

import java.io.Serializable;

import org.googlecode.gwt.reflection.client.Reflectable;

public class PojoBean implements Serializable, Reflectable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String codice;
	private String descrizione;
	private String timestamp_id;
	private Double value;
	private Integer numero;
	
	public String getCodice() {
		return codice;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getTimestamp_id() {
		return timestamp_id;
	}
	public void setTimestamp_id(String timestamp_id) {
		this.timestamp_id = timestamp_id;
	}
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}

}
