package com.cezarykluczynski.stapi.server.common.documentation.service

import com.cezarykluczynski.stapi.contract.documentation.dto.DocumentDTO
import com.cezarykluczynski.stapi.contract.documentation.dto.DocumentationDTO
import spock.lang.Specification

class DocumentationProviderTest extends Specification {

	private DocumentationReader documentationReaderMock

	private DocumentationProvider documentationProvider

	void setup() {
		documentationReaderMock = Mock()
		documentationProvider = new DocumentationProvider(documentationReaderMock)
	}

	void "provides DocumentationDTO"() {
		given:
		List<DocumentDTO> restDocuments = Mock()
		List<DocumentDTO> soapDocuments = Mock()

		when:
		DocumentationDTO documentationDTO = documentationProvider.provide()

		then:
		1 * documentationReaderMock.readDirectory(DocumentationProvider.SWAGGER_DIRECTORY) >> restDocuments
		1 * documentationReaderMock.readDirectory(DocumentationProvider.WSDL_DIRECTORY) >> soapDocuments
		0 * _
		documentationDTO.restDocuments == restDocuments
		documentationDTO.soapDocuments == soapDocuments
	}

}
