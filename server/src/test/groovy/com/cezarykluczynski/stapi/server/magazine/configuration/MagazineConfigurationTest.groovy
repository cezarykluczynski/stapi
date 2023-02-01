package com.cezarykluczynski.stapi.server.magazine.configuration

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.magazine.endpoint.MagazineRestEndpoint
import com.cezarykluczynski.stapi.server.magazine.mapper.MagazineBaseRestMapper
import com.cezarykluczynski.stapi.server.magazine.mapper.MagazineFullRestMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

class MagazineConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private MagazineConfiguration magazineConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		magazineConfiguration = new MagazineConfiguration(endpointFactory: endpointFactoryMock)
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
