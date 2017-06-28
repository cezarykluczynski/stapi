package com.cezarykluczynski.stapi.server.season.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.SeasonBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.SeasonBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.SeasonFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.SeasonFullResponse
import com.cezarykluczynski.stapi.server.season.reader.SeasonSoapReader
import spock.lang.Specification

class SeasonSoapEndpointTest extends Specification {

	private SeasonSoapReader seasonSoapReaderMock

	private SeasonSoapEndpoint seasonSoapEndpoint

	void setup() {
		seasonSoapReaderMock = Mock()
		seasonSoapEndpoint = new SeasonSoapEndpoint(seasonSoapReaderMock)
	}

	void "passes base call to SeasonSoapReader"() {
		given:
		SeasonBaseRequest seasonRequest = Mock()
		SeasonBaseResponse seasonResponse = Mock()

		when:
		SeasonBaseResponse seasonResponseResult = seasonSoapEndpoint.getSeasonBase(seasonRequest)

		then:
		1 * seasonSoapReaderMock.readBase(seasonRequest) >> seasonResponse
		seasonResponseResult == seasonResponse
	}

	void "passes full call to SeasonSoapReader"() {
		given:
		SeasonFullRequest seasonFullRequest = Mock()
		SeasonFullResponse seasonFullResponse = Mock()

		when:
		SeasonFullResponse seasonResponseResult = seasonSoapEndpoint.getSeasonFull(seasonFullRequest)

		then:
		1 * seasonSoapReaderMock.readFull(seasonFullRequest) >> seasonFullResponse
		seasonResponseResult == seasonFullResponse
	}

}
