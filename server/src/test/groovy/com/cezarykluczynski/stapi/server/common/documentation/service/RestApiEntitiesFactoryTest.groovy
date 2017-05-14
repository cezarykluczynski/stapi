package com.cezarykluczynski.stapi.server.common.documentation.service

import com.cezarykluczynski.stapi.client.v1.rest.api.AstronomicalObjectApi
import com.cezarykluczynski.stapi.contract.documentation.dto.ApiEntitiesDTO
import com.cezarykluczynski.stapi.contract.documentation.dto.EndpointDocumentationDTO
import com.cezarykluczynski.stapi.contract.documentation.dto.EndpointMethodDTO
import com.cezarykluczynski.stapi.contract.documentation.dto.enums.SwaggerMethodType
import spock.lang.Specification

import java.lang.reflect.Method

class RestApiEntitiesFactoryTest extends Specification {

	private RestEndpointMethodFactory restEndpointMethodFactoryMock

	private RestApiEntitiesFactory restApiEntitiesFactory

	void setup() {
		restEndpointMethodFactoryMock = Mock()
		restApiEntitiesFactory = new RestApiEntitiesFactory(restEndpointMethodFactoryMock)
	}

	void "creates EndpointDocumentationDTO from ApiEntitiesDTO"() {
		given:
		ApiEntitiesDTO apiEntitiesDTO = new ApiEntitiesDTO()
		apiEntitiesDTO.entityName = 'AstronomicalObject'
		apiEntitiesDTO.baseRequest = AstronomicalObjectApi
		EndpointMethodDTO getMethod = Mock()
		EndpointMethodDTO searchGetMethod = Mock()
		EndpointMethodDTO searchPostMethod = Mock()

		when:
		EndpointDocumentationDTO endpointDocumentationDTO = restApiEntitiesFactory.create(apiEntitiesDTO)

		then:
		1 * restEndpointMethodFactoryMock.create(_ as Method, SwaggerMethodType.GET, apiEntitiesDTO) >> getMethod
		1 * restEndpointMethodFactoryMock.create(_ as Method, SwaggerMethodType.SEARCH_GET, apiEntitiesDTO) >> searchGetMethod
		1 * restEndpointMethodFactoryMock.create(_ as Method, SwaggerMethodType.SEARCH_POST, apiEntitiesDTO) >> searchPostMethod
		0 * _
		endpointDocumentationDTO.endpointMethods[0] == getMethod
		endpointDocumentationDTO.endpointMethods[1] == searchGetMethod
		endpointDocumentationDTO.endpointMethods[2] == searchPostMethod
	}

}
