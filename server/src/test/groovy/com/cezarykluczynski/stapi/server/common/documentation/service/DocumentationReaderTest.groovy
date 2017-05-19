package com.cezarykluczynski.stapi.server.common.documentation.service

import com.cezarykluczynski.stapi.contract.documentation.dto.DocumentDTO
import com.cezarykluczynski.stapi.contract.documentation.dto.enums.DocumentType
import spock.lang.Specification

class DocumentationReaderTest extends Specification {

	private DocumentationReader documentationReader

	void setup() {
		documentationReader = new DocumentationReader()
	}

	void "reads directory into list of DocumentDTO, recursively"() {
		given:
		String rootDirectory = '../contract/src/main/resources/v1/swagger/book'
		String contractDirectory = 'contract/src/main/resources/v1/swagger/book'
		String directory = new File(rootDirectory).isDirectory() ? rootDirectory : contractDirectory

		when:
		List<DocumentDTO> documentDTOList = documentationReader.readDirectory(directory)
		DocumentDTO documentDTO = documentationReader.readDirectory(directory).stream()
					.filter { it.path.endsWith('bookBase.yaml') }
					.findFirst().get()
		then:
		documentDTOList.size() == 7
		documentDTO.path.endsWith 'bookBase.yaml'
		documentDTO.content.contains 'rolePlayingBook:'
		documentDTO.type == DocumentType.YAML
	}

}
