package com.cezarykluczynski.stapi.server.common.documentation.service

import com.cezarykluczynski.stapi.server.common.documentation.dto.DocumentDTO
import com.cezarykluczynski.stapi.server.common.documentation.dto.enums.DocumentType
import spock.lang.Specification

class DocumentationReaderTest extends Specification {

	private DocumentationReader documentationReader

	void setup() {
		documentationReader = new DocumentationReader()
	}

	void "reads directory into list of DocumentDTO"() {
		given:
		String rootDirectory = '../contract/src/main/resources/v1/swagger'
		String contractDirectory = 'contract/src/main/resources/v1/swagger'
		String directory = new File(rootDirectory).directory ? rootDirectory : contractDirectory

		when:
		List<DocumentDTO> documentDTOList = documentationReader.readDirectory(directory)
		DocumentDTO documentDTO = documentationReader.readDirectory(directory).stream()
					.filter { it.path.endsWith('.yaml') }
					.findFirst().get()

		then:
		documentDTOList.size() == 2
		documentDTO.path.endsWith '.yaml'
		documentDTO.type == DocumentType.YAML
	}

}
