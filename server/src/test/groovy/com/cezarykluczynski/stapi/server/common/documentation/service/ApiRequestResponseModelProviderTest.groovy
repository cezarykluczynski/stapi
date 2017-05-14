package com.cezarykluczynski.stapi.server.common.documentation.service

import com.cezarykluczynski.stapi.contract.documentation.dto.ApiEndpointModelDTO
import com.cezarykluczynski.stapi.contract.documentation.dto.ApiRequestResponseModelDTO
import com.cezarykluczynski.stapi.contract.documentation.dto.ApiResponseModelDTO
import spock.lang.Specification

class ApiRequestResponseModelProviderTest extends Specification {

	private ApiResponseModelProvider apiResponseModelProviderMock

	private ApiRequestModelProvider apiRequestModelProviderMock

	private ApiRequestResponseModelProvider apiRequestResponseModelProvider

	void setup() {
		apiResponseModelProviderMock = Mock()
		apiRequestModelProviderMock = Mock()
		apiRequestResponseModelProvider = new ApiRequestResponseModelProvider(apiResponseModelProviderMock, apiRequestModelProviderMock)
	}

	void "provides ApiRequestResponseModelDTO"() {
		given:
		Set<Class> restRequests = Mock()
		Set<Class> restModels = Mock()
		Set<Class> restResponses = Mock()
		Set<Class> restEndpoints = Mock()
		Set<Class> soapRequests = Mock()
		Set<Class> soapModels = Mock()
		Set<Class> soapResponses = Mock()
		Set<Class> soapEndpoints = Mock()
		ApiResponseModelDTO apiResponseModelDTO = new ApiResponseModelDTO(
				restRequests: restRequests,
				restModels: restModels,
				restResponses: restResponses,
				soapRequests: soapRequests,
				soapModels: soapModels,
				soapResponses: soapResponses)
		ApiEndpointModelDTO apiRequestModelDTO = new ApiEndpointModelDTO(
				restEndpoints: restEndpoints,
				soapEndpoints: soapEndpoints)

		when:
		ApiRequestResponseModelDTO apiRequestResponseModelDTO = apiRequestResponseModelProvider.provide()

		then:
		1 * apiResponseModelProviderMock.provide() >> apiResponseModelDTO
		1 * apiRequestModelProviderMock.provide() >> apiRequestModelDTO
		0 * _
		apiRequestResponseModelDTO.restRequests == restRequests
		apiRequestResponseModelDTO.restModels == restModels
		apiRequestResponseModelDTO.restResponses == restResponses
		apiRequestResponseModelDTO.restEndpoints == restEndpoints
		apiRequestResponseModelDTO.soapRequests == soapRequests
		apiRequestResponseModelDTO.soapModels == soapModels
		apiRequestResponseModelDTO.soapResponses == soapResponses
		apiRequestResponseModelDTO.soapEndpoints == soapEndpoints
	}

}
