package org.googlecode.gwt.reflection.client.complexmodel;

import java.math.BigDecimal;

import org.googlecode.gwt.reflection.client.converters.Converter;


public class Articolo extends GenericModel{

	private static final long serialVersionUID = 1L;

	private Long id;
	

	private StatoArticolo stato;
	private Compratore compratore;
	private BigDecimal peso;
	private BigDecimal pesoForzato;
	private boolean flInFattura;
	
	public Articolo() {
	}

	public Articolo(Long id) {
		this.id = id;
	}

	public Articolo(String codice) {
		super.setCodice(codice);
	}

	public Articolo(Long id, String codice) {
		this.id = id;
		super.setCodice(codice);
	}
	
	public Articolo(Long id, String codice, String descr) {
		this.id = id;
		super.setCodice(codice);
		super.setDescrizione(descr);
	}
	
	public Compratore getCompratore() {
		return compratore;
	}

	public void setCompratore(Compratore compratore) {
		this.compratore = compratore;
	}

	public BigDecimal getPeso() {
		return peso;
	}

	public void setPeso(BigDecimal peso) {
		this.peso = peso;
	}

	public BigDecimal getPesoForzato() {
		return pesoForzato;
	}

	public void setPesoForzato(BigDecimal pesoForzato) {
		this.pesoForzato = pesoForzato;
	}

	public boolean isFlInFattura() {
		return flInFattura;
	}

	public void setFlInFattura(boolean flInFattura) {
		this.flInFattura = flInFattura;
	}
	
	public Long getId() {
		return id;
	}
	


	public void setId(Long id) {
		this.id = id;
	}

	@Converter(org.googlecode.gwt.reflection.client.complexmodel.StatoArticoloConverter.class)
	public StatoArticolo getStato() {
		return stato;
	}

	public void setStato(StatoArticolo stato) {
		this.stato = stato;
	}
}
