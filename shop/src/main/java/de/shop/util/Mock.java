package de.shop.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import de.shop.artikelverwaltung.domain.Artikel;
import de.shop.bestellverwaltung.domain.Bestellung;
import de.shop.bestellverwaltung.domain.Position;
import de.shop.kundenverwaltung.domain.AbstractKunde;
import de.shop.kundenverwaltung.domain.Adresse;
import de.shop.kundenverwaltung.domain.Firmenkunde;
import de.shop.kundenverwaltung.domain.HobbyType;
import de.shop.kundenverwaltung.domain.Privatkunde;

public final class Mock {
	
	
	private static final int MAX_ID = 99;
	private static final int MAX_KUNDEN = 8;
	private static final int MAX_BESTELLUNGEN = 4;
	private static final int MAX_ARTIKEL = 10;
	private static final long MAX_POSITIONEN = 2;


	public static AbstractKunde findKundeById(Long id) {
		
		if (id > MAX_ID) {
			return null;
		}
		
		final AbstractKunde kunde = id % 2 == 1 ? new Privatkunde() : new Firmenkunde();
		kunde.setId(id);
		kunde.setVorname("Max");
		kunde.setNachname("Mustermann");
		kunde.setEmail("" + id + "@hska.de");
		
		final Adresse adresse = new Adresse();
		adresse.setId(id + 1);
		adresse.setPlz("12345");
		adresse.setOrt("Musterort");
		adresse.setStrasse("Musterstrasse");
		adresse.setHausnr("" + id);
		adresse.setKunde(kunde);
		kunde.setAdresse(adresse);
		
		if (kunde.getClass().equals(Privatkunde.class)) {
			
			final Privatkunde privatkunde = (Privatkunde) kunde;
			final Set<HobbyType> hobbies = new HashSet<>();
			hobbies.add(HobbyType.LESEN);
			hobbies.add(HobbyType.REISEN);
			privatkunde.setHobbies(hobbies);
			
		}
		
		return kunde;
	}

	public static List<AbstractKunde> findAllKunden() {
		
		final int anzahl = MAX_KUNDEN;
		final List<AbstractKunde> kunden = new ArrayList<>(anzahl);
		for (int i = 1; i <= anzahl; i++) {
			final AbstractKunde kunde = findKundeById(Long.valueOf(i));
			kunden.add(kunde);			
		}
		return kunden;
	}

	public static List<AbstractKunde> findKundenByNachname(String nachname) {
		final int anzahl = nachname.length();
		final List<AbstractKunde> kunden = new ArrayList<>(anzahl);
		for (int i = 1; i <= anzahl; i++) {
			final AbstractKunde kunde = findKundeById(Long.valueOf(i));
			kunde.setNachname(nachname);
			kunden.add(kunde);			
		}
		return kunden;
	}
	

	public static List<Bestellung> findBestellungenByKunde(@Valid AbstractKunde kunde) {
		// Beziehungsgeflecht zwischen Kunde und Bestellungen aufbauen
		final int anzahl = kunde.getId().intValue() % MAX_BESTELLUNGEN + 1;  // 1, 2, 3 oder 4 Bestellungen
		final List<Bestellung> bestellungen = new ArrayList<>(anzahl);
		for (int i = 1; i <= anzahl; i++) {
			final Bestellung bestellung = findBestellungById(Long.valueOf(i));
			bestellung.setKunde(kunde);
			bestellungen.add(bestellung);			
		}
		kunde.setBestellungen(bestellungen);
		
		return bestellungen;
	}

	public static Bestellung findBestellungById(Long id) {
		if (id > MAX_ID) {
			return null;
		}
		final int it = id.intValue();
		final AbstractKunde kunde = findKundeById(id + 1);  // andere ID fuer den Kunden

		final Bestellung bestellung = new Bestellung();
		bestellung.setId(id);
		bestellung.setAusgeliefert(false);
		bestellung.setKunde(kunde);
		  bestellung.setPositionen(findAllPositionen(it));
		
		return bestellung;
	}
	
