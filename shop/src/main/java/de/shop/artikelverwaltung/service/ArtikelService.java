package de.shop.artikelverwaltung.service;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.validation.constraints.NotNull;

import de.shop.artikelverwaltung.domain.Artikel;
import de.shop.util.interceptor.Log;
import de.shop.util.Mock;


@Dependent
@Log
public class ArtikelService implements Serializable {
	private static final long serialVersionUID = -5105686816948437276L;
	
	private static final int MAX_ID = 10;

	@NotNull(message = "{artikel.notFound.id}")
	public Artikel findArtikelById(int id) {
		// TODO id pruefen		
		if (id > MAX_ID)
			return null;
		// TODO Datenbanzugriffsschicht statt Mock
		return Mock.findArtikelById(id);
	}
	
	public List<Artikel> findAllArtikel() {
	
		return Mock.findAllArtikel();
	}
	
	public Artikel createArtikel(Artikel artikel) {
		
		return Mock.createArtikel(artikel);
	}
	
	public void updateArtikel(Artikel artikel) {
		
		Mock.updateArtikel(artikel);
	}
}
