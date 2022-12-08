package com.cezarykluczynski.stapi.server.technology.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.TechnologyV2BaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.TechnologyV2FullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.technology.dto.TechnologyV2RestBeanParams;
import com.cezarykluczynski.stapi.server.technology.reader.TechnologyV2RestReader;
import com.cezarykluczynski.stapi.util.constant.ContentType;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.springframework.stereotype.Service;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Service
@Produces(ContentType.APPLICATION_JSON_CHARSET_UTF8)
@CrossOriginResourceSharing(allowAllOrigins = CxfConfiguration.CORS_ALLOW_ALL_ORIGINS, maxAge = CxfConfiguration.CORS_MAX_AGE)
public class TechnologyV2RestEndpoint {

	public static final String ADDRESS = "/v2/rest/technology";

	private final TechnologyV2RestReader technologyV2RestReader;

	public TechnologyV2RestEndpoint(TechnologyV2RestReader technologyV2RestReader) {
		this.technologyV2RestReader = technologyV2RestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public TechnologyV2FullResponse getTechnology(@QueryParam("uid") String uid) {
		return technologyV2RestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public TechnologyV2BaseResponse searchTechnology(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return technologyV2RestReader.readBase(TechnologyV2RestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public TechnologyV2BaseResponse searchTechnology(@BeanParam TechnologyV2RestBeanParams technologyRestBeanParams) {
		return technologyV2RestReader.readBase(technologyRestBeanParams);
	}

}