	public static List<Position> findAllPositionen(int id) {
          
		  final int anzahl = 1;
          final List<Position> positionList = new ArrayList<>(anzahl);
          for (int i = 1; i <= anzahl; i++) {
                  final Position position = findPositionById(i, id);
                  positionList.add(position);
          }
          return positionList;
	}
  
	public static Position findPositionById(int id, int bid) {
          if (id > MAX_POSITIONEN || bid > MAX_BESTELLUNGEN) {
                  return null;
          }

          final Position position = new Position();
          position.setId(id);
          position.setAnzahl(id + 3);
          position.setArtikel(findArtikelById(id));
          position.setBestellid(bid);

          return position;
	}

	public static AbstractKunde createKunde(@Valid AbstractKunde kunde) {
		// Neue IDs fuer Kunde und zugehoerige Adresse
		// Ein neuer Kunde hat auch keine Bestellungen
		//TODO CreatePrivat&CreateGeschäftskunde
		final String nachname = kunde.getNachname();
		kunde.setId(Long.valueOf(nachname.length()));
		final Adresse adresse = kunde.getAdresse();
		adresse.setId((Long.valueOf(nachname.length())) + 1);
		adresse.setKunde(kunde);
		kunde.setBestellungen(null);
		kunde.setAdresse(adresse);
		
		System.out.println("Neuer Kunde: " + kunde);
		return kunde;
	}
	
    public static Artikel createArtikel(@Valid Artikel artikel) {

        // TODO Artikelnummer anpassen (%3=0)
        final String name = artikel.getBezeichnung();
        final BigDecimal pr = artikel.getPreis();
        
    	artikel.setBezeichnung(name);
        artikel.setId(2);
        artikel.setPreis(pr);

        System.out.println("Neuer Artikel: " + artikel);
        return artikel;
    }
    
    public static Bestellung createBestellung(@Valid Bestellung best) {
		/*final GregorianCalendar bestdat = best.getBestelldatum();
		best.setBestelldatum(bestdat);*/
		//TODO evt. Verbindung zu Posten herstellen (bzgl. POST)
        final long nummer =11;
        best.setId(nummer);   
        System.out.println("Neue Bestellung: " + best);
        return best;
    }

	public static void updateKunde(@Valid AbstractKunde kunde) {
		System.out.println("Aktualisierter Kunde: " + kunde);
	}

	public static void deleteKunde(Long kundeId) {
		System.out.println("Kunde mit ID=" + kundeId + " geloescht");
	}
	
	public static Artikel findArtikelById(int id) {
        if (id > MAX_ARTIKEL) {
                return null;
        }

        final Artikel artikel = new Artikel();
        
        artikel.setId(id);
        artikel.setBezeichnung("Fahrrad");
        artikel.setPreis(new BigDecimal("213.3"));
        return artikel;
	
	}
	public static List<Artikel> findAllArtikel() {
         final int anzahl = MAX_ARTIKEL;
         final List<Artikel> artikelList = new ArrayList<>(anzahl);
         for (int i = 1; i <= anzahl; i++) {
                 final Artikel artikel = findArtikelById(i);
                 artikelList.add(artikel);
         }
         return artikelList;
	}
	
	private Mock() { /**/ }

	public static void updateArtikel(@Valid Artikel artikel) {
		System.out.println("Aktualisierter Artikel: " + artikel);
	}

	public static Position createPositionen(@Valid Position pos) {
		final Artikel posart = pos.getArtikel();
		pos.setArtikel(posart);
		final int posanzahl = pos.getAnzahl();
		pos.setAnzahl(posanzahl);
		final int posid = pos.getAnzahl() * 3;
		pos.setId(posid);
	    System.out.println("Neue Position: " + pos);
	    return pos;
	}

	public static Position updatePositionen(@Valid Position pos) {
		System.out.println("Aktualisierte Postition: " + pos);
		return pos;
	}
	
}
