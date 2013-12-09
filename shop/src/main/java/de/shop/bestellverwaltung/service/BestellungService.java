package de.shop.bestellverwaltung.service;


import java.util.List;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import de.shop.bestellverwaltung.domain.Bestellung;
import de.shop.bestellverwaltung.domain.Position;
import de.shop.kundenverwaltung.domain.AbstractKunde;
import de.shop.util.Mock;

@Dependent
public class BestellungService {
		
		
		@Inject
		@NeueBestellung
		private transient Event<Bestellung> event;
		
		public Bestellung findBestellungById(Long id) {
			// TODO Datenbanzugriffsschicht statt Mock
			return Mock.findBestellungById(id);
		}

		public List<Bestellung> findBestellungenByKunde(AbstractKunde kunde) {
			// TODO Datenbanzugriffsschicht statt Mock
			return Mock.findBestellungenByKunde(kunde);
		}
		//TODO eigentlich noch AbstractKunde kunde und Locale local als parameter, verstehe nicht warum
		public Bestellung createBestellung(Bestellung bestellung) {
			// TODO Datenbanzugriffsschicht statt Mock
			bestellung = Mock.createBestellung(bestellung);
			event.fire(bestellung);
			
			return bestellung;
		}
		
		public Position createPosition(Position pos) {
			//TODO Datenbankzugriffsschicht statt mock
			pos = Mock.createPositionen(pos);
			return pos;
		}
		public List<Position> findallPositionen(int id) {
			final List<Position> erg = Mock.findAllPositionen(id);
			return erg;
			
		}

		public Position updatePosition(Position pos) {
			pos = Mock.updatePosition(pos);
			return pos;
		}
		
}
		
		
		







