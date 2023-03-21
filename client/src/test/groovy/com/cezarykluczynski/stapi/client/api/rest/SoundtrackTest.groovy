package com.cezarykluczynski.stapi.client.api.rest

import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT
import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.api.dto.SoundtrackSearchCriteria
import com.cezarykluczynski.stapi.client.rest.api.SoundtrackApi
import com.cezarykluczynski.stapi.client.rest.model.SoundtrackBaseResponse
import com.cezarykluczynski.stapi.client.rest.model.SoundtrackFullResponse
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
		1 * soundtrackApiMock.v1Get(UID) >> soundtrackFullResponse
		0 * _
		soundtrackFullResponse == soundtrackFullResponseOutput
	}

	void "searches entities"() {
		given:
		SoundtrackBaseResponse soundtrackBaseResponse = Mock()

		when:
		SoundtrackBaseResponse soundtrackBaseResponseOutput = soundtrack.search(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, TITLE, RELEASE_DATE_FROM,
				RELEASE_DATE_TO, LENGTH_FROM, LENGTH_TO)

		then:
		1 * soundtrackApiMock.v1Search(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, TITLE, RELEASE_DATE_FROM, RELEASE_DATE_TO,
				LENGTH_FROM, LENGTH_TO) >> soundtrackBaseResponse
		0 * _
		soundtrackBaseResponse == soundtrackBaseResponseOutput
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
		soundtrackSearchCriteria.sort.addAll(SORT)

		when:
		SoundtrackBaseResponse soundtrackBaseResponseOutput = soundtrack.search(soundtrackSearchCriteria)

		then:
		1 * soundtrackApiMock.v1Search(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, TITLE, RELEASE_DATE_FROM, RELEASE_DATE_TO,
				LENGTH_FROM, LENGTH_TO) >> soundtrackBaseResponse
		0 * _
		soundtrackBaseResponse == soundtrackBaseResponseOutput
	}

}
