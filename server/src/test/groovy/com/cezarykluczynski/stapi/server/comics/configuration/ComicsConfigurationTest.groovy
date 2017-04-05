package com.cezarykluczynski.stapi.server.comics.configuration

import com.cezarykluczynski.stapi.server.comics.endpoint.ComicsSoapEndpoint
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsBaseRestMapper
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsBaseSoapMapper
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsFullRestMapper
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsFullSoapMapper
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import spock.lang.Specification

import javax.xml.ws.Endpoint

class ComicsConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private ComicsConfiguration comicsConfiguration

	void setup() {
		endpointFactoryMock = Mock(EndpointFactory)
		comicsConfiguration = new ComicsConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "Comics SOAP endpoint is created"() {
		given:
		Endpoint endpoint = Mock(Endpoint)

		when:
		Endpoint endpointOutput = comicsConfiguration.comicsEndpoint()

		then:
		1 * endpointFactoryMock.createSoapEndpoint(ComicsSoapEndpoint, ComicsSoapEndpoint.ADDRESS) >> endpoint
		0 * _
		endpointOutput == endpoint
	}

	void "ComicsBaseSoapMapper is created"() {
		when:
		ComicsBaseSoapMapper comicsBaseSoapMapper = comicsConfiguration.comicsBaseSoapMapper()

		then:
		comicsBaseSoapMapper != null
	}

	void "ComicsFullSoapMapper is created"() {
		when:
		ComicsFullSoapMapper comicsFullSoapMapper = comicsConfiguration.comicsFullSoapMapper()

		then:
		comicsFullSoapMapper != null
	}

	void "ComicsBaseRestMapper is created"() {
		when:
		ComicsBaseRestMapper comicsBaseRestMapper = comicsConfiguration.comicsBaseRestMapper()

		then:
		comicsBaseRestMapper != null
	}

	void "ComicsFullRestMapper is created"() {
		when:
		ComicsFullRestMapper comicsFullRestMapper = comicsConfiguration.comicsFullRestMapper()

		then:
		comicsFullRestMapper != null
	}

}
