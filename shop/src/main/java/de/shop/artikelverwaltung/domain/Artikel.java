package de.shop.artikelverwaltung.domain;

import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;



@Entity
public class Artikel {

	@Id
	@GeneratedValue
	@Basic(optional = false)
	private Long id;
	
	@NotNull(message="{artikelverwaltung.artikel.bezeichnung.notnull}")
	@Size(min = 2, max = 50, message="{artikelverwaltung.artikel.bezeichnung.length}")
	@Pattern(regexp = "[A-Z\u00C4\u00D6\u00DC][-A-Za-z0-9\u00E4\u00F6\u00FC\u00DF\u0020]+", 
			message = "{artikelverwaltung.artikel.bezeichnung.pattern}")
	private String bezeichnung;
	
	@NotNull(message="{artikelverwaltung.artikel.preis.notnull}")
	@DecimalMin(value ="0.0",message="{artikelverwaltung.artikel.preis.min}")
	private BigDecimal preis;
	
	public long getId() {
		return id;
	}
	
	public String getBezeichnung() {
		return bezeichnung;
	}
	
	public BigDecimal getPreis() {
		return preis;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	
	public void setPreis(BigDecimal preis) {
		this.preis = preis;
	}
	
	@Override
	public String toString() {
		return "Artikel [id=" + id + ", bezeichnung=" + bezeichnung
				+ ", preis=" + preis + "]";
	}
	
	
	
	
}
