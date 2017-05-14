package com.cezarykluczynski.stapi.server.common.documentation.service;

import com.cezarykluczynski.stapi.contract.documentation.dto.ApiResponseModelDTO;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Set;
import java.util.stream.Collectors;

@Service
class ApiResponseModelProvider {

	private static final String REST_GENERATED_MODEL = "com.cezarykluczynski.stapi.client.v1.rest.model";
	private static final String REST_GENERATED_API = "com.cezarykluczynski.stapi.client.v1.rest.api";
	private static final String SOAP_GENERATED_MODEL = "com.cezarykluczynski.stapi.client.v1.soap";

	private final ReflectionReader reflectionReader;

	private final ClassNameFilter classNameFilter;

	@Inject
	ApiResponseModelProvider(ReflectionReader reflectionReader, ClassNameFilter classNameFilter) {
		this.reflectionReader = reflectionReader;
		this.classNameFilter = classNameFilter;
	}

	ApiResponseModelDTO provide() {
		Reflections restModelReflections = reflectionReader.readPackage(REST_GENERATED_MODEL);
		Reflections restApiReflections = reflectionReader.readPackage(REST_GENERATED_API);
		Reflections soapModelReflections = reflectionReader.readPackage(SOAP_GENERATED_MODEL);

		Set<String> restApiTypes = normalizeRestTypes(restApiReflections.getAllTypes());

		ApiResponseModelDTO apiResponseModelDTO = new ApiResponseModelDTO();
		apiResponseModelDTO.setRestRequests(getRestRequests(restApiTypes));
		apiResponseModelDTO.setRestModels(getModels(restModelReflections));
		apiResponseModelDTO.setRestResponses(getResponses(restModelReflections));
		apiResponseModelDTO.setSoapRequests(getSoapRequests(soapModelReflections));
		apiResponseModelDTO.setSoapModels(getModels(soapModelReflections));
		apiResponseModelDTO.setSoapResponses(getResponses(soapModelReflections));

		return apiResponseModelDTO;
	}

	private Set<String> normalizeRestTypes(Set<String> allTypes) {
		return allTypes.stream()
				.map(type -> StringUtils.substringBefore(type, "$"))
				.collect(Collectors.toSet());
	}

	private Set<Class> getRestRequests(Set<String> allTypes) {
		return classNameFilter.getClassesEndingWith(allTypes, Lists.newArrayList(Constants.API_SUFFIX));
	}

	private Set<Class> getSoapRequests(Reflections reflections) {
		return classNameFilter.getClassesEndingWith(reflections.getAllTypes(), Constants.REQUEST_SUFFIXES);
	}

	private Set<Class> getModels(Reflections reflections) {
		return classNameFilter.getClassesEndingWith(reflections.getAllTypes(), Constants.ENTITY_SUFFIXES);
	}

	private Set<Class> getResponses(Reflections reflections) {
		return classNameFilter.getClassesEndingWith(reflections.getAllTypes(), Constants.RESPONSE_SUFFIXES);
	}

}
