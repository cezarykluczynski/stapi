package com.cezarykluczynski.stapi.server.common.reader

import com.cezarykluczynski.stapi.server.common.dto.RestEndpointMappingsDTO
import spock.lang.Specification

class CommonDataReaderTest extends Specification {

	private CommonMappingsReader commonMappingsReaderMock

	private CommonDataReader commonDataReader

	void setup() {
		commonMappingsReaderMock = Mock()
		commonDataReader = new CommonDataReader(commonMappingsReaderMock)
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

}
