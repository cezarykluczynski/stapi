package com.cezarykluczynski.stapi.server.performer.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.PerformerRequest
import com.cezarykluczynski.stapi.client.v1.soap.PerformerResponse
import com.cezarykluczynski.stapi.server.performer.reader.PerformerSoapReader
import spock.lang.Specification

class PerformerSoapEndpointTest extends Specification {

	private PerformerSoapReader performerSoapReaderMock

	private PerformerSoapEndpoint performerSoapEndpoint

	def setup() {
		performerSoapReaderMock = Mock(PerformerSoapReader)
		performerSoapEndpoint = new PerformerSoapEndpoint(performerSoapReaderMock)
	}

	def "passes call to PerformerSoapReader"() {
		given:
		PerformerRequest performerRequest = Mock(PerformerRequest)
		PerformerResponse performerResponse = Mock(PerformerResponse)

		when:
		PerformerResponse performerResponseResult = performerSoapEndpoint.getPerformers(performerRequest)

		then:
		1 * performerSoapReaderMock.read(performerRequest) >> performerResponse
		performerResponseResult == performerResponse
	}

}
