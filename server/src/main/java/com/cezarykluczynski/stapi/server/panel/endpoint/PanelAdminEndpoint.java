package com.cezarykluczynski.stapi.server.panel.endpoint;

import com.cezarykluczynski.stapi.auth.account.operation.AccountAdminOperationsService;
import com.cezarykluczynski.stapi.auth.account.operation.read.AccountReadResponseDTO;
import com.cezarykluczynski.stapi.auth.account.operation.read.AccountsSearchCriteriaDTO;
import com.cezarykluczynski.stapi.auth.api_key.operation.ApiKeyAdminOperationsService;
import com.cezarykluczynski.stapi.auth.api_key.operation.block.ApiKeyBlockRelatedResponseDTO;
import com.cezarykluczynski.stapi.auth.api_key.operation.read.ApiKeyReadResponseDTO;
import com.cezarykluczynski.stapi.auth.api_key.operation.read.ApiKeysSearchCriteriaDTO;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.util.constant.ContentType;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Service
@Produces(ContentType.APPLICATION_JSON_CHARSET_UTF8)
@CrossOriginResourceSharing(allowAllOrigins = true, maxAge = CxfConfiguration.CORS_MAX_AGE)
public class PanelAdminEndpoint {

	public static final String ADDRESS = "/v1/rest/common/panel/admin";

	private final ApiKeyAdminOperationsService apiKeyAdminOperationsService;

	private final AccountAdminOperationsService accountAdminOperationsService;

	public PanelAdminEndpoint(ApiKeyAdminOperationsService apiKeyAdminOperationsService,
			AccountAdminOperationsService accountAdminOperationsService) {
		this.apiKeyAdminOperationsService = apiKeyAdminOperationsService;
		this.accountAdminOperationsService = accountAdminOperationsService;
	}

	@POST
	@Path("apiKeys")
	@Consumes(ContentType.APPLICATION_JSON_CHARSET_UTF8)
	@PreAuthorize("hasPermission(filterObject, 'ADMIN_MANAGEMENT')")
	public ApiKeyReadResponseDTO searchApiKeys(@RequestBody ApiKeysSearchCriteriaDTO apiKeysSearchCriteriaDTO) {
		return apiKeyAdminOperationsService.search(apiKeysSearchCriteriaDTO);
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

	@POST
	@Path("accounts")
	@Consumes(ContentType.APPLICATION_JSON_CHARSET_UTF8)
	@PreAuthorize("hasPermission(filterObject, 'ADMIN_MANAGEMENT')")
	public AccountReadResponseDTO searchAccounts(@RequestBody AccountsSearchCriteriaDTO accountsSearchCriteriaDTO) {
		return accountAdminOperationsService.search(accountsSearchCriteriaDTO);
	}

}
