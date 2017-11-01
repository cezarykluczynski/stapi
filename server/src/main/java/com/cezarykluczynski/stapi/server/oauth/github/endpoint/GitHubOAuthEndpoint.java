package com.cezarykluczynski.stapi.server.oauth.github.endpoint;

import com.cezarykluczynski.stapi.auth.oauth.github.dto.GitHubRedirectUrlDTO;
import com.cezarykluczynski.stapi.auth.oauth.github.service.GitHubOAuthFacade;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.util.constant.ContentType;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.springframework.stereotype.Service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
@Produces(ContentType.APPLICATION_JSON_CHARSET_UTF8)
@CrossOriginResourceSharing(allowAllOrigins = true, maxAge = CxfConfiguration.CORS_MAX_AGE)
public class GitHubOAuthEndpoint {

	public static final String ADDRESS = "/v1/rest/oauth/github";

	private final GitHubOAuthFacade gitHubOAuthFacade;

	public GitHubOAuthEndpoint(GitHubOAuthFacade gitHubOAuthFacade) {
		this.gitHubOAuthFacade = gitHubOAuthFacade;
	}

	@GET
	@Path("oAuthAuthorizeUrl")
	@Consumes(MediaType.APPLICATION_JSON)
	public GitHubRedirectUrlDTO getGitHubOAuthAuthorizeUrl() {
		return gitHubOAuthFacade.getGitHubOAuthAuthorizeUrl();
	}

	@GET
	@Path("authenticate")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response authenticate(@QueryParam("code") String code) {
		return Response
				.status(Response.Status.FOUND)
				.header("Location", gitHubOAuthFacade.authenticate(code).getUrl())
				.build();
	}

}
