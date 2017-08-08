package com.cezarykluczynski.stapi.server.video_game.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.VideoGameBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.VideoGameBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.VideoGameFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.VideoGameFullResponse
import com.cezarykluczynski.stapi.server.video_game.reader.VideoGameSoapReader
import spock.lang.Specification

class VideoGameSoapEndpointTest extends Specification {

	private VideoGameSoapReader videoGameSoapReaderMock

	private VideoGameSoapEndpoint videoGameSoapEndpoint

	void setup() {
		videoGameSoapReaderMock = Mock()
		videoGameSoapEndpoint = new VideoGameSoapEndpoint(videoGameSoapReaderMock)
	}

	void "passes base call to VideoGameSoapReader"() {
		given:
		VideoGameBaseRequest videoGameBaseRequest = Mock()
		VideoGameBaseResponse videoGameBaseResponse = Mock()

		when:
		VideoGameBaseResponse videoGameResponseResult = videoGameSoapEndpoint.getVideoGameBase(videoGameBaseRequest)

		then:
		1 * videoGameSoapReaderMock.readBase(videoGameBaseRequest) >> videoGameBaseResponse
		videoGameResponseResult == videoGameBaseResponse
	}

	void "passes full call to VideoGameSoapReader"() {
		given:
		VideoGameFullRequest videoGameFullRequest = Mock()
		VideoGameFullResponse videoGameFullResponse = Mock()

		when:
		VideoGameFullResponse videoGameResponseResult = videoGameSoapEndpoint.getVideoGameFull(videoGameFullRequest)

		then:
		1 * videoGameSoapReaderMock.readFull(videoGameFullRequest) >> videoGameFullResponse
		videoGameResponseResult == videoGameFullResponse
	}

}
