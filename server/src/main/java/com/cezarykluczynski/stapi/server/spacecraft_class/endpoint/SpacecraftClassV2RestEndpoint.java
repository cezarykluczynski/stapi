package com.cezarykluczynski.stapi.server.spacecraft_class.endpoint;

import com.cezarykluczynski.stapi.client.rest.model.SpacecraftClassV2BaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.SpacecraftClassV2FullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.spacecraft_class.dto.SpacecraftClassV2RestBeanParams;
import com.cezarykluczynski.stapi.server.spacecraft_class.reader.SpacecraftClassV2RestReader;
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
public class SpacecraftClassV2RestEndpoint {

	public static final String ADDRESS = "/v2/rest/spacecraftClass";

	private final SpacecraftClassV2RestReader spacecraftClassV2RestReader;

	public SpacecraftClassV2RestEndpoint(SpacecraftClassV2RestReader spacecraftClassV2RestReader) {
		this.spacecraftClassV2RestReader = spacecraftClassV2RestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public SpacecraftClassV2FullResponse getSpacecraftClass(@QueryParam("uid") String uid) {
		return spacecraftClassV2RestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public SpacecraftClassV2BaseResponse searchSpacecraftClass(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return spacecraftClassV2RestReader.readBase(SpacecraftClassV2RestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public SpacecraftClassV2BaseResponse searchSpacecraftClass(@BeanParam SpacecraftClassV2RestBeanParams spacecraftClassRestBeanParams) {
		return spacecraftClassV2RestReader.readBase(spacecraftClassRestBeanParams);
	}

}
