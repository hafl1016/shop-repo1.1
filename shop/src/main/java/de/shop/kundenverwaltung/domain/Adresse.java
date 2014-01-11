package de.shop.kundenverwaltung.domain;

import static de.shop.util.Constants.KEINE_ID;

import java.lang.invoke.MethodHandles;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PostPersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

import org.jboss.logging.Logger;

import de.shop.util.persistence.AbstractAuditable;


@Entity
@Table(indexes = @Index(columnList = "plz"))
public class Adresse extends AbstractAuditable {
	private static final long serialVersionUID = -5108148468525006134L;
	private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());
	

	@Id
	@GeneratedValue
	@Basic(optional = false)
	private Long id = KEINE_ID;

	@NotNull(message = "{adresse.ort.notNull}")
	@Size(min = 2, max = 32, message = "{adresse.ort.length}")
	@Pattern(regexp = "[A-Z\u00C4\u00D6\u00DC][-a-zA-Z\u00E4\u00F6\u00FC\u00DF\u0020]+", 
			message = "{adresse.ort.pattern}")
	private String ort;
	
	@NotNull(message = "{adresse.strasse.notNull}")
	@Size(min = 2, max = 32, message = "{adresse.strasse.length}")
	@Pattern(regexp = "[A-Z\u00C4\u00D6\u00DC][-a-zA-Z\u00E4\u00F6\u00FC\u00DF\u0020]+", 
			message = "{adresse.strasse.pattern}")
	private String strasse;
	
	@Size(max = 4, message = "{adresse.hausnr.length}")
	@Pattern(regexp = "[1-9][0-9]*[a-z]*", message = "{adresse.hausnr.pattern}")
	private String hausnr;
	
	@NotNull(message = "{adresse.plz.notNull}")
	@Pattern(regexp = "\\d{" + 5 + "}", message = "{adresse.plz.pattern}")
	@Column(length = 5)
	private String plz;


	@OneToOne
	@JoinColumn(name = "kunde_fk", nullable = false, unique = true)
	@XmlTransient
	private AbstractKunde kunde;
	
	public Adresse() {
		super();
	}
	
	public Adresse(String plz, String ort, String strasse, String hausnr, AbstractKunde kunde) {
		super();
		this.plz = plz;
		this.ort = ort;
		this.strasse = strasse;
		this.hausnr = hausnr;
		this.kunde = kunde;
	}
	
	@PostPersist
	private void postPersist() {
		LOGGER.debugf("Neue Adresse mit ID=%s", id);
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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

	public void setKunde(AbstractKunde kunde) {
		this.kunde = kunde;
	}
	public AbstractKunde getKunde() {
		return kunde;
	}
	@Override
	public String toString() {
		return "Adresse [id=" + id + ", plz=" + plz + ", ort=" + ort + ", strasse=" + strasse + ", hausnr=" + hausnr
				+ ", " + super.toString() + ']';
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hausnr == null) ? 0 : hausnr.hashCode());
		result = prime * result + ((ort == null) ? 0 : ort.hashCode());
		result = prime * result + ((plz == null) ? 0 : plz.hashCode());
		result = prime * result + ((strasse == null) ? 0 : strasse.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Adresse other = (Adresse) obj;
		
		if (plz == null) {
			if (other.plz != null) {
				return false;
			}
		}
		else if (!plz.equals(other.plz)) {
			return false;
		}
		
		if (ort == null) {
			if (other.ort != null) {
				return false;
			}
		}
		else if (!ort.equals(other.ort)) {
			return false;
		}
		
		if (strasse == null) {
			if (other.strasse != null) {
				return false;
			}
		}
		else if (!strasse.equals(other.strasse)) {
			return false;
		}
		
		if (hausnr == null) {
			if (other.hausnr != null) {
				return false;
			}
		}
		else if (!hausnr.equals(other.hausnr)) {
			return false;
		}
		
		return true;
	}
}
