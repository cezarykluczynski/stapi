package com.cezarykluczynski.stapi.server.title.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.TitleBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.TitleBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.TitleFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.TitleFullResponse
import com.cezarykluczynski.stapi.server.title.reader.TitleSoapReader
import spock.lang.Specification

class TitleSoapEndpointTest extends Specification {

	private TitleSoapReader titleSoapReaderMock

	private TitleSoapEndpoint titleSoapEndpoint

	void setup() {
		titleSoapReaderMock = Mock()
		titleSoapEndpoint = new TitleSoapEndpoint(titleSoapReaderMock)
	}

	void "passes base call to TitleSoapReader"() {
		given:
		TitleBaseRequest titleRequest = Mock()
		TitleBaseResponse titleResponse = Mock()

		when:
		TitleBaseResponse titleResponseResult = titleSoapEndpoint.getTitleBase(titleRequest)

		then:
		1 * titleSoapReaderMock.readBase(titleRequest) >> titleResponse
		titleResponseResult == titleResponse
	}

	void "passes full call to TitleSoapReader"() {
		given:
		TitleFullRequest titleFullRequest = Mock()
		TitleFullResponse titleFullResponse = Mock()

		when:
		TitleFullResponse titleResponseResult = titleSoapEndpoint.getTitleFull(titleFullRequest)

		then:
		1 * titleSoapReaderMock.readFull(titleFullRequest) >> titleFullResponse
		titleResponseResult == titleFullResponse
	}

}
