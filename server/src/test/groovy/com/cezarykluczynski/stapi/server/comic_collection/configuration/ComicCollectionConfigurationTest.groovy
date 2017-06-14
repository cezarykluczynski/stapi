package com.cezarykluczynski.stapi.server.comic_collection.configuration

import com.cezarykluczynski.stapi.server.comic_collection.endpoint.ComicCollectionRestEndpoint
import com.cezarykluczynski.stapi.server.comic_collection.endpoint.ComicCollectionSoapEndpoint
import com.cezarykluczynski.stapi.server.comic_collection.mapper.ComicCollectionBaseRestMapper
import com.cezarykluczynski.stapi.server.comic_collection.mapper.ComicCollectionBaseSoapMapper
import com.cezarykluczynski.stapi.server.comic_collection.mapper.ComicCollectionFullRestMapper
import com.cezarykluczynski.stapi.server.comic_collection.mapper.ComicCollectionFullSoapMapper
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

import javax.xml.ws.Endpoint

class ComicCollectionConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private ComicCollectionConfiguration comicCollectionConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		comicCollectionConfiguration = new ComicCollectionConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "ComicCollection SOAP endpoint is created"() {
		given:
		Endpoint endpoint = Mock()

		when:
		Endpoint endpointOutput = comicCollectionConfiguration.comicCollectionEndpoint()

		then:
		1 * endpointFactoryMock.createSoapEndpoint(ComicCollectionSoapEndpoint, ComicCollectionSoapEndpoint.ADDRESS) >> endpoint
		0 * _
		endpointOutput == endpoint
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

	void "ComicCollectionBaseSoapMapper is created"() {
		when:
		ComicCollectionBaseSoapMapper comicCollectionBaseSoapMapper = comicCollectionConfiguration.comicCollectionBaseSoapMapper()

		then:
		comicCollectionBaseSoapMapper != null
	}

	void "ComicCollectionFullSoapMapper is created"() {
		when:
		ComicCollectionFullSoapMapper comicCollectionFullSoapMapper = comicCollectionConfiguration.comicCollectionFullSoapMapper()

		then:
		comicCollectionFullSoapMapper != null
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
