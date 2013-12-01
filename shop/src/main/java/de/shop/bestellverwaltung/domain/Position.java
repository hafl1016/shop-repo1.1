package de.shop.bestellverwaltung.domain;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
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
		
        private int bestellid;
        
        @XmlTransient
        @Valid
        private Artikel artikel;
        
        @Pattern (regexp = "[1-9]?", message = "{bestellverwaltung.position.anzahl.pattern}")
        private int anzahl;
        
        public long getId() {
                return id;
        }
        
        public void setId(long id) {
                this.id = id;
        }
        
        public int getBestellid() {
                return bestellid;
        }
        
        public void setBestellid(int bestellid) {
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
        
        public void setAnzahl(int anzahl) {
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
