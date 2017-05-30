package com.cezarykluczynski.stapi.server.common.documentation.service

import com.cezarykluczynski.stapi.contract.documentation.dto.DocumentDTO
import com.cezarykluczynski.stapi.contract.documentation.dto.DocumentationDTO
import spock.lang.Specification

import javax.ws.rs.core.Response

class DocumentationProviderTest extends Specification {

	private static final String SWAGGER_DIRECTORY = 'SWAGGER_DIRECTORY'
	private static final String WSDL_DIRECTORY = 'WSDL_DIRECTORY'
	private static final String TEMPORARY_DIRECTORY = 'TEMPORARY_DIRECTORY'

	private DocumentationReader documentationReaderMock

	private DocumentationZipper documentationZipperMock

	private DocumentationDirectoryProvider documentationDirectoryProviderMock

	private DocumentationProvider documentationProvider

	void setup() {
		documentationReaderMock = Mock()
		documentationZipperMock = Mock()
		documentationDirectoryProviderMock = Mock()
		documentationProvider = new DocumentationProvider(documentationReaderMock, documentationZipperMock, documentationDirectoryProviderMock)
	}

	void "provides DocumentationDTO"() {
		given:
		List<DocumentDTO> restDocuments = Mock()
		List<DocumentDTO> soapDocuments = Mock()

		when: 'documentation is requested'
		DocumentationDTO documentationDTO = documentationProvider.provideDocumentation()

		then: 'documentation is returned using DocumentationReader'
		1 * documentationDirectoryProviderMock.swaggerDirectory >> SWAGGER_DIRECTORY
		1 * documentationDirectoryProviderMock.wsdlDirectory >> WSDL_DIRECTORY
		1 * documentationReaderMock.readDirectory(SWAGGER_DIRECTORY) >> restDocuments
		1 * documentationReaderMock.readDirectory(WSDL_DIRECTORY) >> soapDocuments
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
		when:
		Response response = documentationProvider.provideRestSpecsZip()

		then:
		1 * documentationDirectoryProviderMock.temporaryDirectory >> TEMPORARY_DIRECTORY
		1 * documentationDirectoryProviderMock.swaggerDirectory >> SWAGGER_DIRECTORY
		1 * documentationZipperMock.zipDirectoryToFile(SWAGGER_DIRECTORY, _ as File)
		0 * _
		response.status == Response.Status.OK.statusCode
		response.mediaType.type == 'application'
		response.mediaType.subtype == 'octet-stream'
	}

	void "provides zipped SOAP contracts"() {
		when:
		Response response = documentationProvider.provideSoapContractsZip()

		then:
		1 * documentationDirectoryProviderMock.temporaryDirectory >> TEMPORARY_DIRECTORY
		1 * documentationDirectoryProviderMock.wsdlDirectory >> WSDL_DIRECTORY
		1 * documentationZipperMock.zipDirectoryToFile(WSDL_DIRECTORY, _ as File)
		0 * _
		response.status == Response.Status.OK.statusCode
		response.mediaType.type == 'application'
		response.mediaType.subtype == 'octet-stream'
	}

}
