package de.shop.bestellverwaltung.domain;

import javax.xml.bind.annotation.XmlRootElement;


import artikelverwaltung.domain.*;


@XmlRootElement
public class Position {
        private int id;
        private int bestellid;
        private Artikel artikel;
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


}