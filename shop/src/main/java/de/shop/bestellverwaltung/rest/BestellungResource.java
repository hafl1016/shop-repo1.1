package de.shop.bestellverwaltung.rest;

import static de.shop.util.Constants.SELF_LINK;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static javax.ws.rs.core.MediaType.TEXT_XML;

import java.net.URI;
import java.util.GregorianCalendar;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import de.shop.bestellverwaltung.domain.Bestellung;
import de.shop.bestellverwaltung.domain.Position;
import de.shop.bestellverwaltung.service.BestellungService;
import de.shop.kundenverwaltung.domain.AbstractKunde;
import de.shop.kundenverwaltung.rest.KundeResource;
import de.shop.util.Mock;
import de.shop.util.rest.UriHelper;
import de.shop.util.rest.NotFoundException;

@Path("/bestellungen")
@Produces({ APPLICATION_JSON, APPLICATION_XML + ";qs=0.75", TEXT_XML + ";qs=0.5" })
@Consumes
@RequestScoped
public class BestellungResource {
	
	
	@Context
	private UriInfo uriInfo;
	
	@Inject
	private BestellungService bs;
	
	@Inject
	private KundeResource kundeResource;
	
	@Inject
	private UriHelper uriHelper;
	
	
	@GET
	@Path("{id:[1-9][0-9]*}")
	public Response findBestellungById(@PathParam("id") Long id) {
		// TODO Anwendungskern statt Mock, Verwendung von Locale
		final Bestellung bestellung = bs.findBestellungById(id);
		if (bestellung == null) {
			throw new NotFoundException("Keine Bestellung mit der ID " + id + " gefunden.");
		}
		
		setStructuralLinks(bestellung, uriInfo);
		
		// Link-Header setzen
		final Response response = Response.ok(bestellung)
                                          .links(getTransitionalLinks(bestellung, uriInfo))
                                          .build();
		
		return response;
	}
	
	public void setStructuralLinks(Bestellung bestellung, UriInfo uriInfo) {
		// URI fuer Kunde setzen
		//TODO URI Kunde abschauen für URIArtikel
		final AbstractKunde kunde = bestellung.getKunde();
		if (kunde != null) {
			final URI kundeUri = kundeResource.getUriKunde(bestellung.getKunde(), uriInfo);
			bestellung.setKundeUri(kundeUri);
		}
	}
	
	private Link[] getTransitionalLinks(Bestellung bestellung, UriInfo uriInfo) {
		final Link self = Link.fromUri(getUriBestellung(bestellung, uriInfo))
                              .rel(SELF_LINK)
                              .build();
		return new Link[] {self};
	}
	
	public URI getUriBestellung(Bestellung bestellung, UriInfo uriInfo) {
		return uriHelper.getUri(BestellungResource.class, "findBestellungById", bestellung.getId(), uriInfo);
	}
	
	public URI getUriBestellung(Position pos, UriInfo uriInfo) {
		return uriHelper.getUri(BestellungResource.class, "findPositionenByBestellId", pos.getId(), uriInfo);
	}
	
	@POST
	@Consumes({APPLICATION_JSON, APPLICATION_XML, TEXT_XML })
	@Produces
	public Response createBestellung(@Valid Bestellung best) {
		// TODO Anwendungskern statt Mock, Verwendung von Locale
		
		
		best = bs.createBestellung(best);
		GregorianCalendar date= new GregorianCalendar();
		Long dong = date.getTimeInMillis();
		date.setTimeInMillis(dong);
				
		best.setBestelldatum(date);
		return Response.created(getUriBestellung(best, uriInfo))
			           .build();
	}
	
	@GET
	@Path("{id:[1-9][0-9]*}/positionen")
	public Response findPositionenByBestellId(@PathParam("id") int id) {
		// TODO Anwendungskern statt Mock, Verwendung von Locale
		final List<Position> poslist = bs.findallPositionen(id);
		if (poslist == null) {
			throw new NotFoundException("Keine Positionen mit der BestellID " + id + " gefunden.");
		}
              
        return Response.ok(new GenericEntity<List<? extends Position>>(poslist) { })
        		.build();
	}
	
	
	
	@POST
	@Consumes({APPLICATION_JSON, APPLICATION_XML, TEXT_XML })
	@Produces
	@Path("{id:[1-9][0-9]*}/positionen")
	public Response createPosition(@Valid Position pos) {
		// TODO Anwendungskern statt Mock, Verwendung von Locale
		pos = bs.createPosition(pos);
	
		return Response.created(getUriBestellung(pos, uriInfo))
			           .build();
	}
	
	@PUT
	@Consumes({APPLICATION_JSON, APPLICATION_XML, TEXT_XML })
	@Produces
	@Path("{id:[1-9][0-9]*}/positionen")
	public void updatePosition(Position pos) {
		// TODO Anwendungskern statt Mock, Verwendung von Locale
		pos= Mock.updatePositionen(pos);
	
		
	}
}
	
