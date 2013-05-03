package com.mycompany.project.client;


import java.util.Date;

import org.googlecode.gwt.reflection.client.Reflectable;
import org.googlecode.gwt.reflection.client.converters.Converter;

public class PersonPojo implements Reflectable {
	
	public enum Stati {
		CELIBE,
		NUBILE,
		CONIUGATO
	}
	
	private String nome;
	private String cognome;
	private int eta;
	private Date dataNascita;
	private Stati stato;
	private boolean uomo;
	
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public boolean isUomo() {
		return uomo;
	}
	public void setUomo(boolean uomo) {
		this.uomo = uomo;
	}

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
	
	@Converter(StatiConverter.class)
	public Stati getStato() {
		return stato;
	}
	public void setStato(Stati stato) {
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
