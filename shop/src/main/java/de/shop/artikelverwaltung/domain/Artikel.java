package de.shop.artikelverwaltung.domain;

public class Artikel {

	
	private long id;
	private String bezeichnung;
	private double preis;
	public long getId() {
		return id;
	}
	
	public String getBezeichnung() {
		return bezeichnung;
	}
	
	public double getPreis() {
		return preis;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	
	public void setPreis(double preis) {
		this.preis = preis;
	}
	
	@Override
	public String toString() {
		return "Artikel [id=" + id + ", bezeichnung=" + bezeichnung
				+ ", preis=" + preis + "]";
	}
	
	
	
	
}
