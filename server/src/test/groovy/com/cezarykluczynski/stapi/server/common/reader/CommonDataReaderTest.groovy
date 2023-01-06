package com.cezarykluczynski.stapi.server.common.reader

import com.cezarykluczynski.stapi.server.common.documentation.dto.DocumentationDTO
import com.cezarykluczynski.stapi.server.common.dataversion.CommonDataVersionProvider
import com.cezarykluczynski.stapi.server.common.documentation.service.DocumentationProvider
import com.cezarykluczynski.stapi.server.common.documentation.service.TosAttachmentProvider
import com.cezarykluczynski.stapi.server.common.dto.DataVersionDTO
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointDetailsDTO
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointStatisticsDTO
import jakarta.ws.rs.core.Response
import spock.lang.Specification

class CommonDataReaderTest extends Specification {

	private CommonEntitiesStatisticsReader commonEntitiesStatisticsReaderMock

	private CommonEntitiesDetailsReader commonEntitiesDetailsReaderMock

	private DocumentationProvider documentationProviderMock

	private CommonDataVersionProvider commonDataVersionProviderMock

	private TosAttachmentProvider tosAttachmentProviderMock

	private CommonDataReader commonDataReader

	void setup() {
		commonEntitiesStatisticsReaderMock = Mock()
		commonEntitiesDetailsReaderMock = Mock()
		documentationProviderMock = Mock()
		commonDataVersionProviderMock = Mock()
		tosAttachmentProviderMock = Mock()
		commonDataReader = new CommonDataReader(commonEntitiesStatisticsReaderMock, commonEntitiesDetailsReaderMock, documentationProviderMock,
				commonDataVersionProviderMock, tosAttachmentProviderMock)
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

	void "gets documentation from DocumentationProvider"() {
		given:
		DocumentationDTO documentationDTO = Mock()

		when:
		DocumentationDTO documentationDTOOutput = commonDataReader.documentation()

		then:
		1 * documentationProviderMock.provideDocumentation() >> documentationDTO
		0 * _
		documentationDTOOutput == documentationDTO
	}

	void "gets zipped REST documentation from DocumentationProvider"() {
		given:
		Response response = Mock()

		when:
		Response responseOutput = commonDataReader.restSpecsZip()

		then:
		1 * documentationProviderMock.provideRestSpecsZip() >> response
		0 * _
		responseOutput == response
	}

	void "gets zipped SOAP documentation from DocumentationProvider"() {
		given:
		Response response = Mock()

		when:
		Response responseOutput = commonDataReader.soapContractsZip()

		then:
		1 * documentationProviderMock.provideSoapContractsZip() >> response
		0 * _
		responseOutput == response
	}

	void "gets zipped SOAP TOS form from DocumentationProvider"() {
		given:
		Response response = Mock()

		when:
		Response responseOutput = commonDataReader.tosFormZip()

		then:
		1 * tosAttachmentProviderMock.provide('form.docx') >> response
		0 * _
		responseOutput == response
	}

	void "gets data version"() {
		given:
		DataVersionDTO dataVersionDTO = Mock()

		when:
		DataVersionDTO dataVersionDTOOutput = commonDataReader.dataVersion()

		then:
		1 * commonDataVersionProviderMock.provide() >> dataVersionDTO
		0 * _
		dataVersionDTOOutput == dataVersionDTO
	}

}
