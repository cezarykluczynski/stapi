package com.cezarykluczynski.stapi.server.common.endpoint;

import com.cezarykluczynski.stapi.server.common.documentation.dto.DocumentationDTO;
import com.cezarykluczynski.stapi.server.common.dto.DataVersionDTO;
import com.cezarykluczynski.stapi.server.common.dto.PongDTO;
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointDetailsDTO;
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointStatisticsDTO;
import com.cezarykluczynski.stapi.server.common.reader.CommonDataReader;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.github.model.GitHubDTO;
import com.cezarykluczynski.stapi.server.github.service.GitHubApi;
import com.cezarykluczynski.stapi.util.constant.ContentType;
import com.cezarykluczynski.stapi.util.feature_switch.api.FeatureSwitchApi;
import com.cezarykluczynski.stapi.util.feature_switch.dto.FeatureSwitchesDTO;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
@Produces(ContentType.APPLICATION_JSON_CHARSET_UTF8)
@CrossOriginResourceSharing(allowAllOrigins = CxfConfiguration.CORS_ALLOW_ALL_ORIGINS, maxAge = CxfConfiguration.CORS_MAX_AGE)
public class CommonRestEndpoint {

	public static final String ADDRESS = "/v1/rest/common";

	private final CommonDataReader commonDataReader;

	private final FeatureSwitchApi featureSwitchApi;

	private final GitHubApi gitHubApi;

	public CommonRestEndpoint(CommonDataReader commonDataReader, FeatureSwitchApi featureSwitchApi, GitHubApi gitHubApi) {
		this.commonDataReader = commonDataReader;
		this.featureSwitchApi = featureSwitchApi;
		this.gitHubApi = gitHubApi;
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

	@GET
	@Path("statistics/entities")
	public RestEndpointStatisticsDTO entitiesStatistics() {
		return commonDataReader.entitiesStatistics();
	}

	@GET
	@Path("statistics/hits")
	public RestEndpointStatisticsDTO hitsStatistics() {
		return commonDataReader.hitsStatistics();
	}

	@GET
	@Path("details")
	public RestEndpointDetailsDTO details() {
		return commonDataReader.details();
	}

	@GET
	@Path("documentation")
	public DocumentationDTO documentation() {
		return commonDataReader.documentation();
	}

	@GET
	@Path("ping")
	public PongDTO ping() {
		return new PongDTO();
	}

	@GET
	@Path("download/zip/rest")
	@ResponseBody
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response restSpecsZip() {
		return commonDataReader.restSpecsZip();
	}

	@GET
	@Path("download/zip/soap")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response soapContractsZip() {
		return commonDataReader.soapContractsZip();
	}

	@GET
	@Path("download/zip/tosForm")
	@ResponseBody
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response tosFormZip() {
		return commonDataReader.tosFormZip();
	}

	@GET
	@Path("dataVersion")
	public DataVersionDTO dataVersion() {
		return commonDataReader.dataVersion();
	}

}
