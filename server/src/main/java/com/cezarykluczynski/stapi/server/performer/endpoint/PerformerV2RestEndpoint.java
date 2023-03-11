package com.cezarykluczynski.stapi.server.performer.endpoint;

import com.cezarykluczynski.stapi.client.rest.model.PerformerV2BaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.PerformerV2FullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.performer.dto.PerformerV2RestBeanParams;
import com.cezarykluczynski.stapi.server.performer.reader.PerformerV2RestReader;
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
public class PerformerV2RestEndpoint {

	public static final String ADDRESS = "/v2/rest/performer";

	private final PerformerV2RestReader performerV2RestReader;

	public PerformerV2RestEndpoint(PerformerV2RestReader performerV2RestReader) {
		this.performerV2RestReader = performerV2RestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public PerformerV2FullResponse getPerformer(@QueryParam("uid") String uid) {
		return performerV2RestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public PerformerV2BaseResponse searchPerformer(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return performerV2RestReader.readBase(PerformerV2RestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public PerformerV2BaseResponse searchPerformer(@BeanParam PerformerV2RestBeanParams performerV2RestBeanParams) {
		return performerV2RestReader.readBase(performerV2RestBeanParams);
	}

}
