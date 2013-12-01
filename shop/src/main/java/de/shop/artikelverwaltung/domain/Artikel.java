package de.shop.artikelverwaltung.domain;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Artikel {

	@NotNull
	private long id;
	
	@NotEmpty
	@Size(min = 2, max = 50)
	@Pattern(regexp = "[A-ZÄÖÜ][a-zäöüß]+")
	private String bezeichnung;
	
	@NotEmpty
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
