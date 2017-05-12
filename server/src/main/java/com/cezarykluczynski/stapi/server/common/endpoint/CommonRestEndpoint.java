package com.cezarykluczynski.stapi.server.common.endpoint;


import com.cezarykluczynski.stapi.server.common.dto.RestEndpointDetailsDTO;
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointStatisticsDTO;
import com.cezarykluczynski.stapi.server.common.reader.CommonDataReader;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Service
@Produces(MediaType.APPLICATION_JSON)
public class CommonRestEndpoint {

	public static final String ADDRESS = "/v1/rest/common";

	private final CommonDataReader commonDataReader;

	@Inject
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

}
