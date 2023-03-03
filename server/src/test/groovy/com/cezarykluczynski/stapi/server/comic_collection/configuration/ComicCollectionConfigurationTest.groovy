package com.cezarykluczynski.stapi.server.comic_collection.configuration

import com.cezarykluczynski.stapi.server.comic_collection.endpoint.ComicCollectionRestEndpoint
import com.cezarykluczynski.stapi.server.comic_collection.endpoint.ComicCollectionV2RestEndpoint
import com.cezarykluczynski.stapi.server.comic_collection.mapper.ComicCollectionBaseRestMapper
import com.cezarykluczynski.stapi.server.comic_collection.mapper.ComicCollectionFullRestMapper
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

class ComicCollectionConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private ComicCollectionConfiguration comicCollectionConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		comicCollectionConfiguration = new ComicCollectionConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "ComicCollection REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = comicCollectionConfiguration.comicCollectionServer()

		then:
		1 * endpointFactoryMock.createRestEndpoint(ComicCollectionRestEndpoint, ComicCollectionRestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "ComicCollection V2 REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = comicCollectionConfiguration.comicCollectionV2Server()

		then:
		1 * endpointFactoryMock.createRestEndpoint(ComicCollectionV2RestEndpoint, ComicCollectionV2RestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "ComicCollectionBaseRestMapper is created"() {
		when:
		ComicCollectionBaseRestMapper comicCollectionBaseRestMapper = comicCollectionConfiguration.comicCollectionBaseRestMapper()

		then:
		comicCollectionBaseRestMapper != null
	}

	void "ComicCollectionFullRestMapper is created"() {
		when:
		ComicCollectionFullRestMapper comicCollectionFullRestMapper = comicCollectionConfiguration.comicCollectionFullRestMapper()

		then:
		comicCollectionFullRestMapper != null
	}

}
