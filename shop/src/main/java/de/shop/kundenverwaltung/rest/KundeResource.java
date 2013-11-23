package de.shop.kundenverwaltung.rest;

import static de.shop.util.Constants.ADD_LINK;
import static de.shop.util.Constants.FIRST_LINK;
import static de.shop.util.Constants.LAST_LINK;
import static de.shop.util.Constants.REMOVE_LINK;
import static de.shop.util.Constants.SELF_LINK;
import static de.shop.util.Constants.UPDATE_LINK;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static javax.ws.rs.core.MediaType.TEXT_XML;

import java.net.URI;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import de.shop.bestellverwaltung.domain.Bestellung;
import de.shop.bestellverwaltung.rest.BestellungResource;
import de.shop.kundenverwaltung.domain.AbstractKunde;
import de.shop.util.Mock;
import de.shop.util.rest.UriHelper;
import de.shop.util.rest.NotFoundException;

@Path("/kunden")
@Produces({ APPLICATION_JSON, APPLICATION_XML + ";qs=0.75", TEXT_XML + ";qs=0.5" })
@Consumes
public class KundeResource {
	
	
	public static final String KUNDEN_ID_PATH_PARAM = "kundeId";
	public static final String KUNDEN_NACHNAME_QUERY_PARAM = "nachname";
	
	@Context
	private UriInfo uriInfo;
	
	@Inject
	private BestellungResource bestellungResource;
	
	@Inject
	private UriHelper uriHelper;
	
	@GET
	@Produces({ TEXT_PLAIN, APPLICATION_JSON })
	@Path("version")
	public String getVersion() {
		return "1.0";
	}
	
	@GET
	@Path("{" + KUNDEN_ID_PATH_PARAM + ":[1-9][0-9]*}")
	public Response findKundeById(@PathParam(KUNDEN_ID_PATH_PARAM) Long id) {
		// TODO Anwendungskern statt Mock, Verwendung von Locale
		final AbstractKunde kunde = Mock.findKundeById(id);
		if (kunde == null) {
			throw new NotFoundException("Kein Kunde mit der ID " + id + " gefunden.");
		}
		
		setStructuralLinks(kunde, uriInfo);
		
		return Response.ok(kunde)
                       .links(getTransitionalLinks(kunde, uriInfo))
                       .build();
	}
	
	public void setStructuralLinks(AbstractKunde kunde, UriInfo uriInfo) {
		// URI fuer Bestellungen setzen
		final URI uri = getUriBestellungen(kunde, uriInfo);
		kunde.setBestellungenUri(uri);
	}
	
	private URI getUriBestellungen(AbstractKunde kunde, UriInfo uriInfo) {
		return uriHelper.getUri(KundeResource.class, "findBestellungenByKundeId", kunde.getId(), uriInfo);
	}		
	
	public Link[] getTransitionalLinks(AbstractKunde kunde, UriInfo uriInfo) {
		final Link self = Link.fromUri(getUriKunde(kunde, uriInfo))
	                          .rel(SELF_LINK)
	                          .build();
		
		final Link add = Link.fromUri(uriHelper.getUri(KundeResource.class, uriInfo))
                             .rel(ADD_LINK)
                             .build();

		final Link update = Link.fromUri(uriHelper.getUri(KundeResource.class, uriInfo))
                                .rel(UPDATE_LINK)
                                .build();

		final Link remove = Link.fromUri(uriHelper.getUri(KundeResource.class, "deleteKunde", kunde.getId(), uriInfo))
                                .rel(REMOVE_LINK)
                                .build();
		
		return new Link[] {self, add, update, remove};
	}

	
	public URI getUriKunde(AbstractKunde kunde, UriInfo uriInfo) {
		return uriHelper.getUri(KundeResource.class, "findKundeById", kunde.getId(), uriInfo);
	}

	
	@GET
	public Response findKundenByNachname(@QueryParam(KUNDEN_NACHNAME_QUERY_PARAM) String nachname) {
		List<? extends AbstractKunde> kunden = null;
		if (nachname != null) {
			// TODO Anwendungskern statt Mock, Verwendung von Locale
			kunden = Mock.findKundenByNachname(nachname);
			if (kunden.isEmpty()) {
				throw new NotFoundException("Kein Kunde mit Nachname " + nachname + " gefunden.");
			}
		}
		else {
			// TODO Anwendungskern statt Mock, Verwendung von Locale
			kunden = Mock.findAllKunden();
			if (kunden.isEmpty()) {
				throw new NotFoundException("Keine Kunden vorhanden.");
			}
		}
		
		for (AbstractKunde k : kunden) {
			setStructuralLinks(k, uriInfo);
		}
		
		return Response.ok(new GenericEntity<List<? extends AbstractKunde>>(kunden) { } )
                       .links(getTransitionalLinksKunden(kunden, uriInfo))
                       .build();
	}
	
