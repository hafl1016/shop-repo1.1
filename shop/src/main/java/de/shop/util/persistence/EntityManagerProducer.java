package de.shop.util.persistence;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * marius!!!!@author <a href="mailto:Juergen.Zimmermann@HS-Karlsruhe.de">J&uuml;rgen Zimmermann</a>
 */
@Dependent
public class EntityManagerProducer {
	@PersistenceContext
	@Produces
	private EntityManager em;
}
