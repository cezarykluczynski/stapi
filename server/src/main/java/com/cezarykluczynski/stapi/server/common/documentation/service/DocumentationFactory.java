package com.cezarykluczynski.stapi.server.common.documentation.service;

import com.cezarykluczynski.stapi.contract.documentation.dto.ApiEntitiesDTO;
import com.cezarykluczynski.stapi.contract.documentation.dto.DocumentationDTO;
import com.cezarykluczynski.stapi.contract.documentation.dto.EntityDocumentationDTO;
import com.cezarykluczynski.stapi.contract.documentation.dto.enums.ApiType;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@Service
class DocumentationFactory {

	private final RestApiEntitiesFactory restApiEntitiesFactory;

	private final SoapApiEntitiesFactory soapApiEntitiesFactory;

	@Inject
	DocumentationFactory(RestApiEntitiesFactory restApiEntitiesFactory, SoapApiEntitiesFactory soapApiEntitiesFactory) {
		this.restApiEntitiesFactory = restApiEntitiesFactory;
		this.soapApiEntitiesFactory = soapApiEntitiesFactory;
	}

	DocumentationDTO create(Map<ApiType, Map<String, ApiEntitiesDTO>> apiTypeEntitiesMap) {
		DocumentationDTO documentationDTO = new DocumentationDTO();
		List<EntityDocumentationDTO> entityDocumentationDTOList = Lists.newArrayList();
		documentationDTO.setEntityDocumentations(entityDocumentationDTOList);

		apiTypeEntitiesMap.get(ApiType.REST).forEach((entityName, restApiEntitiesDTO) -> {
			ApiEntitiesDTO soapApiEntitiesDTO = apiTypeEntitiesMap.get(ApiType.SOAP).get(entityName);
			EntityDocumentationDTO entityDocumentationDTO = new EntityDocumentationDTO();
			entityDocumentationDTO.setRestEndpointDocumentation(restApiEntitiesFactory.create(restApiEntitiesDTO));
			entityDocumentationDTO.setSoapEndpointDocumentation(soapApiEntitiesFactory.create(soapApiEntitiesDTO));
			entityDocumentationDTOList.add(entityDocumentationDTO);
		});

		return documentationDTO;
	}

}
