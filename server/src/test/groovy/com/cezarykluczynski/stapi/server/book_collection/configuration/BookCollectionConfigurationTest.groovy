package com.cezarykluczynski.stapi.server.book_collection.configuration

import com.cezarykluczynski.stapi.server.book_collection.endpoint.BookCollectionRestEndpoint
import com.cezarykluczynski.stapi.server.book_collection.endpoint.BookCollectionSoapEndpoint
import com.cezarykluczynski.stapi.server.book_collection.mapper.BookCollectionBaseRestMapper
import com.cezarykluczynski.stapi.server.book_collection.mapper.BookCollectionBaseSoapMapper
import com.cezarykluczynski.stapi.server.book_collection.mapper.BookCollectionFullRestMapper
import com.cezarykluczynski.stapi.server.book_collection.mapper.BookCollectionFullSoapMapper
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

import javax.xml.ws.Endpoint

class BookCollectionConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private BookCollectionConfiguration bookCollectionConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		bookCollectionConfiguration = new BookCollectionConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "BookCollection SOAP endpoint is created"() {
		given:
		Endpoint endpoint = Mock()

		when:
		Endpoint endpointOutput = bookCollectionConfiguration.bookCollectionEndpoint()

		then:
		1 * endpointFactoryMock.createSoapEndpoint(BookCollectionSoapEndpoint, BookCollectionSoapEndpoint.ADDRESS) >> endpoint
		0 * _
		endpointOutput == endpoint
	}

	void "BookCollection REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = bookCollectionConfiguration.bookCollectionServer()

		then:
		1 * endpointFactoryMock.createRestEndpoint(BookCollectionRestEndpoint, BookCollectionRestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "BookCollectionBaseSoapMapper is created"() {
		when:
		BookCollectionBaseSoapMapper bookCollectionBaseSoapMapper = bookCollectionConfiguration.bookCollectionBaseSoapMapper()

		then:
		bookCollectionBaseSoapMapper != null
	}

	void "BookCollectionFullSoapMapper is created"() {
		when:
		BookCollectionFullSoapMapper bookCollectionFullSoapMapper = bookCollectionConfiguration.bookCollectionFullSoapMapper()

		then:
		bookCollectionFullSoapMapper != null
	}

	void "BookCollectionBaseRestMapper is created"() {
		when:
		BookCollectionBaseRestMapper bookCollectionBaseRestMapper = bookCollectionConfiguration.bookCollectionBaseRestMapper()

		then:
		bookCollectionBaseRestMapper != null
	}

	void "BookCollectionFullRestMapper is created"() {
		when:
		BookCollectionFullRestMapper bookCollectionFullRestMapper = bookCollectionConfiguration.bookCollectionFullRestMapper()

		then:
		bookCollectionFullRestMapper != null
	}

}
