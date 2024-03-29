package com.cezarykluczynski.stapi.client.rest.facade

import static AbstractFacadeTest.SORT
import static AbstractFacadeTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.rest.api.SoundtrackApi
import com.cezarykluczynski.stapi.client.rest.model.SoundtrackBaseResponse
import com.cezarykluczynski.stapi.client.rest.model.SoundtrackFullResponse
import com.cezarykluczynski.stapi.client.rest.model.SoundtrackSearchCriteria
import com.cezarykluczynski.stapi.util.AbstractSoundtrackTest

class SoundtrackTest extends AbstractSoundtrackTest {

	private SoundtrackApi soundtrackApiMock

	private Soundtrack soundtrack

	void setup() {
		soundtrackApiMock = Mock()
		soundtrack = new Soundtrack(soundtrackApiMock)
	}

	void "gets single entity"() {
		given:
		SoundtrackFullResponse soundtrackFullResponse = Mock()

		when:
		SoundtrackFullResponse soundtrackFullResponseOutput = soundtrack.get(UID)

		then:
		1 * soundtrackApiMock.v1GetSoundtrack(UID) >> soundtrackFullResponse
		0 * _
		soundtrackFullResponse == soundtrackFullResponseOutput
	}

	void "searches entities with criteria"() {
		given:
		SoundtrackBaseResponse soundtrackBaseResponse = Mock()
		SoundtrackSearchCriteria soundtrackSearchCriteria = new SoundtrackSearchCriteria(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				title: TITLE,
				releaseDateFrom: RELEASE_DATE_FROM,
				releaseDateTo: RELEASE_DATE_TO,
				lengthFrom: LENGTH_FROM,
				lengthTo: LENGTH_TO)
		soundtrackSearchCriteria.sort = SORT

		when:
		SoundtrackBaseResponse soundtrackBaseResponseOutput = soundtrack.search(soundtrackSearchCriteria)

		then:
		1 * soundtrackApiMock.v1SearchSoundtracks(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, TITLE, RELEASE_DATE_FROM, RELEASE_DATE_TO,
				LENGTH_FROM, LENGTH_TO) >> soundtrackBaseResponse
		0 * _
		soundtrackBaseResponse == soundtrackBaseResponseOutput
	}

}
