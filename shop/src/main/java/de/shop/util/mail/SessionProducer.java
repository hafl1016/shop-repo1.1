package de.shop.util.mail;

import javax.annotation.Resource;
import javax.enterprise.inject.Produces;
import javax.mail.Session;


public class SessionProducer {
	@Resource(lookup = "java:jboss/mail/Default")
	@Produces
	private Session session;
}
