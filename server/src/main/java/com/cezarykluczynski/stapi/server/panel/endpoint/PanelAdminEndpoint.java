package com.cezarykluczynski.stapi.server.panel.endpoint;

import com.cezarykluczynski.stapi.auth.api_key.operation.ApiKeyAdminOperationsService;
import com.cezarykluczynski.stapi.auth.api_key.operation.block.ApiKeyBlockRelatedResponseDTO;
import com.cezarykluczynski.stapi.auth.api_key.operation.read.ApiKeyReadResponseDTO;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.util.constant.ContentType;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Service
@Produces(ContentType.APPLICATION_JSON_CHARSET_UTF8)
@CrossOriginResourceSharing(allowAllOrigins = true, maxAge = CxfConfiguration.CORS_MAX_AGE)
public class PanelAdminEndpoint {

	public static final String ADDRESS = "/v1/rest/panel/admin";

	private final ApiKeyAdminOperationsService apiKeyAdminOperationsService;

	public PanelAdminEndpoint(ApiKeyAdminOperationsService apiKeyAdminOperationsService) {
		this.apiKeyAdminOperationsService = apiKeyAdminOperationsService;
	}

	@GET
	@Path("apiKeys")
	@Consumes(MediaType.APPLICATION_JSON)
	@PreAuthorize("hasPermission(filterObject, 'ADMIN_MANAGEMENT')")
	public ApiKeyReadResponseDTO readApiKeysPage(@QueryParam("pageNumber") int pageNumber) {
		return apiKeyAdminOperationsService.getPage(pageNumber);
	}

	@POST
	@Path("apiKeys/block")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@PreAuthorize("hasPermission(filterObject, 'ADMIN_MANAGEMENT')")
	public ApiKeyBlockRelatedResponseDTO blockApiKey(@FormParam("accountId") Long accountId, @FormParam("apiKeyId") Long apiKeyId) {
		return apiKeyAdminOperationsService.block(accountId, apiKeyId);
	}

	@POST
	@Path("apiKeys/unblock")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@PreAuthorize("hasPermission(filterObject, 'ADMIN_MANAGEMENT')")
	public ApiKeyBlockRelatedResponseDTO unblockApiKey(@FormParam("accountId") Long accountId, @FormParam("apiKeyId") Long apiKeyId) {
		return apiKeyAdminOperationsService.unblock(accountId, apiKeyId);
	}

}
