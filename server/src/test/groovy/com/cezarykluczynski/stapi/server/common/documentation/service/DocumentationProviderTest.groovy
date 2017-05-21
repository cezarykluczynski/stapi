package com.cezarykluczynski.stapi.server.common.documentation.service

import com.cezarykluczynski.stapi.contract.documentation.dto.DocumentDTO
import com.cezarykluczynski.stapi.contract.documentation.dto.DocumentationDTO
import org.apache.commons.io.FileUtils
import spock.lang.Specification

import javax.ws.rs.core.Response

class DocumentationProviderTest extends Specification {

	private DocumentationReader documentationReaderMock

	private DocumentationZipper documentationZipperMock

	private DocumentationProvider documentationProvider

	void setup() {
		documentationReaderMock = Mock()
		documentationZipperMock = Mock()
		documentationProvider = new DocumentationProvider(documentationReaderMock, documentationZipperMock)
	}

	void "provides DocumentationDTO"() {
		given:
		List<DocumentDTO> restDocuments = Mock()
		List<DocumentDTO> soapDocuments = Mock()

		when: 'documentation is requested'
		DocumentationDTO documentationDTO = documentationProvider.provideDocumentation()

		then: 'documentation is returned using DocumentationReader'
		1 * documentationReaderMock.readDirectory(DocumentationProvider.SWAGGER_DIRECTORY) >> restDocuments
		1 * documentationReaderMock.readDirectory(DocumentationProvider.WSDL_DIRECTORY) >> soapDocuments
		0 * _
		documentationDTO.restDocuments == restDocuments
		documentationDTO.soapDocuments == soapDocuments

		when: 'documentation is requested'
		DocumentationDTO documentationDTOCached = documentationProvider.provideDocumentation()

		then: 'cached documentation is returned'
		0 * _
		documentationDTOCached == documentationDTO
	}

	void "provides zipped REST specs"() {
		given:
		FileUtils.deleteQuietly(new File(DocumentationProvider.SWAGGER_ZIP_TARGET_FILE))

		when:
		Response response = documentationProvider.provideRestSpecsZip()

		then:
		1 * documentationZipperMock.zipDirectoryToFile(DocumentationProvider.SWAGGER_DIRECTORY, _ as File)
		0 * _
		response.status == Response.Status.OK.statusCode
		response.mediaType.type == 'application'
		response.mediaType.subtype == 'octet-stream'
	}

	void "provides zipped SOAP contracts"() {
		given:
		FileUtils.deleteQuietly(new File(DocumentationProvider.WSDL_ZIP_TARGET_FILE))

		when:
		Response response = documentationProvider.provideSoapContractsZip()

		then:
		1 * documentationZipperMock.zipDirectoryToFile(DocumentationProvider.WSDL_DIRECTORY, _ as File)
		0 * _
		response.status == Response.Status.OK.statusCode
		response.mediaType.type == 'application'
		response.mediaType.subtype == 'octet-stream'
	}

}
