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
		String contractDirectory = './contract/src/main/resources/v1/swagger/book'
		String directory = new File(rootDirectory).isDirectory() ? rootDirectory : contractDirectory

		when:
		List<DocumentDTO> documentDTOList = documentationReader.readDirectory(directory)

		then:
		documentDTOList.size() == 7
		documentDTOList[0].path.endsWith 'bookBase.yaml'
		documentDTOList[0].content.contains 'rolePlayingBook:'
		documentDTOList[0].type == DocumentType.YAML
		documentDTOList[1].path.endsWith 'bookBaseResponse.yaml'
		documentDTOList[2].path.endsWith 'bookFull.yaml'
		documentDTOList[3].path.endsWith 'bookFullResponse.yaml'
		documentDTOList[4].path.endsWith 'bookHeader.yaml'
		documentDTOList[5].path.endsWith 'book.path.yaml'
		documentDTOList[6].path.endsWith 'bookSearch.path.yaml'
	}

}
