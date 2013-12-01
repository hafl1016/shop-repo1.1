package de.shop.bestellverwaltung.domain;

import java.io.Serializable;
import java.net.URI;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.constraints.NotEmpty;

import de.shop.kundenverwaltung.domain.AbstractKunde;

@XmlRootElement
public class Bestellung implements Serializable {
	private static final long serialVersionUID = 1618359234119003714L;
	
	
	private Long id;
	
	@AssertFalse(message = "{bestellverwaltung.bestellung.istAusgeliefert.assertFalse}")
	private boolean ausgeliefert;
	
	@NotNull(message = "{bestellverwaltung.bestellung.bestelldatum.notNull}")
	private String bestelldatum;
	
	@NotEmpty
	@Valid
	private List<Position> positionen;
	
	@Valid
	@XmlTransient
	private AbstractKunde kunde;
	
	private URI kundeUri;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getBestelldatum() {
		return bestelldatum;
	}
	
	public void setBestelldatum(String bestelldatum) {
		this.bestelldatum = bestelldatum;
	}
	
	public boolean isAusgeliefert() {
		return ausgeliefert;
	}
	
	public void setAusgeliefert(boolean ausgeliefert) {
		this.ausgeliefert = ausgeliefert;
	}
	
	public AbstractKunde getKunde() {
		return kunde;
	}
	
	public void setKunde(AbstractKunde kunde) {
		this.kunde = kunde;
	}
	
	public URI getKundeUri() {
		return kundeUri;
	}
	
	public void setKundeUri(URI kundeUri) {
		this.kundeUri = kundeUri;
	}
	
	 public void setPositionen(List<Position> positionen) {
         this.positionen = positionen;
	 
	 }
	  public List<Position> getPositionen() {
          return positionen;
	 }
	  
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		final Bestellung other = (Bestellung) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Bestellung [id=" + id + ", ausgeliefert=" + ausgeliefert + ", kundeUri=" + kundeUri + "]";
	}
}
