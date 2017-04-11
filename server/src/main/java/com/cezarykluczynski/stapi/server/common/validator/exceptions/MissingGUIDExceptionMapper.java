package com.cezarykluczynski.stapi.server.common.validator.exceptions;

import com.cezarykluczynski.stapi.server.common.throttle.rest.RestError;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class MissingGUIDExceptionMapper implements ExceptionMapper<MissingGUIDException> {

	@Override
	public Response toResponse(MissingGUIDException exception) {
		return Response
				.status(Response.Status.BAD_REQUEST)
				.type(MediaType.APPLICATION_JSON)
				.entity(new RestError("MISSING_GUID", exception.getMessage()))
				.build();
	}

}