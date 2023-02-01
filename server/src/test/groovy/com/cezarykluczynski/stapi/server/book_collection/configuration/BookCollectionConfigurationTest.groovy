package com.cezarykluczynski.stapi.server.book_collection.configuration

import com.cezarykluczynski.stapi.server.book_collection.endpoint.BookCollectionRestEndpoint
import com.cezarykluczynski.stapi.server.book_collection.mapper.BookCollectionBaseRestMapper
import com.cezarykluczynski.stapi.server.book_collection.mapper.BookCollectionFullRestMapper
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

class BookCollectionConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private BookCollectionConfiguration bookCollectionConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		bookCollectionConfiguration = new BookCollectionConfiguration(endpointFactory: endpointFactoryMock)
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
