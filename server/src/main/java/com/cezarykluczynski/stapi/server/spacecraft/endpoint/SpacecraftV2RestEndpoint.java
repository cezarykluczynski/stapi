package com.cezarykluczynski.stapi.server.spacecraft.endpoint;

import com.cezarykluczynski.stapi.client.rest.model.SpacecraftV2BaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.SpacecraftV2FullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.spacecraft.dto.SpacecraftV2RestBeanParams;
import com.cezarykluczynski.stapi.server.spacecraft.reader.SpacecraftV2RestReader;
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
public class SpacecraftV2RestEndpoint {

	public static final String ADDRESS = "/v2/rest/spacecraft";

	private final SpacecraftV2RestReader spacecraftV2RestReader;

	public SpacecraftV2RestEndpoint(SpacecraftV2RestReader spacecraftV2RestReader) {
		this.spacecraftV2RestReader = spacecraftV2RestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public SpacecraftV2FullResponse getSpacecraft(@QueryParam("uid") String uid) {
		return spacecraftV2RestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public SpacecraftV2BaseResponse searchSpacecraft(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return spacecraftV2RestReader.readBase(SpacecraftV2RestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public SpacecraftV2BaseResponse searchSpacecraft(@BeanParam SpacecraftV2RestBeanParams spacecraftRestBeanParams) {
		return spacecraftV2RestReader.readBase(spacecraftRestBeanParams);
	}

}
