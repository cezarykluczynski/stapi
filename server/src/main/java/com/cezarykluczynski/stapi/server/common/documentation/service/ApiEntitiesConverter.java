package com.cezarykluczynski.stapi.server.common.documentation.service;

import com.cezarykluczynski.stapi.contract.documentation.dto.ApiEntitiesDTO;
import com.cezarykluczynski.stapi.contract.documentation.dto.ApiRequestResponseModelDTO;
import com.cezarykluczynski.stapi.contract.documentation.dto.enums.ApiType;
import com.cezarykluczynski.stapi.util.tool.StringUtil;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
class ApiEntitiesConverter {

	Map<ApiType, Map<String, ApiEntitiesDTO>> from(ApiRequestResponseModelDTO apiRequestResponseModelDTO) {
		Map<ApiType, Multimap<String, Class>> apiTypeMultimapMap = getEntityToApiClassesMultimap(apiRequestResponseModelDTO);
		return fromMultimap(apiTypeMultimapMap);
	}

	private Map<ApiType, Map<String, ApiEntitiesDTO>> fromMultimap(Map<ApiType, Multimap<String, Class>> apiTypeMultimapMap) {
		Map<ApiType, Map<String, ApiEntitiesDTO>> apiTypeEntitiesMap = Maps.newHashMap();
		Map<String, ApiEntitiesDTO> restApiEntitiesDTOMap = Maps.newHashMap();
		Map<String, ApiEntitiesDTO> soapApiEntitiesDTOMap = Maps.newHashMap();

		apiTypeEntitiesMap.put(ApiType.REST, restApiEntitiesDTOMap);
		apiTypeEntitiesMap.put(ApiType.SOAP, soapApiEntitiesDTOMap);
		apiTypeMultimapMap.get(ApiType.REST).entries().forEach(entry -> addToApiEntityDTOMap(entry, restApiEntitiesDTOMap, ApiType.REST));
		apiTypeMultimapMap.get(ApiType.SOAP).entries().forEach(entry -> addToApiEntityDTOMap(entry, soapApiEntitiesDTOMap, ApiType.SOAP));

		return apiTypeEntitiesMap;
	}

	private void addToApiEntityDTOMap(Map.Entry<String, Class> entry, Map<String, ApiEntitiesDTO> apiEntitiesDTOMap, ApiType apiType) {
		String canonicalEntityName = entry.getKey();
		Class clazz = entry.getValue();
		String simpleClassName = clazz.getSimpleName();
		if (!apiEntitiesDTOMap.containsKey(canonicalEntityName)) {
			ApiEntitiesDTO apiEntitiesDTO = new ApiEntitiesDTO();
			apiEntitiesDTO.setApiType(apiType);
			apiEntitiesDTO.setEntityName(canonicalEntityName);
			apiEntitiesDTOMap.put(canonicalEntityName, apiEntitiesDTO);
		}
		ApiEntitiesDTO apiEntitiesDTO = apiEntitiesDTOMap.get(canonicalEntityName);

		if (simpleClassName.endsWith(Constants.BASE_REQUEST_SUFFIX)) {
			apiEntitiesDTO.setBaseRequest(clazz);
		} else if (simpleClassName.endsWith(Constants.FULL_REQUEST_SUFFIX)) {
			apiEntitiesDTO.setFullRequest(clazz);
		} else if (simpleClassName.endsWith(Constants.API_SUFFIX)) {
			apiEntitiesDTO.setBaseRequest(clazz);
			apiEntitiesDTO.setFullRequest(clazz);
		} else if (simpleClassName.endsWith(Constants.BASE_ENTITY_SUFFIX)) {
			apiEntitiesDTO.setBaseEntity(clazz);
		} else if (simpleClassName.endsWith(Constants.FULL_ENTITY_SUFFIX)) {
			apiEntitiesDTO.setFullEntity(clazz);
		} else if (simpleClassName.endsWith(Constants.BASE_RESPONSE_SUFFIX)) {
			apiEntitiesDTO.setBaseResponse(clazz);
		} else if (simpleClassName.endsWith(Constants.FULL_RESPONSE_SUFFIX)) {
			apiEntitiesDTO.setFullResponse(clazz);
		} else if (simpleClassName.endsWith(Constants.REST_ENDPOINT_SUFFIX) || simpleClassName.endsWith(Constants.SOAP_ENDPOINT_SUFFIX)) {
			apiEntitiesDTO.setEndpoint(clazz);
		} else {
			throw new RuntimeException(String.format("Could not map class with simple name \"%s\" to a ApiEntitiesDTO field", simpleClassName));
		}
	}

	private Map<ApiType, Multimap<String, Class>> getEntityToApiClassesMultimap(ApiRequestResponseModelDTO apiRequestResponseModelDTO) {
		Map<ApiType, Multimap<String, Class>> apiTypeMultimapMap = Maps.newHashMap();
		Multimap<String, Class> restEntityToApiClassesMultimap = ArrayListMultimap.create();
		Multimap<String, Class> soapEntityToApiClassesMultimap = ArrayListMultimap.create();
		apiTypeMultimapMap.put(ApiType.REST, restEntityToApiClassesMultimap);
		apiTypeMultimapMap.put(ApiType.SOAP, soapEntityToApiClassesMultimap);

		apiRequestResponseModelDTO.getRestRequests().forEach(clazz -> restEntityToApiClassesMultimap.put(requestNameFromModel(clazz), clazz));
		apiRequestResponseModelDTO.getRestModels().forEach(clazz -> restEntityToApiClassesMultimap.put(entityNameFromModel(clazz), clazz));
		apiRequestResponseModelDTO.getRestResponses().forEach(clazz -> restEntityToApiClassesMultimap.put(responseNameFromModel(clazz), clazz));
		apiRequestResponseModelDTO.getRestEndpoints().forEach(clazz -> restEntityToApiClassesMultimap.put(endpointNameFromModel(clazz), clazz));

		apiRequestResponseModelDTO.getSoapRequests().forEach(clazz -> soapEntityToApiClassesMultimap.put(requestNameFromModel(clazz), clazz));
		apiRequestResponseModelDTO.getSoapModels().forEach(clazz -> soapEntityToApiClassesMultimap.put(entityNameFromModel(clazz), clazz));
		apiRequestResponseModelDTO.getSoapResponses().forEach(clazz -> soapEntityToApiClassesMultimap.put(responseNameFromModel(clazz), clazz));
		apiRequestResponseModelDTO.getSoapEndpoints().forEach(clazz -> soapEntityToApiClassesMultimap.put(endpointNameFromModel(clazz), clazz));

		return apiTypeMultimapMap;
	}

	private String endpointNameFromModel(Class clazz) {
		return StringUtil.substringBeforeAny(clazz.getSimpleName(), Constants.ENDPOINT_SUFFIXES);
	}

	private static String requestNameFromModel(Class clazz) {
		return StringUtil.substringBeforeAny(clazz.getSimpleName(), Constants.REQUEST_SUFFIXES);
	}

	private static String entityNameFromModel(Class clazz) {
		return StringUtil.substringBeforeAny(clazz.getSimpleName(), Constants.ENTITY_SUFFIXES);
	}

	private static String responseNameFromModel(Class clazz) {
		return StringUtil.substringBeforeAny(clazz.getSimpleName(), Constants.RESPONSE_SUFFIXES);
	}

}
