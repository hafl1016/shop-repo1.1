package de.shop.kundenverwaltung.domain;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.constraints.NotEmpty;

public class Adresse implements Serializable {
	private static final long serialVersionUID = -3029272617931844501L;
	
	
	@NotNull
	private Long id;
	
	@NotEmpty
	@Size(min = 2, max = 50)
	@Pattern(regexp = "[A-ZÄÖÜ][a-zäöüß]+")
	private String strasse;
	
	@NotEmpty
	@Size(min = 1, max = 5)
	@Pattern(regexp = "[1-9][1-0a-z]+")
	private String hausnr;
	
	@NotEmpty
	@Pattern(regexp = "\\d{5}")
	private String plz;
	
	@NotEmpty
	@Size(min = 2, max = 50)
	@Pattern(regexp = "[A-ZÄÖÜ][a-zäöüß]+")
	private String ort;
	
	@XmlTransient
	@Valid
	private AbstractKunde kunde;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getStrasse() {
		return strasse;
	}
	
	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}
	
	public String getHausnr() {
		return hausnr;
	}
	
	public void setHausnr(String hausnr) {
		this.hausnr = hausnr;
	}
	
	public String getPlz() {
		return plz;
	}
	
	public void setPlz(String plz) {
		this.plz = plz;
	}
	
	public String getOrt() {
		return ort;
	}
	
	public void setOrt(String ort) {
		this.ort = ort;
	}
	
	public AbstractKunde getKunde() {
		return kunde;
	}
	
	public void setKunde(AbstractKunde kunde) {
		this.kunde = kunde;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hausnr == null) ? 0 : hausnr.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((kunde == null) ? 0 : kunde.hashCode());
		result = prime * result + ((ort == null) ? 0 : ort.hashCode());
		result = prime * result + ((plz == null) ? 0 : plz.hashCode());
		result = prime * result + ((strasse == null) ? 0 : strasse.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Adresse other = (Adresse) obj;
		if (hausnr == null) {
			if (other.hausnr != null)
				return false;
		}
		else if (!hausnr.equals(other.hausnr))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		if (kunde == null) {
			if (other.kunde != null)
				return false;
		}
		else if (!kunde.equals(other.kunde))
			return false;
		if (ort == null) {
			if (other.ort != null)
				return false;
		}
		else if (!ort.equals(other.ort))
			return false;
		if (plz == null) {
			if (other.plz != null)
				return false;
		}
		else if (!plz.equals(other.plz))
			return false;
		if (strasse == null) {
			if (other.strasse != null)
				return false;
		}
		else if (!strasse.equals(other.strasse))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Adresse [id=" + id + ", strasse=" + strasse + ", hausnr="
				+ hausnr + ", plz=" + plz + ", ort=" + ort + "]";
	}
}
