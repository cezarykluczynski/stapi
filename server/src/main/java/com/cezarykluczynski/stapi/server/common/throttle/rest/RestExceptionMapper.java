package com.cezarykluczynski.stapi.server.common.throttle.rest;

import com.cezarykluczynski.stapi.util.constant.ContentType;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Produces(ContentType.APPLICATION_JSON_CHARSET_UTF8)
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
