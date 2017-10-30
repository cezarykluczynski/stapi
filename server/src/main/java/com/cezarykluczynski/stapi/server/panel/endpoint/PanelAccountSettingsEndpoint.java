package com.cezarykluczynski.stapi.server.panel.endpoint;

import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.util.constant.ContentType;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.springframework.stereotype.Service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Service
@Produces(ContentType.APPLICATION_JSON_CHARSET_UTF8)
@CrossOriginResourceSharing(allowAllOrigins = true, maxAge = CxfConfiguration.CORS_MAX_AGE)
public class PanelAccountSettingsEndpoint {

	public static final String ADDRESS = "/v1/rest/panel/accountSettings";

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public String todo() {
		return "todo"; // TODO
	}

}
