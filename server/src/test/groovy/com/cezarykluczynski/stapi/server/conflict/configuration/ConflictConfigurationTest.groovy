package com.cezarykluczynski.stapi.server.conflict.configuration

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.conflict.endpoint.ConflictRestEndpoint
import com.cezarykluczynski.stapi.server.conflict.endpoint.ConflictV2RestEndpoint
import com.cezarykluczynski.stapi.server.conflict.mapper.ConflictBaseRestMapper
import com.cezarykluczynski.stapi.server.conflict.mapper.ConflictFullRestMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

class ConflictConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private ConflictConfiguration conflictConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		conflictConfiguration = new ConflictConfiguration(endpointFactory: endpointFactoryMock)
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

	void "Conflict V2 REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = conflictConfiguration.conflictV2Server()

		then:
		1 * endpointFactoryMock.createRestEndpoint(ConflictV2RestEndpoint, ConflictV2RestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
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
