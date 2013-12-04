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

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Artikel {

	@Id
	@GeneratedValue
	@Basic(optional = false)
	private Long id;
	
	@NotNull
	@Size(min = 2, max = 50, message="{artikelverwaltung.artikel.bezeichnung.length}")
	@Pattern(regexp = "[A-Z\u00C4\u00D6\u00DC][a-z\u00E4\u00F6\u00FC\u00DF]+", 
			message = "{artikelverwaltung.artikel.bezeichnung.pattern}")
	private String bezeichnung;
	
	@NotEmpty(message="{artikelverwaltung.artikel.preis.notempty}")
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
