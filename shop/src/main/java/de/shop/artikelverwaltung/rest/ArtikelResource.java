package de.shop.artikelverwaltung.rest;

import static de.shop.util.Constants.SELF_LINK;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static javax.ws.rs.core.MediaType.TEXT_XML;
import static de.shop.util.Constants.KEINE_ID;

import java.lang.invoke.MethodHandles;
import java.net.URI;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.jboss.logging.Logger;

import de.shop.artikelverwaltung.domain.Artikel;
import de.shop.artikelverwaltung.service.ArtikelService;
import de.shop.util.interceptor.Log;
import de.shop.util.rest.UriHelper;


/**
 * @author <a href="mailto:Juergen.Zimmermann@HS-Karlsruhe.de">J&uuml;rgen Zimmermann</a>
 */
@Path("/artikel")
@Produces({ APPLICATION_JSON, APPLICATION_XML + ";qs=0.75", TEXT_XML + ";qs=0.5" })
@Consumes
@RequestScoped
@Transactional
@Log
public class ArtikelResource {
	private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());
	
	@Context
	private UriInfo uriInfo;
	
	@Inject
	private ArtikelService as;
	
	@Inject
	private UriHelper uriHelper;

	
	@GET
	@Path("{id:[1-9][0-9]*}")
	public Response findArtikelById(@PathParam("id") Long id) {
		final Artikel artikel = as.findArtikelById(id);
		return Response.ok(artikel)
                       .links(getTransitionalLinks(artikel, uriInfo))
                       .build();
	}
	
	@POST
	@Consumes({APPLICATION_JSON, APPLICATION_XML, TEXT_XML})
	@Produces
	public Response createArtikel(@Valid Artikel artikel) {
		artikel.setId(KEINE_ID);
		
		artikel = as.createArtikel(artikel);
		LOGGER.tracef("Artikel: %s", artikel);
		
		return Response.created(getUriArtikel(artikel,uriInfo))
					   .build();
	}
	
	@PUT
	@Consumes({ APPLICATION_JSON, APPLICATION_XML, TEXT_XML })
	@Produces
	public void updateArtikel(@Valid Artikel artikel) {
		final Artikel origArtikel = as.findArtikelById(artikel.getId());
		LOGGER.tracef("Artikel vorher: %s", origArtikel);
		
		origArtikel.setValues(artikel);
		LOGGER.tracef("Artikel nachher: %s", origArtikel);
		
		as.updateArtikel(origArtikel);
	}
	
	private Link[] getTransitionalLinks(Artikel artikel, UriInfo uriInfo) {
		final Link self = Link.fromUri(getUriArtikel(artikel, uriInfo))
                              .rel(SELF_LINK)
                              .build();

		return new Link[] { self };
	}
	
	public URI getUriArtikel(Artikel artikel, UriInfo uriInfo) {
		return uriHelper.getUri(ArtikelResource.class, "findArtikelById", artikel.getId(), uriInfo);
	}
}
