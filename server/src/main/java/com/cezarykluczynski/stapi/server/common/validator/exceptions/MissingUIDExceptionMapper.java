package com.cezarykluczynski.stapi.server.common.validator.exceptions;

import com.cezarykluczynski.stapi.util.constant.ContentType;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

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
