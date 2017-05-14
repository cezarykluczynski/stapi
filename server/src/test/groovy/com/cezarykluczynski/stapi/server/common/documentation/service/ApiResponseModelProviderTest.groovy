package com.cezarykluczynski.stapi.server.common.documentation.service

import com.cezarykluczynski.stapi.contract.documentation.dto.ApiResponseModelDTO
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.reflections.Reflections
import spock.lang.Specification

class ApiResponseModelProviderTest extends Specification {

	private ReflectionReader reflectionReaderMock

	private ClassNameFilter classNameFilterMock

	private ApiResponseModelProvider apiResponseModelProvider

	void setup() {
		reflectionReaderMock = Mock()
		classNameFilterMock = Mock()
		apiResponseModelProvider = new ApiResponseModelProvider(reflectionReaderMock, classNameFilterMock)
	}

	void "provides ApiResponseModelDTO"() {
		given:
		Reflections restModelReflections = Mock()
		Reflections restApiReflections = Mock()
		Reflections soapModelReflections = Mock()
		Set<String> restModelTypes = Sets.newHashSet('RestModelType')
		Set<String> restApiTypes = Sets.newHashSet('RestApiType$51')
		Set<String> restApiTypesCleaned = Sets.newHashSet('RestApiType')
		Set<String> soapModelTypes = Sets.newHashSet('SoapApiType')
		Set<Class> restRequests = Mock()
		Set<Class> restModels = Mock()
		Set<Class> restResponses = Mock()
		Set<Class> soapRequests = Mock()
		Set<Class> soapModels = Mock()
		Set<Class> soapResponses = Mock()

		when:
		ApiResponseModelDTO apiResponseModelDTO = apiResponseModelProvider.provide()

		then:
		1 * reflectionReaderMock.readPackage(ApiResponseModelProvider.REST_GENERATED_MODEL) >> restModelReflections
		1 * reflectionReaderMock.readPackage(ApiResponseModelProvider.REST_GENERATED_API) >> restApiReflections
		1 * reflectionReaderMock.readPackage(ApiResponseModelProvider.SOAP_GENERATED_MODEL) >> soapModelReflections
		1 * restApiReflections.allTypes >> restApiTypes
		1 * classNameFilterMock.getClassesEndingWith(restApiTypesCleaned, Lists.newArrayList(Constants.API_SUFFIX)) >> restRequests
		2 * restModelReflections.allTypes >> restModelTypes
		1 * classNameFilterMock.getClassesEndingWith(restModelTypes, Constants.ENTITY_SUFFIXES) >> restModels
		1 * classNameFilterMock.getClassesEndingWith(restModelTypes, Constants.RESPONSE_SUFFIXES) >> restResponses
		3 * soapModelReflections.allTypes >> soapModelTypes
		1 * classNameFilterMock.getClassesEndingWith(soapModelTypes, Constants.REQUEST_SUFFIXES) >> soapRequests
		1 * classNameFilterMock.getClassesEndingWith(soapModelTypes, Constants.ENTITY_SUFFIXES) >> soapModels
		1 * classNameFilterMock.getClassesEndingWith(soapModelTypes, Constants.RESPONSE_SUFFIXES) >> soapResponses
		0 * _
		apiResponseModelDTO.restRequests == restRequests
		apiResponseModelDTO.restModels == restModels
		apiResponseModelDTO.restResponses == restResponses
		apiResponseModelDTO.soapRequests == soapRequests
		apiResponseModelDTO.soapModels == soapModels
		apiResponseModelDTO.soapResponses == soapResponses
	}

}
