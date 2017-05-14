package com.cezarykluczynski.stapi.server.common.documentation.service

import com.cezarykluczynski.stapi.contract.documentation.dto.ApiEntitiesDTO
import com.cezarykluczynski.stapi.contract.documentation.dto.ApiRequestResponseModelDTO
import com.cezarykluczynski.stapi.contract.documentation.dto.DocumentationDTO
import com.cezarykluczynski.stapi.contract.documentation.dto.enums.ApiType
import spock.lang.Specification

class DocumentationProviderTest extends Specification {

	private ApiRequestResponseModelProvider apiRequestResponseModelProviderMock

	private ApiEntitiesConverter apiEntitiesConverterMock

	private DocumentationFactory documentationFactoryMock

	private DocumentationProvider documentationProvider

	void setup() {
		apiRequestResponseModelProviderMock = Mock()
		apiEntitiesConverterMock = Mock()
		documentationFactoryMock = Mock()
		documentationProvider = new DocumentationProvider(apiRequestResponseModelProviderMock, apiEntitiesConverterMock, documentationFactoryMock)
	}

	void "provides DocumentationDTO"() {
		given:
		ApiRequestResponseModelDTO apiRequestResponseModelDTO = Mock()
		Map<ApiType, Map<String, ApiEntitiesDTO>> apiTypeEntitiesMap = Mock()
		DocumentationDTO documentationDTO = Mock()

		when:
		DocumentationDTO documentationDTOOutput = documentationProvider.provide()

		then:
		1 * apiRequestResponseModelProviderMock.provide() >> apiRequestResponseModelDTO
		1 * apiEntitiesConverterMock.from(apiRequestResponseModelDTO) >> apiTypeEntitiesMap
		1 * documentationFactoryMock.create(apiTypeEntitiesMap) >> documentationDTO
		0 * _
		documentationDTOOutput == documentationDTO
	}

}
