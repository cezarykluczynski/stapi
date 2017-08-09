package com.cezarykluczynski.stapi.server.soundtrack.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackFullResponse
import com.cezarykluczynski.stapi.server.soundtrack.reader.SoundtrackSoapReader
import spock.lang.Specification

class SoundtrackSoapEndpointTest extends Specification {

	private SoundtrackSoapReader soundtrackSoapReaderMock

	private SoundtrackSoapEndpoint soundtrackSoapEndpoint

	void setup() {
		soundtrackSoapReaderMock = Mock()
		soundtrackSoapEndpoint = new SoundtrackSoapEndpoint(soundtrackSoapReaderMock)
	}

	void "passes base call to SoundtrackSoapReader"() {
		given:
		SoundtrackBaseRequest soundtrackBaseRequest = Mock()
		SoundtrackBaseResponse soundtrackBaseResponse = Mock()

		when:
		SoundtrackBaseResponse soundtrackResponseResult = soundtrackSoapEndpoint.getSoundtrackBase(soundtrackBaseRequest)

		then:
		1 * soundtrackSoapReaderMock.readBase(soundtrackBaseRequest) >> soundtrackBaseResponse
		soundtrackResponseResult == soundtrackBaseResponse
	}

	void "passes full call to SoundtrackSoapReader"() {
		given:
		SoundtrackFullRequest soundtrackFullRequest = Mock()
		SoundtrackFullResponse soundtrackFullResponse = Mock()

		when:
		SoundtrackFullResponse soundtrackResponseResult = soundtrackSoapEndpoint.getSoundtrackFull(soundtrackFullRequest)

		then:
		1 * soundtrackSoapReaderMock.readFull(soundtrackFullRequest) >> soundtrackFullResponse
		soundtrackResponseResult == soundtrackFullResponse
	}

}
