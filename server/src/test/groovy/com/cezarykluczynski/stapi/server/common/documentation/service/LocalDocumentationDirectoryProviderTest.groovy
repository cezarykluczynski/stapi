package com.cezarykluczynski.stapi.server.common.documentation.service

import spock.lang.Specification

class LocalDocumentationDirectoryProviderTest extends Specification {

	private LocalDocumentationDirectoryProvider localDocumentationDirectoryProvider

	void setup() {
		localDocumentationDirectoryProvider = new LocalDocumentationDirectoryProvider()
	}

	void "provides Swagger directory"() {
		expect:
		localDocumentationDirectoryProvider.swaggerDirectory == LocalDocumentationDirectoryProvider.SWAGGER_DIRECTORY
	}

	void "provides WSDL directory"() {
		expect:
		localDocumentationDirectoryProvider.wsdlDirectory == LocalDocumentationDirectoryProvider.WSDL_DIRECTORY
	}

	void "provides temporary directory"() {
		expect:
		localDocumentationDirectoryProvider.temporaryDirectory == LocalDocumentationDirectoryProvider.BUILD_DIRECTORY
	}

}
