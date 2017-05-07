package com.cezarykluczynski.stapi.server.common.reader;

import com.cezarykluczynski.stapi.server.common.dto.RestEndpointDetailsDTO;
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointStatisticsDTO;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class CommonDataReader {

	private final CommonEntitiesStatisticsReader commonEntitiesStatisticsReader;

	private final CommonEntitiesDetailsReader commonEntitiesDetailsReader;

	@Inject
	public CommonDataReader(CommonEntitiesStatisticsReader commonEntitiesStatisticsReader, CommonEntitiesDetailsReader commonEntitiesDetailsReader) {
		this.commonEntitiesStatisticsReader = commonEntitiesStatisticsReader;
		this.commonEntitiesDetailsReader = commonEntitiesDetailsReader;
	}

	public RestEndpointStatisticsDTO entitiesStatistics() {
		return commonEntitiesStatisticsReader.entitiesStatistics();
	}

	public RestEndpointDetailsDTO details() {
		return commonEntitiesDetailsReader.details();
	}

}
