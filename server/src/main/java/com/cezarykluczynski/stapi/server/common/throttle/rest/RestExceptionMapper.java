package com.cezarykluczynski.stapi.server.common.throttle.rest;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class RestExceptionMapper implements ExceptionMapper<RestException> {

	@Override
	public Response toResponse(RestException restException) {
		return Response
				.status(Response.Status.FORBIDDEN)
				.type(MediaType.APPLICATION_JSON)
				.entity(new RestError(restException.getThrottleReason().name(), restException.getMessage()))
				.build();
	}

}
