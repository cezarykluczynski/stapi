package com.cezarykluczynski.stapi.server.common.endpoint

import com.cezarykluczynski.stapi.contract.documentation.dto.DocumentationDTO
import com.cezarykluczynski.stapi.server.common.dto.PongDTO
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointDetailsDTO
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointStatisticsDTO
import com.cezarykluczynski.stapi.server.common.reader.CommonDataReader
import spock.lang.Specification

import javax.ws.rs.core.Response

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

	void "gets documentation from CommonDataReader"() {
		given:
		DocumentationDTO documentationDTO = Mock()

		when:
		DocumentationDTO documentationDTOOutput = commonRestEndpoint.documentation()

		then:
		1 * commonDataReaderMock.documentation() >> documentationDTO
		0 * _
		documentationDTOOutput == documentationDTO
	}

	void "responds to ping"() {
		when:
		PongDTO pongDTO = commonRestEndpoint.ping()

		then:
		pongDTO.pong == 'pong'
	}

	void "gets zipped REST documentation"() {
		given:
		Response response = Mock()

		when:
		Response responseOutput = commonRestEndpoint.restSpecsZip()

		then:
		1 * commonDataReaderMock.restSpecsZip() >> response
		0 * _
		responseOutput == response
	}

	void "gets zipped SOAP documentation"() {
		given:
		Response response = Mock()

		when:
		Response responseOutput = commonRestEndpoint.soapContractsZip()

		then:
		1 * commonDataReaderMock.soapContractsZip() >> response
		0 * _
		responseOutput == response
	}

}
