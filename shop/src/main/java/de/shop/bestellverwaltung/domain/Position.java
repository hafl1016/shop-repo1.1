package de.shop.bestellverwaltung.domain;

import java.io.Serializable;
import java.net.URI;

import javax.persistence.Basic;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import de.shop.artikelverwaltung.domain.*;


@XmlRootElement
public class Position implements Serializable{

		private static final long serialVersionUID = 2640612708567145520L;

		@Id
		@GeneratedValue
		@Basic(optional = false)
        private long id;
		
        private Integer bestellid;
        
        @XmlTransient
        @Valid
        private Artikel artikel;
        
        @NotNull(message = "{bestellverwaltung.position.anzahl.notNull}")
        @Min(value=1, message = "{bestellverwaltung.position.anzahl.min}")
        private Integer anzahl;
        
        private URI ArtikelUri;
        
        public URI getArtikelUri() {
			return ArtikelUri;
		}

		public void setArtikelUri(URI artikelUri) {
			ArtikelUri = artikelUri;
		}

		public long getId() {
                return id;
        }
        
        public void setId(long id) {
                this.id = id;
        }
        
        public int getBestellid() {
                return bestellid;
        }
        
        public void setBestellid(Integer bestellid) {
                this.bestellid = bestellid;
        }
        
        public Artikel getArtikel() {
                return artikel;
        }
        
        public void setArtikel(Artikel artikel) {
                this.artikel = artikel;
        }
        
        public int getAnzahl() {
                return anzahl;
        }
        
        public void setAnzahl(Integer anzahl) {
                this.anzahl = anzahl;
        }
        
        public Position() {
                super();
        }
        
		@Override
		public String toString() {
			return "Position [id=" + id + ", bestellid=" + bestellid
					+ ", artikel=" + artikel + ", anzahl=" + anzahl + "]";
		}


}
