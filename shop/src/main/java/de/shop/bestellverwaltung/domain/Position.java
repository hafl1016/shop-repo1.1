package de.shop.bestellverwaltung.domain;

import javax.xml.bind.annotation.XmlRootElement;

import de.shop.artikelverwaltung.domain.AbstractArtikel;

@XmlRootElement
public class Position {
        private int id;
        private int bestellid;
        private AbstractArtikel artikel;
        private int anzahl;
        public int getId() {
                return id;
        }
        public void setId(int id) {
                this.id = id;
        }
        public int getBestellid() {
                return bestellid;
        }
        public void setBestellid(int bestellid) {
                this.bestellid = bestellid;
        }
        public AbstractArtikel getArtikel() {
                return artikel;
        }
        public void setArtikel(AbstractArtikel artikel) {
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
                return "Position [id=" + id + ", bestellid=" + bestellid + ", artikel="
                                + artikel + ", anzahl=" + anzahl + "]";
        }
        @Override
        public int hashCode() {
                final int prime = 31;
                int result = 1;
                result = prime * result + anzahl;
                result = prime * result + ((artikel == null) ? 0 : artikel.hashCode());
                result = prime * result + bestellid;
                result = prime * result + id;
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
                Position other = (Position) obj;
                if (anzahl != other.anzahl)
                        return false;
                if (artikel == null) {
                        if (other.artikel != null)
                                return false;
                } else if (!artikel.equals(other.artikel))
                        return false;
                if (bestellid != other.bestellid)
                        return false;
                if (id != other.id)
                        return false;
                return true;
        }
}