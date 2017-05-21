package com.cezarykluczynski.stapi.server.common.reader

import com.cezarykluczynski.stapi.contract.documentation.dto.DocumentationDTO
import com.cezarykluczynski.stapi.server.common.documentation.service.DocumentationProvider
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointDetailsDTO
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointStatisticsDTO
import spock.lang.Specification

import javax.ws.rs.core.Response

class CommonDataReaderTest extends Specification {

	private CommonEntitiesStatisticsReader commonEntitiesStatisticsReaderMock

	private CommonEntitiesDetailsReader commonEntitiesDetailsReaderMock

	private CommonHitsStatisticsReader commonHitsStatisticsReaderMock

	private DocumentationProvider documentationProvider

	private CommonDataReader commonDataReader

	void setup() {
		commonEntitiesStatisticsReaderMock = Mock()
		commonEntitiesDetailsReaderMock = Mock()
		commonHitsStatisticsReaderMock = Mock()
		documentationProvider = Mock()
		commonDataReader = new CommonDataReader(commonEntitiesStatisticsReaderMock, commonEntitiesDetailsReaderMock, commonHitsStatisticsReaderMock,
				documentationProvider)
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

	void "gets hits statistics from CommonHitsStatisticsReader"() {
		given:
		RestEndpointStatisticsDTO restEndpointStatisticsDTO = Mock()

		when:
		RestEndpointStatisticsDTO restEndpointStatisticsDTOOutput = commonDataReader.hitsStatistics()

		then:
		1 * commonHitsStatisticsReaderMock.hitsStatistics() >> restEndpointStatisticsDTO
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

	void "gets documentation from DocumentationProvider"() {
		given:
		DocumentationDTO documentationDTO = Mock()

		when:
		DocumentationDTO documentationDTOOutput = commonDataReader.documentation()

		then:
		1 * documentationProvider.provideDocumentation() >> documentationDTO
		0 * _
		documentationDTOOutput == documentationDTO
	}

	void "gets zipped REST documentation from DocumentationProvider"() {
		given:
		Response response = Mock()

		when:
		Response responseOutput = commonDataReader.restSpecsZip()

		then:
		1 * documentationProvider.provideRestSpecsZip() >> response
		0 * _
		responseOutput == response
	}

	void "gets zipped SOAP documentation from DocumentationProvider"() {
		given:
		Response response = Mock()

		when:
		Response responseOutput = commonDataReader.soapContractsZip()

		then:
		1 * documentationProvider.provideSoapContractsZip() >> response
		0 * _
		responseOutput == response
	}

}
