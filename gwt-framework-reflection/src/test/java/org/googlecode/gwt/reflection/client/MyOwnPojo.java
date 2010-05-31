package org.googlecode.gwt.reflection.client;


import java.util.Date;

import org.googlecode.gwt.reflection.client.converters.Converter;

import com.google.gwt.core.client.GWT;

public class MyOwnPojo implements Reflectable {
	
	public static final class IndirizzoConverter implements WrapperFactory.Converter<Indirizzo>{

		public Indirizzo convertFromString(String value) {
			// TODO Auto-generated method stub
			return null;
		}

		public String convertToString(Indirizzo value) {
			// TODO Auto-generated method stub
			return value.getVia()+", "+Integer.toString(value.getNumero())+ " - "+value.getCitta();
		}
		
	}

	public enum Stati {
		A,
		B,
		C
	}
	
	private String nome;
	private int eta;
	private Date dataNascita;
	private Stati stato;
	private Boolean bella;
	
	
	private Indirizzo indirizzo;
	
	@Converter(IndirizzoConverter.class)
	public Indirizzo getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(Indirizzo indirizzo) {
		this.indirizzo = indirizzo;
	}
	
	
	public static class Indirizzo{
		
		
		public Indirizzo(String citta, Integer numero, String via) {
			super();
			this.citta = citta;
			this.numero = numero;
			this.via = via;
		}
		private String via;
		private Integer numero;
		private Integer cap;
		private String citta;
		public String getVia() {
			return via;
		}
		public void setVia(String via) {
			this.via = via;
		}
		public Integer getNumero() {
			return numero;
		}
		public void setNumero(Integer numero) {
			this.numero = numero;
		}
		public Integer getCap() {
			return cap;
		}
		public void setCap(Integer cap) {
			this.cap = cap;
		}
		public String getCitta() {
			return citta;
		}
		public void setCitta(String citta) {
			this.citta = citta;
		}
		
		
		
		
	}
	
	public Boolean isBella() {
		return bella;
	}
	public void setBella(Boolean bella) {
		GWT.log("Bella:"+bella, null);
		this.bella = bella;
	}
	public Stati getStato() {
		return stato;
	}
	public void setStato(Stati stato) {
		GWT.log("Stato:"+stato, null);
		
		this.stato = stato;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getEta() {
		return eta;
	}
	public void setEta(int eta) {
		this.eta = eta;
	}
	public Date getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}
}