	private Link[] getTransitionalLinksKunden(List<? extends AbstractKunde> kunden, UriInfo uriInfo) {
		if (kunden == null || kunden.isEmpty()) {
			return null;
		}
		
		final Link first = Link.fromUri(getUriKunde(kunden.get(0), uriInfo))
	                           .rel(FIRST_LINK)
	                           .build();
		final int lastPos = kunden.size() - 1;
		final Link last = Link.fromUri(getUriKunde(kunden.get(lastPos), uriInfo))
                              .rel(LAST_LINK)
                              .build();
		
		return new Link[] {first, last};
	}
	
	@GET
	@Path("{id:[1-9][0-9]*}/bestellungen")
	public Response findBestellungenByKundeId(@PathParam("id") Long kundeId) {
		// TODO Anwendungskern statt Mock, Verwendung von Locale
		final AbstractKunde kunde = Mock.findKundeById(kundeId);
		final List<Bestellung> bestellungen = Mock.findBestellungenByKunde(kunde);
		if (bestellungen.isEmpty()) {
			throw new NotFoundException("Zur ID " + kundeId + " wurden keine Bestellungen gefunden");
		}
		
		// URIs innerhalb der gefundenen Bestellungen anpassen
		for (Bestellung bestellung : bestellungen) {
			bestellungResource.setStructuralLinks(bestellung, uriInfo);
		}
		
		return Response.ok(new GenericEntity<List<Bestellung>>(bestellungen) { } )
                       .links(getTransitionalLinksBestellungen(bestellungen, kunde, uriInfo))
                       .build();
	}
	
	private Link[] getTransitionalLinksBestellungen(List<Bestellung> bestellungen, AbstractKunde kunde, UriInfo uriInfo) {
		if (bestellungen == null || bestellungen.isEmpty()) {
			return new Link[0];
		}
		
		final Link self = Link.fromUri(getUriBestellungen(kunde, uriInfo))
                              .rel(SELF_LINK)
                              .build();
		
		final Link first = Link.fromUri(bestellungResource.getUriBestellung(bestellungen.get(0), uriInfo))
	                           .rel(FIRST_LINK)
	                           .build();
		final int lastPos = bestellungen.size() - 1;
		
		final Link last = Link.fromUri(bestellungResource.getUriBestellung(bestellungen.get(lastPos), uriInfo))
                              .rel(LAST_LINK)
                              .build();
		
		return new Link[] {self, first, last};
	}
	
	@POST
	@Consumes({APPLICATION_JSON, APPLICATION_XML, TEXT_XML })
	@Produces
	public Response createKunde(AbstractKunde kunde) {
		// TODO Anwendungskern statt Mock, Verwendung von Locale
		kunde = Mock.createKunde(kunde);
		return Response.created(getUriKunde(kunde, uriInfo))
			           .build();
	}
	
	@PUT
	@Consumes({APPLICATION_JSON, APPLICATION_XML, TEXT_XML })
	@Produces
	public void updateKunde(AbstractKunde kunde) {
		// TODO Anwendungskern statt Mock, Verwendung von Locale
		Mock.updateKunde(kunde);
	}
	
	@DELETE
	@Path("{id:[1-9][0-9]*}")
	@Produces
	public void deleteKunde(@PathParam("id") Long kundeId) {
		// TODO Anwendungskern statt Mock, Verwendung von Locale
		Mock.deleteKunde(kundeId);
	}
}
