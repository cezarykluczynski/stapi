package com.cezarykluczynski.stapi.server.common.validator.exceptions;

import com.cezarykluczynski.stapi.server.common.throttle.rest.RestError;
import com.cezarykluczynski.stapi.util.constant.ContentType;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Produces(ContentType.APPLICATION_JSON_CHARSET_UTF8)
@SuppressWarnings("AbbreviationAsWordInName")
public class MissingUIDExceptionMapper implements ExceptionMapper<MissingUIDException> {

	@Override
	public Response toResponse(MissingUIDException exception) {
		return Response
				.status(Response.Status.BAD_REQUEST)
				.type(MediaType.APPLICATION_JSON)
				.entity(new RestError("MISSING_UID", exception.getMessage()))
				.build();
	}

}
