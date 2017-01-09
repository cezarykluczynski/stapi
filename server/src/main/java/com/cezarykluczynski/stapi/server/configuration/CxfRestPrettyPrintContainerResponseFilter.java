package com.cezarykluczynski.stapi.server.configuration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.jaxrs.cfg.EndpointConfigBase;
import com.fasterxml.jackson.jaxrs.cfg.ObjectWriterInjector;
import com.fasterxml.jackson.jaxrs.cfg.ObjectWriterModifier;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;

// source: http://stackoverflow.com/a/32649532
public class CxfRestPrettyPrintContainerResponseFilter implements ContainerResponseFilter {

	private static final String PRETTY_PRINT_TRIGGER_QUERY_PARAM = "pretty";

	@Override
	public void filter(ContainerRequestContext reqCtx, ContainerResponseContext respCtx) throws IOException {
		if (reqCtx.getUriInfo().getQueryParameters().containsKey(PRETTY_PRINT_TRIGGER_QUERY_PARAM)) {
			ObjectWriterInjector.set(new PrettyPrintObjectWriterModifier());
		}
	}

	private static class PrettyPrintObjectWriterModifier extends ObjectWriterModifier {

		@Override
		public ObjectWriter modify(EndpointConfigBase<?> endpointConfigBase, MultivaluedMap<String, Object> multivaluedMap, Object object,
				ObjectWriter objectWriter, JsonGenerator jsonGenerator) throws IOException {
			jsonGenerator.useDefaultPrettyPrinter();
			return objectWriter;
		}
	}

}
