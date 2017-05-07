package com.cezarykluczynski.stapi.server.common.reader;

import com.cezarykluczynski.stapi.server.common.dto.RestEndpointMappingsDTO;
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointStatisticsDTO;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class CommonDataReader {

	private final CommonMappingsReader commonMappingsReader;

	private final CommonEntitiesStatisticsReader commonEntitiesStatisticsReader;

	@Inject
	public CommonDataReader(CommonMappingsReader commonMappingsReader, CommonEntitiesStatisticsReader commonEntitiesStatisticsReader) {
		this.commonMappingsReader = commonMappingsReader;
		this.commonEntitiesStatisticsReader = commonEntitiesStatisticsReader;
	}

	public RestEndpointMappingsDTO mappings() {
		return commonMappingsReader.mappings();
	}

	public RestEndpointStatisticsDTO entitiesStatistics() {
		return commonEntitiesStatisticsReader.entitiesStatistics();
	}

}
