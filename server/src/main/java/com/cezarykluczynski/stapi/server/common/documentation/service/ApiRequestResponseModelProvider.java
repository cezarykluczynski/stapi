package com.cezarykluczynski.stapi.server.common.documentation.service;

import com.cezarykluczynski.stapi.contract.documentation.dto.ApiEndpointModelDTO;
import com.cezarykluczynski.stapi.contract.documentation.dto.ApiRequestResponseModelDTO;
import com.cezarykluczynski.stapi.contract.documentation.dto.ApiResponseModelDTO;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
class ApiRequestResponseModelProvider {

	private final ApiResponseModelProvider apiResponseModelProvider;

	private final ApiRequestModelProvider apiRequestModelProvider;

	@Inject
	ApiRequestResponseModelProvider(ApiResponseModelProvider apiResponseModelProvider, ApiRequestModelProvider apiRequestModelProvider) {
		this.apiResponseModelProvider = apiResponseModelProvider;
		this.apiRequestModelProvider = apiRequestModelProvider;
	}

	ApiRequestResponseModelDTO provide() {
		ApiResponseModelDTO apiResponseModelDTO = apiResponseModelProvider.provide();
		ApiEndpointModelDTO apiEndpointModelDTO = apiRequestModelProvider.provide();

		ApiRequestResponseModelDTO apiRequestResponseModelDTO = new ApiRequestResponseModelDTO();

		apiRequestResponseModelDTO.setRestRequests(apiResponseModelDTO.getRestRequests());
		apiRequestResponseModelDTO.setRestModels(apiResponseModelDTO.getRestModels());
		apiRequestResponseModelDTO.setRestResponses(apiResponseModelDTO.getRestResponses());
		apiRequestResponseModelDTO.setRestEndpoints(apiEndpointModelDTO.getRestEndpoints());

		apiRequestResponseModelDTO.setSoapRequests(apiResponseModelDTO.getSoapRequests());
		apiRequestResponseModelDTO.setSoapModels(apiResponseModelDTO.getSoapModels());
		apiRequestResponseModelDTO.setSoapResponses(apiResponseModelDTO.getSoapResponses());
		apiRequestResponseModelDTO.setSoapEndpoints(apiEndpointModelDTO.getSoapEndpoints());

		return apiRequestResponseModelDTO;
	}

}
