package com.cezarykluczynski.stapi.server.panel.endpoint;

import com.cezarykluczynski.stapi.auth.api_key.operation.ApiKeysOwnOperationsService;
import com.cezarykluczynski.stapi.auth.api_key.operation.creation.ApiKeyCreationResponseDTO;
import com.cezarykluczynski.stapi.auth.api_key.operation.edit.ApiKeyEditResponseDTO;
import com.cezarykluczynski.stapi.auth.api_key.operation.read.ApiKeyReadResponseDTO;
import com.cezarykluczynski.stapi.auth.api_key.operation.removal.ApiKeyRemovalResponseDTO;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.util.constant.ContentType;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Service
@Produces(ContentType.APPLICATION_JSON_CHARSET_UTF8)
@CrossOriginResourceSharing(allowAllOrigins = true, maxAge = CxfConfiguration.CORS_MAX_AGE)
public class PanelApiKeysEndpoint {

	public static final String ADDRESS = "/v1/rest/common/panel/apiKeys";

	private final ApiKeysOwnOperationsService apiKeysOwnOperationsService;

	public PanelApiKeysEndpoint(ApiKeysOwnOperationsService apiKeysOwnOperationsService) {
		this.apiKeysOwnOperationsService = apiKeysOwnOperationsService;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@PreAuthorize("hasPermission(filterObject, 'API_KEY_MANAGEMENT')")
	public ApiKeyReadResponseDTO getAll() {
		return apiKeysOwnOperationsService.getAll();
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@PreAuthorize("hasPermission(filterObject, 'API_KEY_MANAGEMENT')")
	public ApiKeyCreationResponseDTO create() {
		return apiKeysOwnOperationsService.create();
	}

	@DELETE
	@Path("/{apiKeyId}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@PreAuthorize("hasPermission(filterObject, 'API_KEY_MANAGEMENT')")
	public ApiKeyRemovalResponseDTO remove(@PathParam("apiKeyId") Long apiKeyId) {
		return apiKeysOwnOperationsService.remove(apiKeyId);
	}

	@POST
	@Path("/{apiKeyId}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@PreAuthorize("hasPermission(filterObject, 'API_KEY_MANAGEMENT')")
	public ApiKeyEditResponseDTO edit(@PathParam("apiKeyId") Long apiKeyId, @FormParam("url") String url,
			@FormParam("description") String description) {
		return apiKeysOwnOperationsService.edit(apiKeyId, url, description);
	}

}
