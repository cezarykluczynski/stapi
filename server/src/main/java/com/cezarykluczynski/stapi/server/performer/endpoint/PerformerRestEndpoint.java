package com.cezarykluczynski.stapi.server.performer.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.performer.dto.PerformerRestBeanParams;
import com.cezarykluczynski.stapi.server.performer.reader.PerformerRestReader;
import com.cezarykluczynski.stapi.util.constant.ContentType;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.springframework.stereotype.Service;

@Service
@Produces(ContentType.APPLICATION_JSON_CHARSET_UTF8)
@CrossOriginResourceSharing(allowAllOrigins = CxfConfiguration.CORS_ALLOW_ALL_ORIGINS, maxAge = CxfConfiguration.CORS_MAX_AGE)
public class PerformerRestEndpoint {

	public static final String ADDRESS = "/v1/rest/performer";

	private final PerformerRestReader performerRestReader;

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
