package com.cezarykluczynski.stapi.server.performer.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.performer.dto.PerformerRestBeanParams;
import com.cezarykluczynski.stapi.server.performer.reader.PerformerRestReader;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
	public PerformerFullResponse getPerformer(@QueryParam("uid") String uid) {
		return performerRestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public PerformerBaseResponse searchPerformer(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return performerRestReader.readBase(PerformerRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public PerformerBaseResponse searchPerformer(@BeanParam PerformerRestBeanParams performerRestBeanParams) {
		return performerRestReader.readBase(performerRestBeanParams);
	}

}
