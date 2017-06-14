package com.cezarykluczynski.stapi.server.comic_strip.configuration

import com.cezarykluczynski.stapi.server.comic_strip.endpoint.ComicStripRestEndpoint
import com.cezarykluczynski.stapi.server.comic_strip.endpoint.ComicStripSoapEndpoint
import com.cezarykluczynski.stapi.server.comic_strip.mapper.ComicStripBaseRestMapper
import com.cezarykluczynski.stapi.server.comic_strip.mapper.ComicStripBaseSoapMapper
import com.cezarykluczynski.stapi.server.comic_strip.mapper.ComicStripFullRestMapper
import com.cezarykluczynski.stapi.server.comic_strip.mapper.ComicStripFullSoapMapper
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

import javax.xml.ws.Endpoint

class ComicStripConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private ComicStripConfiguration comicStripConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		comicStripConfiguration = new ComicStripConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "ComicStrip SOAP endpoint is created"() {
		given:
		Endpoint endpoint = Mock()

		when:
		Endpoint endpointOutput = comicStripConfiguration.comicStripEndpoint()

		then:
		1 * endpointFactoryMock.createSoapEndpoint(ComicStripSoapEndpoint, ComicStripSoapEndpoint.ADDRESS) >> endpoint
		0 * _
		endpointOutput == endpoint
	}

	void "ComicStrip REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = comicStripConfiguration.comicStripServer()

		then:
		1 * endpointFactoryMock.createRestEndpoint(ComicStripRestEndpoint, ComicStripRestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "ComicStripBaseSoapMapper is created"() {
		when:
		ComicStripBaseSoapMapper comicStripBaseSoapMapper = comicStripConfiguration.comicStripBaseSoapMapper()

		then:
		comicStripBaseSoapMapper != null
	}

	void "ComicStripFullSoapMapper is created"() {
		when:
		ComicStripFullSoapMapper comicStripFullSoapMapper = comicStripConfiguration.comicStripFullSoapMapper()

		then:
		comicStripFullSoapMapper != null
	}

	void "ComicStripBaseRestMapper is created"() {
		when:
		ComicStripBaseRestMapper comicStripBaseRestMapper = comicStripConfiguration.comicStripBaseRestMapper()

		then:
		comicStripBaseRestMapper != null
	}

	void "ComicStripFullRestMapper is created"() {
		when:
		ComicStripFullRestMapper comicStripFullRestMapper = comicStripConfiguration.comicStripFullRestMapper()

		then:
		comicStripFullRestMapper != null
	}

}
