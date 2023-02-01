package com.cezarykluczynski.stapi.server.book.configuration

import com.cezarykluczynski.stapi.server.book.endpoint.BookRestEndpoint
import com.cezarykluczynski.stapi.server.book.endpoint.BookV2RestEndpoint
import com.cezarykluczynski.stapi.server.book.mapper.BookBaseRestMapper
import com.cezarykluczynski.stapi.server.book.mapper.BookFullRestMapper
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

class BookConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private BookConfiguration bookConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		bookConfiguration = new BookConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "Book REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = bookConfiguration.bookServer()

		then:
		1 * endpointFactoryMock.createRestEndpoint(BookRestEndpoint, BookRestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "Book V2 REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = bookConfiguration.bookV2Server()

		then:
		1 * endpointFactoryMock.createRestEndpoint(BookV2RestEndpoint, BookV2RestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "BookBaseRestMapper is created"() {
		when:
		BookBaseRestMapper bookBaseRestMapper = bookConfiguration.bookBaseRestMapper()

		then:
		bookBaseRestMapper != null
	}

	void "BookFullRestMapper is created"() {
		when:
		BookFullRestMapper bookFullRestMapper = bookConfiguration.bookFullRestMapper()

		then:
		bookFullRestMapper != null
	}

}
