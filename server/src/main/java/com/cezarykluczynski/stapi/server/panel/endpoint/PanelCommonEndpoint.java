package com.cezarykluczynski.stapi.server.panel.endpoint;

import com.cezarykluczynski.stapi.auth.account.dto.UserDTO;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.github.model.GitHubDTO;
import com.cezarykluczynski.stapi.server.github.service.GitHubApi;
import com.cezarykluczynski.stapi.server.panel.service.PanelCurrentUserProvider;
import com.cezarykluczynski.stapi.util.constant.ContentType;
import com.cezarykluczynski.stapi.util.feature_switch.api.FeatureSwitchApi;
import com.cezarykluczynski.stapi.util.feature_switch.dto.FeatureSwitchesDTO;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Service
@Produces(ContentType.APPLICATION_JSON_CHARSET_UTF8)
@CrossOriginResourceSharing(allowAllOrigins = true, maxAge = CxfConfiguration.CORS_MAX_AGE)
public class PanelCommonEndpoint {

	public static final String ADDRESS = "/v1/rest/common/panel";

	private final PanelCurrentUserProvider panelCurrentUserProvider;

	private final FeatureSwitchApi featureSwitchApi;

	private final GitHubApi gitHubApi;

	public PanelCommonEndpoint(PanelCurrentUserProvider panelCurrentUserProvider, FeatureSwitchApi featureSwitchApi, GitHubApi gitHubApi) {
		this.panelCurrentUserProvider = panelCurrentUserProvider;
		this.featureSwitchApi = featureSwitchApi;
		this.gitHubApi = gitHubApi;
	}

	@GET
	@Path("me")
	@Consumes(MediaType.APPLICATION_JSON)
	@PreAuthorize("hasPermission(filterObject, 'API_KEY_MANAGEMENT')")
	public UserDTO me() {
		return panelCurrentUserProvider.provide();
	}

	@GET
	@Path("featureSwitch")
	@Consumes(MediaType.APPLICATION_JSON)
	public FeatureSwitchesDTO featureSwitches() {
		return featureSwitchApi.getAll();
	}

	@GET
	@Path("github/projectDetails")
	@Consumes(MediaType.APPLICATION_JSON)
	public GitHubDTO gitHubProjectDetails() {
		return gitHubApi.getProjectDetails();
	}

}
