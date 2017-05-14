package com.cezarykluczynski.stapi.server.common.documentation.service

import com.cezarykluczynski.stapi.contract.documentation.dto.ApiEntitiesDTO
import com.cezarykluczynski.stapi.contract.documentation.dto.DocumentationDTO
import com.cezarykluczynski.stapi.contract.documentation.dto.EndpointDocumentationDTO
import com.cezarykluczynski.stapi.contract.documentation.dto.enums.ApiType
import com.google.common.collect.Maps
import spock.lang.Specification

class DocumentationFactoryTest extends Specification {

	private static final String ENTITY_NAME = 'AstronomicalObject'

	private RestApiEntitiesFactory restApiEntitiesFactoryMock

	private SoapApiEntitiesFactory soapApiEntitiesFactoryMock

	private DocumentationFactory documentationFactory

	void setup() {
		restApiEntitiesFactoryMock = Mock()
		soapApiEntitiesFactoryMock = Mock()
		documentationFactory = new DocumentationFactory(restApiEntitiesFactoryMock, soapApiEntitiesFactoryMock)
	}

	void "creates DocumentationDTO"() {
		given:
		Map<ApiType, Map<String, ApiEntitiesDTO>> apiTypeEntitiesMap = Maps.newHashMap()
		Map<String, ApiEntitiesDTO> restApiEntitiesMap = Maps.newHashMap()
		Map<String, ApiEntitiesDTO> soapApiEntitiesMap = Maps.newHashMap()
		apiTypeEntitiesMap.put(ApiType.REST, restApiEntitiesMap)
		apiTypeEntitiesMap.put(ApiType.SOAP, soapApiEntitiesMap)
		ApiEntitiesDTO restApiEntitiesDTO = new ApiEntitiesDTO()
		ApiEntitiesDTO soapApiEntitiesDTO = new ApiEntitiesDTO()
		restApiEntitiesMap.put(ENTITY_NAME, restApiEntitiesDTO)
		soapApiEntitiesMap.put(ENTITY_NAME, soapApiEntitiesDTO)
		EndpointDocumentationDTO restEndpointDocumentationDTO = Mock()
		EndpointDocumentationDTO soapEndpointDocumentationDTO = Mock()

		when:
		DocumentationDTO documentationDTO = documentationFactory.create(apiTypeEntitiesMap)

		then:
		1 * restApiEntitiesFactoryMock.create(restApiEntitiesDTO) >> restEndpointDocumentationDTO
		1 * soapApiEntitiesFactoryMock.create(soapApiEntitiesDTO) >> soapEndpointDocumentationDTO
		0 * _
		documentationDTO.entityDocumentations[0].restEndpointDocumentation == restEndpointDocumentationDTO
		documentationDTO.entityDocumentations[0].soapEndpointDocumentation == soapEndpointDocumentationDTO
	}

}
