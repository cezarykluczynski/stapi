package com.cezarykluczynski.stapi.server.common.reader

import com.cezarykluczynski.stapi.server.common.dto.RestEndpointDetailsDTO
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointMappingsDTO
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointStatisticsDTO
import spock.lang.Specification

class CommonDataReaderTest extends Specification {

	private CommonMappingsReader commonMappingsReaderMock

	private CommonEntitiesStatisticsReader commonEntitiesStatisticsReaderMock

	private CommonEntitiesDetailsReader commonEntitiesDetailsReaderMock

	private CommonDataReader commonDataReader

	void setup() {
		commonMappingsReaderMock = Mock()
		commonEntitiesStatisticsReaderMock = Mock()
		commonEntitiesDetailsReaderMock = Mock()
		commonDataReader = new CommonDataReader(commonMappingsReaderMock, commonEntitiesStatisticsReaderMock, commonEntitiesDetailsReaderMock)
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

	void "gets details from CommonEntitiesDetailsReader"() {
		given:
		RestEndpointDetailsDTO restEndpointDetailsDTO = Mock()

		when:
		RestEndpointDetailsDTO restEndpointDetailsDTOOutput = commonDataReader.details()

		then:
		1 * commonEntitiesDetailsReaderMock.details() >> restEndpointDetailsDTO
		0 * _
		restEndpointDetailsDTOOutput == restEndpointDetailsDTO
	}

}
