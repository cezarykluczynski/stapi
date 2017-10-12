package com.cezarykluczynski.stapi.server.conflict.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.ConflictBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.ConflictBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.ConflictFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.ConflictFullResponse
import com.cezarykluczynski.stapi.server.conflict.reader.ConflictSoapReader
import spock.lang.Specification

class ConflictSoapEndpointTest extends Specification {

	private ConflictSoapReader conflictSoapReaderMock

	private ConflictSoapEndpoint conflictSoapEndpoint

	void setup() {
		conflictSoapReaderMock = Mock()
		conflictSoapEndpoint = new ConflictSoapEndpoint(conflictSoapReaderMock)
	}

	void "passes base call to ConflictSoapReader"() {
		given:
		ConflictBaseRequest conflictBaseRequest = Mock()
		ConflictBaseResponse conflictBaseResponse = Mock()

		when:
		ConflictBaseResponse conflictResponseResult = conflictSoapEndpoint.getConflictBase(conflictBaseRequest)

		then:
		1 * conflictSoapReaderMock.readBase(conflictBaseRequest) >> conflictBaseResponse
		conflictResponseResult == conflictBaseResponse
	}

	void "passes full call to ConflictSoapReader"() {
		given:
		ConflictFullRequest conflictFullRequest = Mock()
		ConflictFullResponse conflictFullResponse = Mock()

		when:
		ConflictFullResponse conflictResponseResult = conflictSoapEndpoint.getConflictFull(conflictFullRequest)

		then:
		1 * conflictSoapReaderMock.readFull(conflictFullRequest) >> conflictFullResponse
		conflictResponseResult == conflictFullResponse
	}

}
