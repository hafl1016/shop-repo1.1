package de.shop.util.rest;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static org.jboss.resteasy.api.validation.ConstraintType.Type.PARAMETER;

import java.util.ArrayList;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.api.validation.ResteasyConstraintViolation;
import org.jboss.resteasy.api.validation.ViolationReport;

import de.shop.util.interceptor.Log;

/**
 * Konvertierung diverser Exceptions in eine halbwegs vernuenftige Fehlermeldungen,
 * falls die Exceptions nicht durch spezfische ExceptionMapper abgebildet werden
 * 
 * Wesentlicher Code von:
 * http://securesoftwaredev.com/2013/08/19/how-to-implement-input-validation-for-rest-resources
 * 
 * Ergaenzt um ViolationReport und ResteasyConstraintViolation
 */
@Provider
@Log
public class DefaultExceptionMapper implements ExceptionMapper<Throwable> {
	@Override
	public Response toResponse(Throwable exception) {
		final Throwable badRequestException = getBadRequestException(exception);
		if (badRequestException  instanceof ConstraintViolationException) {
			final ViolationReport violationReport =
					              toViolationReport((ConstraintViolationException) badRequestException);
			return Response.status(BAD_REQUEST)
				           .entity(violationReport)
                           .build();
		}
		if (badRequestException != null) {
			return Response.status(BAD_REQUEST)
					       .entity(badRequestException.getMessage())
	                       .build();
		}
		
		if (exception instanceof WebApplicationException) {
			return WebApplicationException.class.cast(exception).getResponse();
		}
		
		return Response.serverError()
	                   .entity(exception.getMessage())
	                   .build();
	}

	private static Throwable getBadRequestException(Throwable exception) {
		if (exception instanceof ValidationException) {
			return exception;
		}
		
		final Throwable cause = exception.getCause();
		if (cause != null && !cause.equals(exception)) {
			final Throwable result = getBadRequestException(cause);
			if (result != null) {
				return result;
			}
		}
		
		if (exception instanceof IllegalArgumentException || exception instanceof BadRequestException) {
			return exception;
		}
		
		return null;
	}
	
	private static ViolationReport toViolationReport(ConstraintViolationException exception) {
		final Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
		final ArrayList<ResteasyConstraintViolation> parameterViolations = new ArrayList<>();
		for (ConstraintViolation<?> v : violations) {
			final String path = v.getPropertyPath().toString();
			final String message = v.getMessage();
			final String value = v.getInvalidValue().toString();
			final ResteasyConstraintViolation resteasyConstraintViolation =
					                          new ResteasyConstraintViolation(PARAMETER, path, message, value);
			parameterViolations.add(resteasyConstraintViolation);
		}
		
		final ViolationReport violationReport = new ViolationReport();
		violationReport.setParameterViolations(parameterViolations);
		return violationReport;
	}
}
