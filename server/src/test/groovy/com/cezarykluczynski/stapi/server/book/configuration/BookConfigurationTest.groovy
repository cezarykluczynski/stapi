package com.cezarykluczynski.stapi.server.book.configuration

import com.cezarykluczynski.stapi.server.book.endpoint.BookSoapEndpoint
import com.cezarykluczynski.stapi.server.book.mapper.BookBaseRestMapper
import com.cezarykluczynski.stapi.server.book.mapper.BookBaseSoapMapper
import com.cezarykluczynski.stapi.server.book.mapper.BookFullRestMapper
import com.cezarykluczynski.stapi.server.book.mapper.BookFullSoapMapper
import com.cezarykluczynski.stapi.server.book.endpoint.BookRestEndpoint
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

import javax.xml.ws.Endpoint

class BookConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private BookConfiguration bookConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		bookConfiguration = new BookConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "Book SOAP endpoint is created"() {
		given:
		Endpoint endpoint = Mock()

		when:
		Endpoint endpointOutput = bookConfiguration.bookEndpoint()

		then:
		1 * endpointFactoryMock.createSoapEndpoint(BookSoapEndpoint, BookSoapEndpoint.ADDRESS) >> endpoint
		0 * _
		endpointOutput == endpoint
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

	void "BookBaseSoapMapper is created"() {
		when:
		BookBaseSoapMapper bookBaseSoapMapper = bookConfiguration.bookBaseSoapMapper()

		then:
		bookBaseSoapMapper != null
	}

	void "BookFullSoapMapper is created"() {
		when:
		BookFullSoapMapper bookFullSoapMapper = bookConfiguration.bookFullSoapMapper()

		then:
		bookFullSoapMapper != null
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
