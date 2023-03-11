package com.cezarykluczynski.stapi.server.common.endpoint;

import com.cezarykluczynski.stapi.server.common.dto.DataVersionDTO;
import com.cezarykluczynski.stapi.server.common.dto.PongDTO;
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointDetailsDTO;
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointStatisticsDTO;
import com.cezarykluczynski.stapi.server.common.feature_switch.api.FeatureSwitchApi;
import com.cezarykluczynski.stapi.server.common.feature_switch.dto.FeatureSwitchesDTO;
import com.cezarykluczynski.stapi.server.common.healthcheck.CommonDatabaseStatusValidator;
import com.cezarykluczynski.stapi.server.common.reader.CommonDataReader;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.util.constant.ContentType;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

@Service
@Produces(ContentType.APPLICATION_JSON_CHARSET_UTF8)
@CrossOriginResourceSharing(allowAllOrigins = CxfConfiguration.CORS_ALLOW_ALL_ORIGINS, maxAge = CxfConfiguration.CORS_MAX_AGE)
public class CommonRestEndpoint {

	public static final String ADDRESS = "/v1/rest/common";

	private final CommonDataReader commonDataReader;

	private final FeatureSwitchApi featureSwitchApi;

	private final CommonDatabaseStatusValidator commonDatabaseStatusValidator;

	public CommonRestEndpoint(CommonDataReader commonDataReader, FeatureSwitchApi featureSwitchApi,
			CommonDatabaseStatusValidator commonDatabaseStatusValidator) {
		this.commonDataReader = commonDataReader;
		this.featureSwitchApi = featureSwitchApi;
		this.commonDatabaseStatusValidator = commonDatabaseStatusValidator;
	}

	@GET
	@Path("featureSwitch")
	@Consumes(MediaType.APPLICATION_JSON)
	public FeatureSwitchesDTO featureSwitches() {
		return featureSwitchApi.getAll();
	}

	@GET
	@Path("statistics/entities")
	public RestEndpointStatisticsDTO entitiesStatistics() {
		return commonDataReader.entitiesStatistics();
	}

	@GET
	@Path("details")
	public RestEndpointDetailsDTO details() {
		return commonDataReader.details();
	}

	@GET
	@Path("ping")
	public PongDTO ping() {
		commonDatabaseStatusValidator.validateDatabaseAccess();
		return new PongDTO();
	}

	@Deprecated
	@GET
	@Path("download/zip/rest")
	@ResponseBody
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response restSpecsZip() {
		return commonDataReader.restSpecsZip();
	}

	@GET
	@Path("download/zip/tosForm")
	@ResponseBody
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response tosFormZip() {
		return commonDataReader.tosFormZip();
	}

	@GET
	@Path("download/stapi.yaml")
	@ResponseBody
	public Response stapiYaml() {
		return commonDataReader.stapiYaml();
	}

	@GET
	@Path("dataVersion")
	public DataVersionDTO dataVersion() {
		return commonDataReader.dataVersion();
	}

}
