package com.cezarykluczynski.stapi.server.common.reader;

import com.cezarykluczynski.stapi.server.common.dto.RestEndpointDetailsDTO;
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointStatisticsDTO;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class CommonDataReader {

	private final CommonEntitiesStatisticsReader commonEntitiesStatisticsReader;

	private final CommonEntitiesDetailsReader commonEntitiesDetailsReader;

	private final CommonHitsStatisticsReader commonHitsStatisticsReader;

	@Inject
	public CommonDataReader(CommonEntitiesStatisticsReader commonEntitiesStatisticsReader, CommonEntitiesDetailsReader commonEntitiesDetailsReader,
			CommonHitsStatisticsReader commonHitsStatisticsReader) {
		this.commonEntitiesStatisticsReader = commonEntitiesStatisticsReader;
		this.commonEntitiesDetailsReader = commonEntitiesDetailsReader;
		this.commonHitsStatisticsReader = commonHitsStatisticsReader;
	}

	public RestEndpointStatisticsDTO entitiesStatistics() {
		return commonEntitiesStatisticsReader.entitiesStatistics();
	}

	public RestEndpointStatisticsDTO hitsStatistics() {
		return commonHitsStatisticsReader.hitsStatistics();
	}

	public RestEndpointDetailsDTO details() {
		return commonEntitiesDetailsReader.details();
	}

}
