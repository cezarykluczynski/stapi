package com.cezarykluczynski.stapi.client.api.soap

import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackPortType
import spock.lang.Specification

class SoundtrackTest extends Specification {

	private SoundtrackPortType soundtrackPortTypeMock

	private ApiKeySupplier apiKeySupplierMock

	private Soundtrack soundtrack

	void setup() {
		soundtrackPortTypeMock = Mock()
		apiKeySupplierMock = Mock()
		soundtrack = new Soundtrack(soundtrackPortTypeMock, apiKeySupplierMock)
	}

	void "gets single entity"() {
		given:
		SoundtrackBaseRequest soundtrackBaseRequest = Mock()
		SoundtrackBaseResponse soundtrackBaseResponse = Mock()

		when:
		SoundtrackBaseResponse soundtrackBaseResponseOutput = soundtrack.search(soundtrackBaseRequest)

		then:
		1 * apiKeySupplierMock.supply(soundtrackBaseRequest)
		1 * soundtrackPortTypeMock.getSoundtrackBase(soundtrackBaseRequest) >> soundtrackBaseResponse
		0 * _
		soundtrackBaseResponse == soundtrackBaseResponseOutput
	}

	void "searches entities"() {
		given:
		SoundtrackFullRequest soundtrackFullRequest = Mock()
		SoundtrackFullResponse soundtrackFullResponse = Mock()

		when:
		SoundtrackFullResponse soundtrackFullResponseOutput = soundtrack.get(soundtrackFullRequest)

		then:
		1 * apiKeySupplierMock.supply(soundtrackFullRequest)
		1 * soundtrackPortTypeMock.getSoundtrackFull(soundtrackFullRequest) >> soundtrackFullResponse
		0 * _
		soundtrackFullResponse == soundtrackFullResponseOutput
	}

}
