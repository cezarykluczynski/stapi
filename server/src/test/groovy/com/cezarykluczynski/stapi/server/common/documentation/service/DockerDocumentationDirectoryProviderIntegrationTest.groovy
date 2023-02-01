package com.cezarykluczynski.stapi.server.common.documentation.service

import spock.lang.Requires
import spock.lang.Specification

@Requires({
	new File(DockerDocumentationDirectoryProvider.SWAGGER_DIRECTORY).exists()
})
class DockerDocumentationDirectoryProviderIntegrationTest extends Specification {

	private DockerDocumentationDirectoryProvider warDocumentationDirectoryProvider

	void setup() {
		warDocumentationDirectoryProvider = new DockerDocumentationDirectoryProvider()
	}

	@SuppressWarnings('LineLength')
	void "provides Swagger directory"() {
		expect:
		warDocumentationDirectoryProvider.swaggerDirectory.replaceAll('\\\\', '/').contains(DockerDocumentationDirectoryProvider.SWAGGER_DIRECTORY)
	}

	void "provides temporary directory"() {
		expect:
		warDocumentationDirectoryProvider.temporaryDirectory.replaceAll('\\\\', '/').contains(DockerDocumentationDirectoryProvider.BUILD_DIRECTORY)
	}

}
