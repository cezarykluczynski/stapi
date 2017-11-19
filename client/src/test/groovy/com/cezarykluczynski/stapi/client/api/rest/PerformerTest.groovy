package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.PerformerApi
import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerFullResponse
import com.cezarykluczynski.stapi.util.AbstractRealWorldPersonTest

class PerformerTest extends AbstractRealWorldPersonTest {

	private PerformerApi performerApiMock

	private Performer performer

	void setup() {
		performerApiMock = Mock()
		performer = new Performer(performerApiMock, API_KEY)
	}

	void "gets single entity"() {
		given:
		PerformerFullResponse performerFullResponse = Mock()

		when:
		PerformerFullResponse performerFullResponseOutput = performer.get(UID)

		then:
		1 * performerApiMock.performerGet(UID, API_KEY) >> performerFullResponse
		0 * _
		performerFullResponse == performerFullResponseOutput
	}

	void "searches entities"() {
		given:
		PerformerBaseResponse performerBaseResponse = Mock()

		when:
		PerformerBaseResponse performerBaseResponseOutput = performer.search(PAGE_NUMBER, PAGE_SIZE, SORT, NAME, BIRTH_NAME, GENDER_STRING,
				DATE_OF_BIRTH_FROM, DATE_OF_BIRTH_TO, PLACE_OF_BIRTH, DATE_OF_DEATH_FROM, DATE_OF_DEATH_TO, PLACE_OF_DEATH, ANIMAL_PERFORMER,
				DIS_PERFORMER, DS9_PERFORMER, ENT_PERFORMER, FILM_PERFORMER, STAND_IN_PERFORMER, STUNT_PERFORMER, TAS_PERFORMER, TNG_PERFORMER,
				TOS_PERFORMER, VIDEO_GAME_PERFORMER, VOICE_PERFORMER, VOY_PERFORMER)

		then:
		1 * performerApiMock.performerSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, API_KEY, NAME, BIRTH_NAME, GENDER_STRING, DATE_OF_BIRTH_FROM,
				DATE_OF_BIRTH_TO, PLACE_OF_BIRTH, DATE_OF_DEATH_FROM, DATE_OF_DEATH_TO, PLACE_OF_DEATH, ANIMAL_PERFORMER, DIS_PERFORMER,
				DS9_PERFORMER, ENT_PERFORMER, FILM_PERFORMER, STAND_IN_PERFORMER, STUNT_PERFORMER, TAS_PERFORMER, TNG_PERFORMER, TOS_PERFORMER,
				VIDEO_GAME_PERFORMER, VOICE_PERFORMER, VOY_PERFORMER) >> performerBaseResponse
		0 * _
		performerBaseResponse == performerBaseResponseOutput
	}

}
