package com.cezarykluczynski.stapi.server.common.documentation.service

import com.cezarykluczynski.stapi.client.v1.rest.api.AstronomicalObjectApi
import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectBase as RestAstronomicalObjectBase
import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectBaseResponse as RestAstronomicalObjectBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectFull as RestAstronomicalObjectFull
import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectFullResponse as RestAstronomicalObjectFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectBase as SoapAstronomicalObjectBase
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectBaseResponse as SoapAstronomicalObjectBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectFull as SoapAstronomicalObjectFull
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectFullResponse as SoapAstronomicalObjectFullResponse
import com.cezarykluczynski.stapi.contract.documentation.dto.ApiEntitiesDTO
import com.cezarykluczynski.stapi.contract.documentation.dto.ApiRequestResponseModelDTO
import com.cezarykluczynski.stapi.contract.documentation.dto.enums.ApiType
import com.cezarykluczynski.stapi.server.astronomicalObject.endpoint.AstronomicalObjectRestEndpoint
import com.cezarykluczynski.stapi.server.astronomicalObject.endpoint.AstronomicalObjectSoapEndpoint
import com.google.common.collect.Sets
import spock.lang.Specification

class ApiEntitiesConverterTest extends Specification {

	private static final String ENTITY_NAME = 'AstronomicalObject'

	private ApiEntitiesConverter apiEntitiesConverter

	void setup() {
		apiEntitiesConverter = new ApiEntitiesConverter()
	}

	@SuppressWarnings('LineLength')
	void "converts ApiRequestResponseModelDTO to map of maps of ApiEntitiesDTO"() {
		given:
		ApiRequestResponseModelDTO apiRequestResponseModelDTO = new ApiRequestResponseModelDTO()
		apiRequestResponseModelDTO.restRequests = Sets.newHashSet(AstronomicalObjectApi)
		apiRequestResponseModelDTO.restModels = Sets.newHashSet(RestAstronomicalObjectBase, RestAstronomicalObjectFull)
		apiRequestResponseModelDTO.restResponses = Sets.newHashSet(RestAstronomicalObjectBaseResponse, RestAstronomicalObjectFullResponse)
		apiRequestResponseModelDTO.restEndpoints = Sets.newHashSet(AstronomicalObjectRestEndpoint)
		apiRequestResponseModelDTO.soapRequests = Sets.newHashSet(AstronomicalObjectBaseRequest, AstronomicalObjectFullRequest)
		apiRequestResponseModelDTO.soapModels = Sets.newHashSet(SoapAstronomicalObjectBase, SoapAstronomicalObjectFull)
		apiRequestResponseModelDTO.soapResponses = Sets.newHashSet(SoapAstronomicalObjectBaseResponse, SoapAstronomicalObjectFullResponse)
		apiRequestResponseModelDTO.soapEndpoints = Sets.newHashSet(AstronomicalObjectSoapEndpoint)

		when:
		Map<ApiType, Map<String, ApiEntitiesDTO>> apiTypeEntitiesMap = apiEntitiesConverter.from(apiRequestResponseModelDTO)
		ApiEntitiesDTO restApiEntitiesDTO = apiTypeEntitiesMap.get(ApiType.REST).get(ENTITY_NAME)
		ApiEntitiesDTO soapApiEntitiesDTO = apiTypeEntitiesMap.get(ApiType.SOAP).get(ENTITY_NAME)

		then:
		restApiEntitiesDTO.apiType == ApiType.REST
		restApiEntitiesDTO.entityName == ENTITY_NAME
		restApiEntitiesDTO.baseRequest == AstronomicalObjectApi
		restApiEntitiesDTO.fullRequest == AstronomicalObjectApi
		restApiEntitiesDTO.baseEntity == RestAstronomicalObjectBase
		restApiEntitiesDTO.fullEntity == RestAstronomicalObjectFull
		restApiEntitiesDTO.baseResponse == RestAstronomicalObjectBaseResponse
		restApiEntitiesDTO.fullResponse == RestAstronomicalObjectFullResponse
		restApiEntitiesDTO.endpoint == AstronomicalObjectRestEndpoint
		soapApiEntitiesDTO.apiType == ApiType.SOAP
		soapApiEntitiesDTO.entityName == ENTITY_NAME
		soapApiEntitiesDTO.baseRequest == AstronomicalObjectBaseRequest
		soapApiEntitiesDTO.fullRequest == AstronomicalObjectFullRequest
		soapApiEntitiesDTO.baseEntity == SoapAstronomicalObjectBase
		soapApiEntitiesDTO.fullEntity == SoapAstronomicalObjectFull
		soapApiEntitiesDTO.baseResponse == SoapAstronomicalObjectBaseResponse
		soapApiEntitiesDTO.fullResponse == SoapAstronomicalObjectFullResponse
		soapApiEntitiesDTO.endpoint == AstronomicalObjectSoapEndpoint
	}

}
