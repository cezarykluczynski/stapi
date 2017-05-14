package com.cezarykluczynski.stapi.server.common.documentation.service;

import com.cezarykluczynski.stapi.contract.documentation.dto.ApiEntitiesDTO;
import com.cezarykluczynski.stapi.contract.documentation.dto.ApiRequestResponseModelDTO;
import com.cezarykluczynski.stapi.contract.documentation.dto.DocumentationDTO;
import com.cezarykluczynski.stapi.contract.documentation.dto.enums.ApiType;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Map;

@Service
public class DocumentationProvider {

	private final ApiRequestResponseModelProvider apiRequestResponseModelProvider;

	private final ApiEntitiesConverter apiEntitiesConverter;

	private final DocumentationFactory documentationFactory;

	@Inject
	public DocumentationProvider(ApiRequestResponseModelProvider apiRequestResponseModelProvider, ApiEntitiesConverter apiEntitiesConverter,
			DocumentationFactory documentationFactory) {
		this.apiRequestResponseModelProvider = apiRequestResponseModelProvider;
		this.apiEntitiesConverter = apiEntitiesConverter;
		this.documentationFactory = documentationFactory;
	}

	public DocumentationDTO provide() {
		ApiRequestResponseModelDTO apiRequestResponseModelDTO = apiRequestResponseModelProvider.provide();
		Map<ApiType, Map<String, ApiEntitiesDTO>> apiTypeEntitiesMap = apiEntitiesConverter.from(apiRequestResponseModelDTO);
		return documentationFactory.create(apiTypeEntitiesMap);
	}


}
