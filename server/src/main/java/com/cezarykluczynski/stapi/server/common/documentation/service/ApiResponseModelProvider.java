package com.cezarykluczynski.stapi.server.common.documentation.service;

import com.cezarykluczynski.stapi.contract.documentation.dto.ApiResponseModelDTO;
import com.google.common.collect.Lists;
import org.reflections.Reflections;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

@Service
public class ApiResponseModelProvider {

	private static final String REST_GENERATED_MODEL = "com.cezarykluczynski.stapi.client.v1.rest.model";
	private static final String SOAP_GENERATED_MODEL = "com.cezarykluczynski.stapi.client.v1.soap";
	private static final List<String> ENTITY_SUFFIXES = Lists.newArrayList("Base", "Full");
	private static final List<String> RESPONSE_SUFFIXES = Lists.newArrayList("BaseResponse", "FullResponse");

	private final ReflectionReader reflectionReader;

	private final ClassNameFilter classNameFilter;

	@Inject
	public ApiResponseModelProvider(ReflectionReader reflectionReader, ClassNameFilter classNameFilter) {
		this.reflectionReader = reflectionReader;
		this.classNameFilter = classNameFilter;
	}

	ApiResponseModelDTO provide() {
		Reflections restReflections = reflectionReader.readPackage(REST_GENERATED_MODEL);
		Reflections soapReflections = reflectionReader.readPackage(SOAP_GENERATED_MODEL);

		ApiResponseModelDTO apiResponseModelDTO = new ApiResponseModelDTO();
		apiResponseModelDTO.setRestModels(getModels(restReflections));
		apiResponseModelDTO.setRestResponses(getResponses(restReflections));
		apiResponseModelDTO.setSoapModels(getModels(soapReflections));
		apiResponseModelDTO.setSoapResponses(getResponses(soapReflections));

		return apiResponseModelDTO;
	}

	private Set<Class> getModels(Reflections reflections) {
		return classNameFilter.getClassesEndingWith(reflections.getAllTypes(), ENTITY_SUFFIXES);
	}

	private Set<Class> getResponses(Reflections reflections) {
		return classNameFilter.getClassesEndingWith(reflections.getAllTypes(), RESPONSE_SUFFIXES);
	}

}
