package com.cezarykluczynski.stapi.server.common.documentation.service;

import com.cezarykluczynski.stapi.contract.documentation.dto.DocumentationDTO;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class DocumentationProvider {

	private static final String SWAGGER_DIRECTORY = "./contract/src/main/resources/v1/swagger/";
	private static final String WSDL_DIRECTORY = "./contract/src/main/resources/v1/wsdl/";

	private final DocumentationReader documentationReader;

	@Inject
	public DocumentationProvider(DocumentationReader documentationReader) {
		this.documentationReader = documentationReader;
	}

	public DocumentationDTO provide() {
		DocumentationDTO documentationDTO = new DocumentationDTO();
		documentationDTO.setRestDocuments(documentationReader.readDirectory(SWAGGER_DIRECTORY));
		documentationDTO.setSoapDocuments(documentationReader.readDirectory(WSDL_DIRECTORY));
		return documentationDTO;
	}

}
