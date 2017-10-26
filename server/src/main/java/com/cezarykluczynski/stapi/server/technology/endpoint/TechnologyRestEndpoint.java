package com.cezarykluczynski.stapi.server.technology.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.TechnologyBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.TechnologyFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.technology.dto.TechnologyRestBeanParams;
import com.cezarykluczynski.stapi.server.technology.reader.TechnologyRestReader;
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
public class TechnologyRestEndpoint {

	public static final String ADDRESS = "/v1/rest/technology";

	private final TechnologyRestReader technologyRestReader;

	public TechnologyRestEndpoint(TechnologyRestReader technologyRestReader) {
		this.technologyRestReader = technologyRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public TechnologyFullResponse getTechnology(@QueryParam("uid") String uid) {
		return technologyRestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public TechnologyBaseResponse searchTechnologys(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return technologyRestReader.readBase(TechnologyRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public TechnologyBaseResponse searchTechnologys(@BeanParam TechnologyRestBeanParams technologyRestBeanParams) {
		return technologyRestReader.readBase(technologyRestBeanParams);
	}

}
