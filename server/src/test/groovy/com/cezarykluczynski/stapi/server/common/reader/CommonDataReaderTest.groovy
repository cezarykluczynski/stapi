package com.cezarykluczynski.stapi.server.common.reader

import com.cezarykluczynski.stapi.server.common.dto.RestEndpointMappingsDTO
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointStatisticsDTO
import spock.lang.Specification

class CommonDataReaderTest extends Specification {

	private CommonMappingsReader commonMappingsReaderMock

	private CommonEntitiesStatisticsReader commonEntitiesStatisticsReaderMock

	private CommonDataReader commonDataReader

	void setup() {
		commonMappingsReaderMock = Mock()
		commonEntitiesStatisticsReaderMock = Mock()
		commonDataReader = new CommonDataReader(commonMappingsReaderMock, commonEntitiesStatisticsReaderMock)
	}

	void "gets mappings from CommonMappingsReader"() {
		given:
		RestEndpointMappingsDTO restEndpointMappingsDTO = Mock()

		when:
		RestEndpointMappingsDTO restEndpointMappingsDTOOutput = commonDataReader.mappings()

		then:
		1 * commonMappingsReaderMock.mappings() >> restEndpointMappingsDTO
		0 * _
		restEndpointMappingsDTOOutput == restEndpointMappingsDTO
	}

	void "gets entities statistics from CommonEntitiesStatisticsReader"() {
		given:
		RestEndpointStatisticsDTO restEndpointStatisticsDTO = Mock()

		when:
		RestEndpointStatisticsDTO restEndpointStatisticsDTOOutput = commonDataReader.entitiesStatistics()

		then:
		1 * commonEntitiesStatisticsReaderMock.entitiesStatistics() >> restEndpointStatisticsDTO
		0 * _
		restEndpointStatisticsDTOOutput == restEndpointStatisticsDTO
	}

}
