package com.cezarykluczynski.stapi.server.common.endpoint

import com.cezarykluczynski.stapi.server.common.dto.RestEndpointMappingsDTO
import com.cezarykluczynski.stapi.server.common.reader.CommonDataReader
import spock.lang.Specification

class CommonRestEndpointTest extends Specification {

	private CommonDataReader commonDataReaderMock

	private CommonRestEndpoint commonRestEndpoint

	void setup() {
		commonDataReaderMock = Mock()
		commonRestEndpoint = new CommonRestEndpoint(commonDataReaderMock)
	}

	void "gets mappings from CommonDataReader"() {
		given:
		RestEndpointMappingsDTO restEndpointMappingsDTO = Mock()

		when:
		RestEndpointMappingsDTO restEndpointMappingsDTOOutput = commonRestEndpoint.mappings()

		then:
		1 * commonDataReaderMock.mappings() >> restEndpointMappingsDTO
		0 * _
		restEndpointMappingsDTOOutput == restEndpointMappingsDTO
	}

}
