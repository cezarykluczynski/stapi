package com.cezarykluczynski.stapi.server.common.documentation.service

import jakarta.ws.rs.core.Response
import org.springframework.core.io.ClassPathResource
import spock.lang.Specification

class DocumentationProviderTest extends Specification {

	private static final String SWAGGER_DIRECTORY = 'SWAGGER_DIRECTORY'
	private static final String TEMPORARY_DIRECTORY = 'TEMPORARY_DIRECTORY'
	private static final String APPLIATION_TEST_PROPERTIES = 'application-test.properties'
	private static final String EMPTY_TEST_RESOURCE = 'empty-test-resource.txt'

	private DocumentationZipper documentationZipperMock

	private DocumentationDirectoryProvider documentationDirectoryProviderMock

	private DocumentationProvider documentationProvider

	void setup() {
		documentationZipperMock = Mock()
		documentationDirectoryProviderMock = Mock()
		documentationProvider = new DocumentationProvider(documentationZipperMock, documentationDirectoryProviderMock)
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
		ClassPathResource classPathResource = new ClassPathResource(EMPTY_TEST_RESOURCE)

		when:
		Response response = documentationProvider.provideFile(classPathResource, EMPTY_TEST_RESOURCE)

		then:
		0 * _
		response.status == Response.Status.OK.statusCode
		response.mediaType.type == 'application'
		response.mediaType.subtype == 'octet-stream'
	}

	void "provides stapi.yaml response"() {
		when:
		Response response = documentationProvider.provideStapiYaml()

		then:
		response.status == Response.Status.OK.statusCode
		response.mediaType.type == 'text'
		response.mediaType.subtype == 'plain'
		response.headers.get('Access-Control-Allow-Origin')[0] == '*'
		response.headers.get('Access-Control-Allow-Headers')[0] == '*'
		response.headers.get('Access-Control-Allow-Methods')[0] == 'GET'
		response.headers.get('Access-Control-Allow-Credentials')[0] == 'true'
		response.headers.get('Content-Disposition')[0] == 'inline; filename=stapi.yaml'
	}

}
