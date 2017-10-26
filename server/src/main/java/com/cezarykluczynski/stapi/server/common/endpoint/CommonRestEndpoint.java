package com.cezarykluczynski.stapi.server.common.endpoint;

import com.cezarykluczynski.stapi.contract.documentation.dto.DocumentationDTO;
import com.cezarykluczynski.stapi.server.common.dto.PongDTO;
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointDetailsDTO;
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointStatisticsDTO;
import com.cezarykluczynski.stapi.server.common.reader.CommonDataReader;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.util.constant.ContentType;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

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

	public CommonRestEndpoint(CommonDataReader commonDataReader) {
		this.commonDataReader = commonDataReader;
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

}
