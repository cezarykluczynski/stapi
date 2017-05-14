package com.cezarykluczynski.stapi.server.common.documentation.service

import com.cezarykluczynski.stapi.contract.documentation.dto.ApiResponseModelDTO
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
		Reflections restReflections = Mock()
		Reflections soapReflections = Mock()
		Set<String> restTypes = Sets.newHashSet('RestType')
		Set<String> soapTypes = Sets.newHashSet('SoapType')
		Set<Class> restModels = Mock()
		Set<Class> restResponses = Mock()
		Set<Class> soapModels = Mock()
		Set<Class> soapResponses = Mock()

		when:
		ApiResponseModelDTO apiResponseModelDTO = apiResponseModelProvider.provide()

		then:
		1 * reflectionReaderMock.readPackage(ApiResponseModelProvider.REST_GENERATED_MODEL) >> restReflections
		1 * reflectionReaderMock.readPackage(ApiResponseModelProvider.SOAP_GENERATED_MODEL) >> soapReflections
		2 * restReflections.allTypes >> restTypes
		1 * classNameFilterMock.getClassesEndingWith(restTypes, ApiResponseModelProvider.ENTITY_SUFFIXES) >> restModels
		1 * classNameFilterMock.getClassesEndingWith(restTypes, ApiResponseModelProvider.RESPONSE_SUFFIXES) >> restResponses
		2 * soapReflections.allTypes >> soapTypes
		1 * classNameFilterMock.getClassesEndingWith(soapTypes, ApiResponseModelProvider.ENTITY_SUFFIXES) >> soapModels
		1 * classNameFilterMock.getClassesEndingWith(soapTypes, ApiResponseModelProvider.RESPONSE_SUFFIXES) >> soapResponses
		0 * _
		apiResponseModelDTO.restModels == restModels
		apiResponseModelDTO.restResponses == restResponses
		apiResponseModelDTO.soapModels == soapModels
		apiResponseModelDTO.soapResponses == soapResponses
	}

}
