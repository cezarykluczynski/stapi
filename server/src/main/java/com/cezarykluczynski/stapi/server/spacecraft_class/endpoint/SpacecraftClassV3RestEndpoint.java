package com.cezarykluczynski.stapi.server.spacecraft_class.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftClassV3FullResponse;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.spacecraft_class.reader.SpacecraftClassV3RestReader;
import com.cezarykluczynski.stapi.util.constant.ContentType;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.springframework.stereotype.Service;

@Service
@Produces(ContentType.APPLICATION_JSON_CHARSET_UTF8)
@CrossOriginResourceSharing(allowAllOrigins = CxfConfiguration.CORS_ALLOW_ALL_ORIGINS, maxAge = CxfConfiguration.CORS_MAX_AGE)
public class SpacecraftClassV3RestEndpoint {

	public static final String ADDRESS = "/v3/rest/spacecraftClass";

	private final SpacecraftClassV3RestReader spacecraftClassV3RestReader;

	public SpacecraftClassV3RestEndpoint(SpacecraftClassV3RestReader spacecraftClassV3RestReader) {
		this.spacecraftClassV3RestReader = spacecraftClassV3RestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public SpacecraftClassV3FullResponse getSpacecraftClass(@QueryParam("uid") String uid) {
		return spacecraftClassV3RestReader.readFull(uid);
	}

}
