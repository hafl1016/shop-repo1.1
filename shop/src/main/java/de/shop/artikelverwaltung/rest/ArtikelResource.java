package de.shop.artikelverwaltung.rest;


import static de.shop.util.Constants.SELF_LINK;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static javax.ws.rs.core.MediaType.TEXT_XML;


//import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.util.List;


//import javax.annotation.PostConstruct;
//import javax.annotation.PreDestroy;
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

import de.shop.artikelverwaltung.domain.*;
import de.shop.artikelverwaltung.service.ArtikelService;
//import org.jboss.logging.Logger;
//import de.shop.kundenverwaltung.domain.AbstractKunde;
//import de.shop.artikelverwaltung.service.ArtikelService;
//import de.shop.util.interceptor.Log;
import de.shop.util.rest.NotFoundException;
import de.shop.util.rest.UriHelper;


@Path("/artikel")
@Produces({ APPLICATION_JSON, APPLICATION_XML + ";qs=0.75", TEXT_XML + ";qs=0.5" })
@Consumes
// @Log
public class ArtikelResource {
        @Context
        private UriInfo uriInfo;
      
        
        // private static final String NOT_FOUND_ID = "artikel.notFound.id";

        @Inject
        private ArtikelService as;

        @Inject
        private UriHelper uriHelper;

        // @PostConstruct
        // private void postConstruct() {
        // LOGGER.debugf("CDI-faehiges Bean %s wurde erzeugt", this);
        // }
        //
        // @PreDestroy
        // private void preDestroy() {
        // LOGGER.debugf("CDI-faehiges Bean %s wird geloescht", this);
        // }

        @GET
        public Response findAllArtikel() {
                // TODO Anwendungskern statt Mock, Verwendung von Locale
                final List<Artikel> artikelList = as.findAllArtikel();
                if (artikelList.isEmpty())
                        throw new NotFoundException("Es wurden keine Artikel gefunden.");
                return Response.ok(
                                new GenericEntity<List<? extends Artikel>>(artikelList) {
                                }).build();
        }

        @GET
        @Path("{id:[1-9][0-9]*}")
        public Response findArtikelById(@PathParam("id") int id) {
                final Artikel artikel = as.findArtikelById(id);
                if (artikel == null) {
                        throw new NotFoundException("Kein Artikel mit der Artikelnummer "
                                        + id + " gefunden.");
                }

                return Response.ok(artikel)
                               .links(getTransitionalLinks(artikel, uriInfo))//TODO Was macht das?
                		.build();
        }
        @POST
        @Consumes({ APPLICATION_JSON, APPLICATION_XML, TEXT_XML })
        @Produces
        public Response createArtikel(@Valid Artikel artikel) {
                // TODO Anwendungskern statt Mock, Verwendung von Locale
                artikel = as.createArtikel(artikel);
                return Response.created(getUriArtikel(artikel, uriInfo)).build();
        }
    	

       private Link[] getTransitionalLinks(Artikel artikel, UriInfo uriInfo) {
                final Link self = Link.fromUri(getUriArtikel(artikel, uriInfo))
                                .rel(SELF_LINK).build();

                return new Link[] {self };
                }

        public URI getUriArtikel(Artikel artikel, UriInfo uriInfo) {
                return uriHelper.getUri(ArtikelResource.class, "findArtikelById",
                                artikel.getId(), uriInfo);
        }

    	
        @PUT
        @Consumes({ APPLICATION_JSON, APPLICATION_XML, TEXT_XML })
        @Produces
        public void updateArtikel(Artikel artikel) {
                as.updateArtikel(artikel);
        }

}
