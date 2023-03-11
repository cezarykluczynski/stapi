package com.cezarykluczynski.stapi.server.spacecraft_class.endpoint;

import com.cezarykluczynski.stapi.client.rest.model.SpacecraftClassBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.SpacecraftClassFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.spacecraft_class.dto.SpacecraftClassRestBeanParams;
import com.cezarykluczynski.stapi.server.spacecraft_class.reader.SpacecraftClassRestReader;
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
public class SpacecraftClassRestEndpoint {

	public static final String ADDRESS = "/v1/rest/spacecraftClass";

	private final SpacecraftClassRestReader spacecraftClassRestReader;

	public SpacecraftClassRestEndpoint(SpacecraftClassRestReader spacecraftClassRestReader) {
		this.spacecraftClassRestReader = spacecraftClassRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public SpacecraftClassFullResponse getSpacecraftClass(@QueryParam("uid") String uid) {
		return spacecraftClassRestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public SpacecraftClassBaseResponse searchSpacecraftClass(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return spacecraftClassRestReader.readBase(SpacecraftClassRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public SpacecraftClassBaseResponse searchSpacecraftClass(@BeanParam SpacecraftClassRestBeanParams spacecraftClassRestBeanParams) {
		return spacecraftClassRestReader.readBase(spacecraftClassRestBeanParams);
	}

}
