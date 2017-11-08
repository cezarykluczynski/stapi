package com.cezarykluczynski.stapi.server.panel.endpoint;

import com.cezarykluczynski.stapi.auth.api_key.dto.ApiKeyDTO;
import com.cezarykluczynski.stapi.auth.api_key.operation.ApiKeysOwnOperationsService;
import com.cezarykluczynski.stapi.auth.api_key.operation.creation.ApiKeyCreationResponseDTO;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.util.constant.ContentType;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Service
@Produces(ContentType.APPLICATION_JSON_CHARSET_UTF8)
@CrossOriginResourceSharing(allowAllOrigins = true, maxAge = CxfConfiguration.CORS_MAX_AGE)
public class PanelApiKeysEndpoint {

	public static final String ADDRESS = "/v1/rest/panel/apiKeys";

	private final ApiKeysOwnOperationsService apiKeysOwnOperationsService;

	public PanelApiKeysEndpoint(ApiKeysOwnOperationsService apiKeysOwnOperationsService) {
		this.apiKeysOwnOperationsService = apiKeysOwnOperationsService;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@PreAuthorize("hasPermission(filterObject, 'API_KEY_MANAGEMENT')")
	public List<ApiKeyDTO> getAll() {
		return apiKeysOwnOperationsService.getAll();
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@PreAuthorize("hasPermission(filterObject, 'API_KEY_MANAGEMENT')")
	public ApiKeyCreationResponseDTO create() {
		return apiKeysOwnOperationsService.create();
	}

}
