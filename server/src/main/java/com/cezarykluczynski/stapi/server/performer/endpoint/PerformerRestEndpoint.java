package com.cezarykluczynski.stapi.server.performer.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.performer.dto.PerformerRestBeanParams;
import com.cezarykluczynski.stapi.server.performer.reader.PerformerRestReader;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Service
@Path("v1/rest/performer")
@Produces(MediaType.APPLICATION_JSON)
public class PerformerRestEndpoint {

	private PerformerRestReader performerRestReader;

	@Inject
	public PerformerRestEndpoint(PerformerRestReader performerRestReader) {
		this.performerRestReader = performerRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public PerformerResponse getPerformers(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return performerRestReader.read(PerformerRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public PerformerResponse searchPerformers(@BeanParam PerformerRestBeanParams seriesRestBeanParams) {
		return performerRestReader.read(seriesRestBeanParams);
	}

}
