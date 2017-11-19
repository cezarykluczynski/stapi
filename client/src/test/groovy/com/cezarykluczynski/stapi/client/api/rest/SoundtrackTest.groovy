package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.SoundtrackApi
import com.cezarykluczynski.stapi.client.v1.rest.model.SoundtrackBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.SoundtrackFullResponse
import com.cezarykluczynski.stapi.util.AbstractSoundtrackTest

class SoundtrackTest extends AbstractSoundtrackTest {

	private SoundtrackApi soundtrackApiMock

	private Soundtrack soundtrack

	void setup() {
		soundtrackApiMock = Mock()
		soundtrack = new Soundtrack(soundtrackApiMock, API_KEY)
	}

	void "gets single entity"() {
		given:
		SoundtrackFullResponse soundtrackFullResponse = Mock()

		when:
		SoundtrackFullResponse soundtrackFullResponseOutput = soundtrack.get(UID)

		then:
		1 * soundtrackApiMock.soundtrackGet(UID, API_KEY) >> soundtrackFullResponse
		0 * _
		soundtrackFullResponse == soundtrackFullResponseOutput
	}

	void "searches entities"() {
		given:
		SoundtrackBaseResponse soundtrackBaseResponse = Mock()

		when:
		SoundtrackBaseResponse soundtrackBaseResponseOutput = soundtrack.search(PAGE_NUMBER, PAGE_SIZE, SORT, TITLE, RELEASE_DATE_FROM,
				RELEASE_DATE_TO, LENGTH_FROM, LENGTH_TO)

		then:
		1 * soundtrackApiMock.soundtrackSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, API_KEY, TITLE, RELEASE_DATE_FROM, RELEASE_DATE_TO, LENGTH_FROM,
				LENGTH_TO) >> soundtrackBaseResponse
		0 * _
		soundtrackBaseResponse == soundtrackBaseResponseOutput
	}

}
