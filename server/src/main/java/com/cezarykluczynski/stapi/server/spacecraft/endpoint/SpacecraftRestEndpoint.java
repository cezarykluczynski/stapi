package com.cezarykluczynski.stapi.server.spacecraft.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.spacecraft.dto.SpacecraftRestBeanParams;
import com.cezarykluczynski.stapi.server.spacecraft.reader.SpacecraftRestReader;
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
public class SpacecraftRestEndpoint {

	public static final String ADDRESS = "/v1/rest/spacecraft";

	private final SpacecraftRestReader spacecraftRestReader;

	public SpacecraftRestEndpoint(SpacecraftRestReader spacecraftRestReader) {
		this.spacecraftRestReader = spacecraftRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public SpacecraftFullResponse getSpacecraft(@QueryParam("uid") String uid) {
		return spacecraftRestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public SpacecraftBaseResponse searchSpacecraft(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return spacecraftRestReader.readBase(SpacecraftRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public SpacecraftBaseResponse searchSpacecraft(@BeanParam SpacecraftRestBeanParams spacecraftRestBeanParams) {
		return spacecraftRestReader.readBase(spacecraftRestBeanParams);
	}

}
