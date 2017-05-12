package com.cezarykluczynski.stapi.server.common.endpoint

import com.cezarykluczynski.stapi.server.common.dto.RestEndpointDetailsDTO
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointStatisticsDTO
import com.cezarykluczynski.stapi.server.common.reader.CommonDataReader
import spock.lang.Specification

class CommonRestEndpointTest extends Specification {

	private CommonDataReader commonDataReaderMock

	private CommonRestEndpoint commonRestEndpoint

	void setup() {
		commonDataReaderMock = Mock()
		commonRestEndpoint = new CommonRestEndpoint(commonDataReaderMock)
	}

	void "gets entities statistics from CommonDataReader"() {
		given:
		RestEndpointStatisticsDTO restEndpointStatisticsDTO = Mock()

		when:
		RestEndpointStatisticsDTO restEndpointStatisticsDTOOutput = commonRestEndpoint.entitiesStatistics()

		then:
		1 * commonDataReaderMock.entitiesStatistics() >> restEndpointStatisticsDTO
		0 * _
		restEndpointStatisticsDTOOutput == restEndpointStatisticsDTO
	}

	void "gets hits statistics from CommonDataReader"() {
		given:
		RestEndpointStatisticsDTO restEndpointStatisticsDTO = Mock()

		when:
		RestEndpointStatisticsDTO restEndpointStatisticsDTOOutput = commonRestEndpoint.hitsStatistics()

		then:
		1 * commonDataReaderMock.hitsStatistics() >> restEndpointStatisticsDTO
		0 * _
		restEndpointStatisticsDTOOutput == restEndpointStatisticsDTO
	}

	void "gets details from CommonDataReader"() {
		given:
		RestEndpointDetailsDTO restEndpointDetailsDTO = Mock()

		when:
		RestEndpointDetailsDTO restEndpointDetailsDTOOutput = commonRestEndpoint.details()

		then:
		1 * commonDataReaderMock.details() >> restEndpointDetailsDTO
		0 * _
		restEndpointDetailsDTOOutput == restEndpointDetailsDTO
	}

}
