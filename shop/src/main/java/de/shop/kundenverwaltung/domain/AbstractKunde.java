package de.shop.kundenverwaltung.domain;

import java.io.Serializable;
import java.net.URI;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.persistence.Column;
import javax.persistence.Index;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonSubTypes.Type;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.hibernate.validator.constraints.Email;

import de.shop.bestellverwaltung.domain.Bestellung;

@Entity
@XmlRootElement
@XmlSeeAlso({ Firmenkunde.class, Privatkunde.class })
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
	@Type(value = Privatkunde.class, name = AbstractKunde.PRIVATKUNDE),
	@Type(value = Firmenkunde.class, name = AbstractKunde.FIRMENKUNDE) })
@Table(name = "kunde", indexes = @Index(columnList = "nachname"))

@NamedQueries({
	@NamedQuery(name  = AbstractKunde.FIND_KUNDEN,
                query = "SELECT k"
				        + " FROM   AbstractKunde k"),
	@NamedQuery(name  = AbstractKunde.FIND_KUNDEN_ORDER_BY_ID,
		        query = "SELECT   k"
				        + " FROM  AbstractKunde k"
		                + " ORDER BY k.id"),
	@NamedQuery(name  = AbstractKunde.FIND_IDS_BY_PREFIX,
		        query = "SELECT   k.id"
		                + " FROM  AbstractKunde k"
		                + " WHERE CONCAT('', k.id) LIKE :" + AbstractKunde.PARAM_KUNDE_ID_PREFIX
		                + " ORDER BY k.id"),
	@NamedQuery(name  = AbstractKunde.FIND_KUNDEN_BY_NACHNAME,
	            query = "SELECT k"
				        + " FROM   AbstractKunde k"
	            		+ " WHERE  UPPER(k.nachname) = UPPER(:" + AbstractKunde.PARAM_KUNDE_NACHNAME + ")"),
	@NamedQuery(name  = AbstractKunde.FIND_NACHNAMEN_BY_PREFIX,
   	            query = "SELECT   DISTINCT k.nachname"
				        + " FROM  AbstractKunde k "
	            		+ " WHERE UPPER(k.nachname) LIKE UPPER(:"
	            		+ AbstractKunde.PARAM_KUNDE_NACHNAME_PREFIX + ")"),
   	@NamedQuery(name  = AbstractKunde.FIND_KUNDE_BY_EMAIL,
   	            query = "SELECT DISTINCT k"
   			            + " FROM   AbstractKunde k"
   			            + " WHERE  k.email = :" + AbstractKunde.PARAM_KUNDE_EMAIL),
    @NamedQuery(name  = AbstractKunde.FIND_KUNDEN_BY_PLZ,
	            query = "SELECT k"
				        + " FROM  AbstractKunde k"
			            + " WHERE k.adresse.plz = :" + AbstractKunde.PARAM_KUNDE_ADRESSE_PLZ),
	@NamedQuery(name = AbstractKunde.FIND_KUNDEN_BY_DATE,
			    query = "SELECT k"
			            + " FROM  AbstractKunde k"
			    		+ " WHERE k.seit = :" + AbstractKunde.PARAM_KUNDE_SEIT),
	@NamedQuery(name = AbstractKunde.FIND_PRIVATKUNDEN_FIRMENKUNDEN,
			    query = "SELECT k"
			            + " FROM  AbstractKunde k"
			    		+ " WHERE TYPE(k) IN (Privatkunde, Firmenkunde)")
})
@NamedEntityGraphs({
	@NamedEntityGraph(name = AbstractKunde.GRAPH_BESTELLUNGEN,
					  attributeNodes = @NamedAttributeNode("bestellungen")),
	@NamedEntityGraph(name = AbstractKunde.GRAPH_WARTUNGSVERTRAEGE,
					  attributeNodes = @NamedAttributeNode("wartungsvertraege"))
})

public abstract class AbstractKunde implements Serializable {
	private static final long serialVersionUID = 7401524595142572933L;
	
	
	public static final String PRIVATKUNDE = "P";
	public static final String FIRMENKUNDE = "F";
	
	//Pattern mit UTF-8 (statt Latin-1 bzw. ISO-8859-1) Schreibweise fuer Umlaute:
	private static final String NAME_PATTERN = "[A-Z\u00C4\u00D6\u00DC][a-z\u00E4\u00F6\u00FC\u00DF]+";
	private static final String NACHNAME_PREFIX = "(o'|von|von der|von und zu|van)?";
	
