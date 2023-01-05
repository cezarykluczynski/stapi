package com.cezarykluczynski.stapi.server.spacecraft.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.spacecraft.dto.SpacecraftRestBeanParams;
import com.cezarykluczynski.stapi.server.spacecraft.reader.SpacecraftRestReader;
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
