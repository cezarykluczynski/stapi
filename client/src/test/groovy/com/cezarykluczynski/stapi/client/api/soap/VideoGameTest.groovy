package com.cezarykluczynski.stapi.client.api.soap

import com.cezarykluczynski.stapi.client.v1.soap.VideoGameBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.VideoGameBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.VideoGameFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.VideoGameFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.VideoGamePortType
import spock.lang.Specification

class VideoGameTest extends Specification {

	private VideoGamePortType videoGamePortTypeMock

	private ApiKeySupplier apiKeySupplierMock

	private VideoGame videoGame

	void setup() {
		videoGamePortTypeMock = Mock()
		apiKeySupplierMock = Mock()
		videoGame = new VideoGame(videoGamePortTypeMock, apiKeySupplierMock)
	}

	void "gets single entity"() {
		given:
		VideoGameBaseRequest videoGameBaseRequest = Mock()
		VideoGameBaseResponse videoGameBaseResponse = Mock()

		when:
		VideoGameBaseResponse videoGameBaseResponseOutput = videoGame.search(videoGameBaseRequest)

		then:
		1 * apiKeySupplierMock.supply(videoGameBaseRequest)
		1 * videoGamePortTypeMock.getVideoGameBase(videoGameBaseRequest) >> videoGameBaseResponse
		0 * _
		videoGameBaseResponse == videoGameBaseResponseOutput
	}

	void "searches entities"() {
		given:
		VideoGameFullRequest videoGameFullRequest = Mock()
		VideoGameFullResponse videoGameFullResponse = Mock()

		when:
		VideoGameFullResponse videoGameFullResponseOutput = videoGame.get(videoGameFullRequest)

		then:
		1 * apiKeySupplierMock.supply(videoGameFullRequest)
		1 * videoGamePortTypeMock.getVideoGameFull(videoGameFullRequest) >> videoGameFullResponse
		0 * _
		videoGameFullResponse == videoGameFullResponseOutput
	}

}
