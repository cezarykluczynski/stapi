package com.cezarykluczynski.stapi.server.common.documentation.service

import spock.lang.Requires
import spock.lang.Specification

@Requires({
	new File(WarDocumentationDirectoryProvider.SWAGGER_CLASSPATH_DIRECTORY).exists()
})
class WarDocumentationDirectoryProviderIntegrationTest extends Specification {

	private WarDocumentationDirectoryProvider warDocumentationDirectoryProvider

	void setup() {
		warDocumentationDirectoryProvider = new WarDocumentationDirectoryProvider()
	}

	@SuppressWarnings('LineLength')
	void "provides Swagger directory"() {
		expect:
		warDocumentationDirectoryProvider.swaggerDirectory.replaceAll('\\\\', '/').contains(WarDocumentationDirectoryProvider.SWAGGER_CLASSPATH_DIRECTORY)
	}

	void "provides WSDL directory"() {
		expect:
		warDocumentationDirectoryProvider.wsdlDirectory.replaceAll('\\\\', '/').contains(WarDocumentationDirectoryProvider.WSDL_CLASSPATH_DIRECTORY)
	}

	void "provides temporary directory"() {
		expect:
		warDocumentationDirectoryProvider.temporaryDirectory.replaceAll('\\\\', '/').contains(WarDocumentationDirectoryProvider.TMP_DIRECTORY)
	}

}
