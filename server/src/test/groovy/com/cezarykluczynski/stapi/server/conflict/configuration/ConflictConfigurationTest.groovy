package com.cezarykluczynski.stapi.server.conflict.configuration

import com.cezarykluczynski.stapi.server.conflict.endpoint.ConflictRestEndpoint
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.conflict.endpoint.ConflictSoapEndpoint
import com.cezarykluczynski.stapi.server.conflict.mapper.ConflictBaseRestMapper
import com.cezarykluczynski.stapi.server.conflict.mapper.ConflictBaseSoapMapper
import com.cezarykluczynski.stapi.server.conflict.mapper.ConflictFullRestMapper
import com.cezarykluczynski.stapi.server.conflict.mapper.ConflictFullSoapMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

import javax.xml.ws.Endpoint

class ConflictConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private ConflictConfiguration conflictConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		conflictConfiguration = new ConflictConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "Conflict SOAP endpoint is created"() {
		given:
		Endpoint endpoint = Mock()

		when:
		Endpoint endpointOutput = conflictConfiguration.conflictEndpoint()

		then:
		1 * endpointFactoryMock.createSoapEndpoint(ConflictSoapEndpoint, ConflictSoapEndpoint.ADDRESS) >> endpoint
		0 * _
		endpointOutput == endpoint
	}

	void "Conflict REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = conflictConfiguration.conflictServer()

		then:
		1 * endpointFactoryMock.createRestEndpoint(ConflictRestEndpoint, ConflictRestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "ConflictBaseSoapMapper is created"() {
		when:
		ConflictBaseSoapMapper conflictBaseSoapMapper = conflictConfiguration.conflictBaseSoapMapper()

		then:
		conflictBaseSoapMapper != null
	}

	void "ConflictFullSoapMapper is created"() {
		when:
		ConflictFullSoapMapper conflictFullSoapMapper = conflictConfiguration.conflictFullSoapMapper()

		then:
		conflictFullSoapMapper != null
	}

	void "ConflictBaseRestMapper is created"() {
		when:
		ConflictBaseRestMapper conflictBaseRestMapper = conflictConfiguration.conflictBaseRestMapper()

		then:
		conflictBaseRestMapper != null
	}

	void "ConflictFullRestMapper is created"() {
		when:
		ConflictFullRestMapper conflictFullRestMapper = conflictConfiguration.conflictFullRestMapper()

		then:
		conflictFullRestMapper != null
	}

}
