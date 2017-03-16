package com.cezarykluczynski.stapi.server.performer.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.PerformerBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.PerformerBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.PerformerFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.PerformerFullResponse
import com.cezarykluczynski.stapi.server.performer.reader.PerformerSoapReader
import spock.lang.Specification

class PerformerSoapEndpointTest extends Specification {

	private PerformerSoapReader performerSoapReaderMock

	private PerformerSoapEndpoint performerSoapEndpoint

	void setup() {
		performerSoapReaderMock = Mock(PerformerSoapReader)
		performerSoapEndpoint = new PerformerSoapEndpoint(performerSoapReaderMock)
	}

	void "passes base call to PerformerSoapReader"() {
		given:
		PerformerBaseRequest performerBaseRequest = Mock(PerformerBaseRequest)
		PerformerBaseResponse performerBaseResponse = Mock(PerformerBaseResponse)

		when:
		PerformerBaseResponse performerResponseResult = performerSoapEndpoint.getPerformerBase(performerBaseRequest)

		then:
		1 * performerSoapReaderMock.readBase(performerBaseRequest) >> performerBaseResponse
		performerResponseResult == performerBaseResponse
	}

	void "passes full call to PerformerSoapReader"() {
		given:
		PerformerFullRequest performerFullRequest = Mock(PerformerFullRequest)
		PerformerFullResponse performerFullResponse = Mock(PerformerFullResponse)

		when:
		PerformerFullResponse performerResponseResult = performerSoapEndpoint.getPerformerFull(performerFullRequest)

		then:
		1 * performerSoapReaderMock.readFull(performerFullRequest) >> performerFullResponse
		performerResponseResult == performerFullResponse
	}

}
