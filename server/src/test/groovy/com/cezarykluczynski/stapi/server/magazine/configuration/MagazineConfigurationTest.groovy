package com.cezarykluczynski.stapi.server.magazine.configuration

import com.cezarykluczynski.stapi.server.magazine.endpoint.MagazineRestEndpoint
import com.cezarykluczynski.stapi.server.magazine.endpoint.MagazineSoapEndpoint
import com.cezarykluczynski.stapi.server.magazine.mapper.MagazineBaseRestMapper
import com.cezarykluczynski.stapi.server.magazine.mapper.MagazineBaseSoapMapper
import com.cezarykluczynski.stapi.server.magazine.mapper.MagazineFullRestMapper
import com.cezarykluczynski.stapi.server.magazine.mapper.MagazineFullSoapMapper
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

import javax.xml.ws.Endpoint

class MagazineConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private MagazineConfiguration magazineConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		magazineConfiguration = new MagazineConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "Magazine SOAP endpoint is created"() {
		given:
		Endpoint endpoint = Mock()

		when:
		Endpoint endpointOutput = magazineConfiguration.magazineEndpoint()

		then:
		1 * endpointFactoryMock.createSoapEndpoint(MagazineSoapEndpoint, MagazineSoapEndpoint.ADDRESS) >> endpoint
		0 * _
		endpointOutput == endpoint
	}

	void "Magazine REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = magazineConfiguration.magazineServer()

		then:
		1 * endpointFactoryMock.createRestEndpoint(MagazineRestEndpoint, MagazineRestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "MagazineBaseSoapMapper is created"() {
		when:
		MagazineBaseSoapMapper magazineBaseSoapMapper = magazineConfiguration.magazineBaseSoapMapper()

		then:
		magazineBaseSoapMapper != null
	}

	void "MagazineFullSoapMapper is created"() {
		when:
		MagazineFullSoapMapper magazineFullSoapMapper = magazineConfiguration.magazineFullSoapMapper()

		then:
		magazineFullSoapMapper != null
	}

	void "MagazineBaseRestMapper is created"() {
		when:
		MagazineBaseRestMapper magazineBaseRestMapper = magazineConfiguration.magazineBaseRestMapper()

		then:
		magazineBaseRestMapper != null
	}

	void "MagazineFullRestMapper is created"() {
		when:
		MagazineFullRestMapper magazineFullRestMapper = magazineConfiguration.magazineFullRestMapper()

		then:
		magazineFullRestMapper != null
	}

}
