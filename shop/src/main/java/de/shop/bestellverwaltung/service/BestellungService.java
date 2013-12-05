package de.shop.bestellverwaltung.service;

import java.util.List;
import java.util.Locale;

import javax.enterprise.context.Dependent;

import de.shop.bestellverwaltung.domain.Bestellung;
import de.shop.kundenverwaltung.domain.AbstractKunde;

@Dependent
public interface BestellungService {
	Bestellung findBestellungById(Long id);
	List<Bestellung> findBestellungenByKunde(AbstractKunde kunde);
	Bestellung createBestellung(Bestellung bestellung, AbstractKunde kunde, Locale locale);
}
