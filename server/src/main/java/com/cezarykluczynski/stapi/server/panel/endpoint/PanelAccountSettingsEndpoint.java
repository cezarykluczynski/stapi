package com.cezarykluczynski.stapi.server.panel.endpoint;

import com.cezarykluczynski.stapi.auth.account.api.ConsentApi;
import com.cezarykluczynski.stapi.auth.account.dto.ConsentDTO;
import com.cezarykluczynski.stapi.auth.account.operation.AccountOwnOperationsService;
import com.cezarykluczynski.stapi.auth.account.operation.edit.AccountEditResponseDTO;
import com.cezarykluczynski.stapi.auth.account.operation.read.AccountConsentsReadResponseDTO;
import com.cezarykluczynski.stapi.auth.account.operation.removal.AccountRemovalResponseDTO;
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
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Service
@Produces(ContentType.APPLICATION_JSON_CHARSET_UTF8)
@CrossOriginResourceSharing(allowAllOrigins = true, maxAge = CxfConfiguration.CORS_MAX_AGE)
public class PanelAccountSettingsEndpoint {

	public static final String ADDRESS = "/v1/rest/common/panel/accountSettings";

	private final AccountOwnOperationsService accountOwnOperationsService;

	private final ConsentApi consentApi;

	public PanelAccountSettingsEndpoint(AccountOwnOperationsService accountOwnOperationsService, ConsentApi consentApi) {
		this.accountOwnOperationsService = accountOwnOperationsService;
		this.consentApi = consentApi;
	}

	@DELETE
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

	@GET
	@Path("consents/own")
	@Consumes(MediaType.APPLICATION_JSON)
	public AccountConsentsReadResponseDTO ownConsents() {
		return accountOwnOperationsService.readConsents();
	}

	@POST
	@Path("consents/own")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@PreAuthorize("hasPermission(filterObject, 'API_KEY_MANAGEMENT')")
	public AccountEditResponseDTO updateConsents(@FormParam("consents") String[] consents) {
		return accountOwnOperationsService.updateConsents(consents);
	}

	@GET
	@Path("consents")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<ConsentDTO> consents() {
		return consentApi.provideAll();
	}


}
