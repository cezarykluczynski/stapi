package com.cezarykluczynski.stapi.server.common.documentation.service

import com.cezarykluczynski.stapi.server.common.documentation.dto.DocumentDTO
import com.cezarykluczynski.stapi.server.common.documentation.dto.DocumentationDTO
import jakarta.ws.rs.core.Response
import org.springframework.core.io.ClassPathResource
import spock.lang.Specification

class DocumentationProviderTest extends Specification {

	private static final String SWAGGER_DIRECTORY = 'SWAGGER_DIRECTORY'
	private static final String TEMPORARY_DIRECTORY = 'TEMPORARY_DIRECTORY'
	private static final String APPLIATION_TEST_PROPERTIES = 'application-test.properties'

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

		when: 'documentation is requested'
		DocumentationDTO documentationDTO = documentationProvider.provideDocumentation()

		then: 'documentation is returned using DocumentationReader'
		1 * documentationDirectoryProviderMock.swaggerDirectory >> SWAGGER_DIRECTORY
		1 * documentationReaderMock.readDirectory(SWAGGER_DIRECTORY) >> restDocuments
		0 * _
		documentationDTO.restDocuments == restDocuments

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

	void "provides zipped file using path"() {
		given:
		ClassPathResource classPathResource = new ClassPathResource(APPLIATION_TEST_PROPERTIES)
		String filename = classPathResource.filename

		when:
		Response response = documentationProvider.provideFile(filename, APPLIATION_TEST_PROPERTIES)

		then:
		0 * _
		response.status == Response.Status.OK.statusCode
		response.mediaType.type == 'application'
		response.mediaType.subtype == 'octet-stream'
	}

	void "provides zipped file using classpath resource"() {
		given:
		ClassPathResource classPathResource = new ClassPathResource(APPLIATION_TEST_PROPERTIES)

		when:
		Response response = documentationProvider.provideFile(classPathResource, APPLIATION_TEST_PROPERTIES)

		then:
		0 * _
		response.status == Response.Status.OK.statusCode
		response.mediaType.type == 'application'
		response.mediaType.subtype == 'octet-stream'
	}

}
