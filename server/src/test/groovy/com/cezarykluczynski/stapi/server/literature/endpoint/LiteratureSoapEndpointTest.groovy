package com.cezarykluczynski.stapi.server.literature.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.LiteratureBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.LiteratureBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.LiteratureFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.LiteratureFullResponse
import com.cezarykluczynski.stapi.server.literature.reader.LiteratureSoapReader
import spock.lang.Specification

class LiteratureSoapEndpointTest extends Specification {

	private LiteratureSoapReader literatureSoapReaderMock

	private LiteratureSoapEndpoint literatureSoapEndpoint

	void setup() {
		literatureSoapReaderMock = Mock()
		literatureSoapEndpoint = new LiteratureSoapEndpoint(literatureSoapReaderMock)
	}

	void "passes base call to LiteratureSoapReader"() {
		given:
		LiteratureBaseRequest literatureRequest = Mock()
		LiteratureBaseResponse literatureResponse = Mock()

		when:
		LiteratureBaseResponse literatureResponseResult = literatureSoapEndpoint.getLiteratureBase(literatureRequest)

		then:
		1 * literatureSoapReaderMock.readBase(literatureRequest) >> literatureResponse
		literatureResponseResult == literatureResponse
	}

	void "passes full call to LiteratureSoapReader"() {
		given:
		LiteratureFullRequest literatureFullRequest = Mock()
		LiteratureFullResponse literatureFullResponse = Mock()

		when:
		LiteratureFullResponse literatureResponseResult = literatureSoapEndpoint.getLiteratureFull(literatureFullRequest)

		then:
		1 * literatureSoapReaderMock.readFull(literatureFullRequest) >> literatureFullResponse
		literatureResponseResult == literatureFullResponse
	}

}