	public static final String NACHNAME_PATTERN = NACHNAME_PREFIX + NAME_PATTERN + "(-" + NAME_PATTERN + ")?";
	private static final int NACHNAME_LENGTH_MIN = 2;
	private static final int NACHNAME_LENGTH_MAX = 32;
	private static final int EMAIL_LENGTH_MAX = 128;
	private static final String PREFIX = "AbstractKunde.";
	public static final String FIND_KUNDEN = PREFIX + "findKunden";
	public static final String FIND_KUNDEN_ORDER_BY_ID = PREFIX + "findKundenOrderById";
	public static final String FIND_IDS_BY_PREFIX = PREFIX + "findIdsByPrefix";
	public static final String FIND_KUNDEN_BY_NACHNAME = PREFIX + "findKundenByNachname";
	public static final String FIND_NACHNAMEN_BY_PREFIX = PREFIX + "findNachnamenByPrefix";
	public static final String FIND_KUNDE_BY_EMAIL = PREFIX + "findKundeByEmail";
	public static final String FIND_KUNDEN_BY_PLZ = PREFIX + "findKundenByPlz";
	public static final String FIND_KUNDEN_BY_DATE = PREFIX + "findKundenByDate";
	public static final String FIND_PRIVATKUNDEN_FIRMENKUNDEN = PREFIX + "findPrivatkundenFirmenkunden";
	
	public static final String PARAM_KUNDE_ID = "kundeId";
	public static final String PARAM_KUNDE_ID_PREFIX = "idPrefix";
	public static final String PARAM_KUNDE_NACHNAME = "nachname";
	public static final String PARAM_KUNDE_NACHNAME_PREFIX = "nachnamePrefix";
	public static final String PARAM_KUNDE_ADRESSE_PLZ = "plz";
	public static final String PARAM_KUNDE_SEIT = "seit";
	public static final String PARAM_KUNDE_EMAIL = "email";
	
	public static final String GRAPH_BESTELLUNGEN = PREFIX + "bestellungen";
	public static final String GRAPH_WARTUNGSVERTRAEGE = PREFIX + "wartungsvertraege";


	@Id
	@GeneratedValue
	@Basic(optional = false)
	private Long id;
	
	@NotNull(message = "{kundenverwaltung.kunde.nachname.notNull}")
	@Size(min = NACHNAME_LENGTH_MIN, max = NACHNAME_LENGTH_MAX,
	      message = "{kundenverwaltung.kunde.nachname.length }")
	@Pattern(regexp = NACHNAME_PATTERN, message = "{kundenverwaltung.kunde.nachname.pattern}")
	private String nachname;
	
	//TODO validation
	private String vorname;
	
	@Email(message = "{kundenverwaltung.kunde.email.pattern}")
	@NotNull(message = "{kundenverwaltung.kunde.email.notNull }")
	@Size(max = EMAIL_LENGTH_MAX, message = "{kunde.email.length}")
	@Column(unique = true)
	private String email;
	
	@Valid
	@NotNull(message = "{kundenverwaltung.kunde.adresse.notNull}")
	@OneToOne(mappedBy = "kunde")
	private Adresse adresse;
	
	@XmlTransient
	private List<Bestellung> bestellungen;
	
	private URI bestellungenUri;

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getVorname() {
		return vorname;
	}
	
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	
	public String getNachname() {
		return nachname;
	}
	
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Adresse getAdresse() {
		return adresse;
	}
	
	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}
	
	public List<Bestellung> getBestellungen() {
		return bestellungen;
	}
	
	public void setBestellungen(List<Bestellung> bestellungen) {
		this.bestellungen = bestellungen;
	}

	public URI getBestellungenUri() {
		return bestellungenUri;
	}
	
	public void setBestellungenUri(URI bestellungenUri) {
		this.bestellungenUri = bestellungenUri;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adresse == null) ? 0 : adresse.hashCode());
		result = prime * result
				+ ((bestellungen == null) ? 0 : bestellungen.hashCode());
		result = prime * result
				+ ((bestellungenUri == null) ? 0 : bestellungenUri.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((nachname == null) ? 0 : nachname.hashCode());
		result = prime * result + ((vorname == null) ? 0 : vorname.hashCode());
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
		final AbstractKunde other = (AbstractKunde) obj;
		if (adresse == null) {
			if (other.adresse != null)
				return false;
		}
		else if (!adresse.equals(other.adresse))
			return false;
		if (bestellungen == null) {
			if (other.bestellungen != null)
				return false;
		}
		else if (!bestellungen.equals(other.bestellungen))
			return false;
		if (bestellungenUri == null) {
			if (other.bestellungenUri != null)
				return false;
		}
		else if (!bestellungenUri.equals(other.bestellungenUri))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		}
		else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		if (nachname == null) {
			if (other.nachname != null)
				return false;
		}
		else if (!nachname.equals(other.nachname))
			return false;
		if (vorname == null) {
			if (other.vorname != null)
				return false;
		}
		else if (!vorname.equals(other.vorname))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "AbstractKunde [id=" + id + ", vorname=" + vorname
				+ ", nachname=" + nachname + ", email=" + email + ", adresse="
				+ adresse + ", bestellungen=" + bestellungen
				+ ", bestellungenUri=" + bestellungenUri + "]";
	}
}
