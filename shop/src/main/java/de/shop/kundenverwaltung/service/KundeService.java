package de.shop.kundenverwaltung.service;

import java.util.List;

import javax.enterprise.context.Dependent;

import de.shop.kundenverwaltung.domain.AbstractKunde;
import de.shop.util.Mock;

@Dependent
public class KundeService { 
	
	public AbstractKunde findKundeById(Long id) {
		if (id == null) {
			return null;
		}
		// TODO Datenbanzugriffsschicht statt Mock
		final AbstractKunde kunde = Mock.findKundeById(id);
		return kunde;
	}
	
	public List<AbstractKunde> findAllKunden() {
		// TODO Datenbanzugriffsschicht statt Mock
		final List<AbstractKunde> kunden = Mock.findAllKunden();
		return kunden;
	}
	
	public List<AbstractKunde> findKundenByNachname(String nachname) {
		// TODO Datenbanzugriffsschicht statt Mock
		final List<AbstractKunde> kunden = Mock.findKundenByNachname(nachname);
		return kunden;
	}

	public AbstractKunde  createKunde(AbstractKunde kunde) {
		if (kunde == null) {
			return kunde;
		}

		// Pruefung, ob die Email-Adresse schon existiert
		// TODO Datenbanzugriffsschicht statt Mock
		//TODO find kunde by email
		kunde = Mock.createKunde(kunde);

		return kunde;
	}

	public AbstractKunde updateKunde(AbstractKunde kunde) {
		if (kunde == null) {
			return null;
		}
		//TODO passt das oder wie?
		/*// Pruefung, ob die Email-Adresse schon existiert
		final AbstractKunde vorhandenerKunde = Mock.findKundeByEmail(kunde.getEmail());

		// Gibt es die Email-Adresse bei einem anderen, bereits vorhandenen Kunden?
		if (vorhandenerKunde.getId().longValue() != kunde.getId().longValue()) {
			throw new EmailExistsException(kunde.getEmail());
		}*/
		
		// TODO Datenbanzugriffsschicht statt Mock
		Mock.updateKunde(kunde);
		
		return kunde;
	}

	
}


