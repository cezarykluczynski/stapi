package com.cezarykluczynski.stapi.server.panel.endpoint;

import com.cezarykluczynski.stapi.auth.account.operation.AccountOwnOperationsService;
import com.cezarykluczynski.stapi.auth.account.operation.edit.AccountEditResponseDTO;
import com.cezarykluczynski.stapi.auth.account.operation.removal.AccountRemovalResponseDTO;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.util.constant.ContentType;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Service
@Produces(ContentType.APPLICATION_JSON_CHARSET_UTF8)
@CrossOriginResourceSharing(allowAllOrigins = true, maxAge = CxfConfiguration.CORS_MAX_AGE)
public class PanelAccountSettingsEndpoint {

	public static final String ADDRESS = "/v1/rest/panel";

	private final AccountOwnOperationsService accountOwnOperationsService;

	public PanelAccountSettingsEndpoint(AccountOwnOperationsService accountOwnOperationsService) {
		this.accountOwnOperationsService = accountOwnOperationsService;
	}

	@DELETE
	@Path("accountSettings")
	@Consumes(MediaType.APPLICATION_JSON)
	@PreAuthorize("hasPermission(filterObject, 'API_KEY_MANAGEMENT')")
	public AccountRemovalResponseDTO remove() {
		return accountOwnOperationsService.remove();
	}

	@POST
	@Path("basicData")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@PreAuthorize("hasPermission(filterObject, 'API_KEY_MANAGEMENT')")
	public AccountEditResponseDTO updateBasicData(@FormParam("name") String name, @FormParam("email") String email) {
		return accountOwnOperationsService.updateBasicData(name, email);
	}


}
