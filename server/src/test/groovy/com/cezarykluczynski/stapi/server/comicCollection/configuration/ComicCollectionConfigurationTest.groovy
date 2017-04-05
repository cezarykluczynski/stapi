package com.cezarykluczynski.stapi.server.comicCollection.configuration

import com.cezarykluczynski.stapi.server.comicCollection.endpoint.ComicCollectionSoapEndpoint
import com.cezarykluczynski.stapi.server.comicCollection.mapper.ComicCollectionBaseRestMapper
import com.cezarykluczynski.stapi.server.comicCollection.mapper.ComicCollectionBaseSoapMapper
import com.cezarykluczynski.stapi.server.comicCollection.mapper.ComicCollectionFullRestMapper
import com.cezarykluczynski.stapi.server.comicCollection.mapper.ComicCollectionFullSoapMapper
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import spock.lang.Specification

import javax.xml.ws.Endpoint

class ComicCollectionConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private ComicCollectionConfiguration comicCollectionConfiguration

	void setup() {
		endpointFactoryMock = Mock(EndpointFactory)
		comicCollectionConfiguration = new ComicCollectionConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "ComicCollection SOAP endpoint is created"() {
		given:
		Endpoint endpoint = Mock(Endpoint)

		when:
		Endpoint endpointOutput = comicCollectionConfiguration.comicCollectionEndpoint()

		then:
		1 * endpointFactoryMock.createSoapEndpoint(ComicCollectionSoapEndpoint, ComicCollectionSoapEndpoint.ADDRESS) >> endpoint
		0 * _
		endpointOutput == endpoint
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
