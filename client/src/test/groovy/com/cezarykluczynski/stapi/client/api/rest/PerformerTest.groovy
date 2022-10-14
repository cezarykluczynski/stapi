package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.PerformerApi
import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerV2BaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerV2FullResponse
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
		1 * performerApiMock.v1RestPerformerGet(UID, API_KEY) >> performerFullResponse
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
		1 * performerApiMock.v1RestPerformerSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, API_KEY, NAME, BIRTH_NAME, GENDER_STRING, DATE_OF_BIRTH_FROM,
				DATE_OF_BIRTH_TO, PLACE_OF_BIRTH, DATE_OF_DEATH_FROM, DATE_OF_DEATH_TO, PLACE_OF_DEATH, ANIMAL_PERFORMER, DIS_PERFORMER,
				DS9_PERFORMER, ENT_PERFORMER, FILM_PERFORMER, STAND_IN_PERFORMER, STUNT_PERFORMER, TAS_PERFORMER, TNG_PERFORMER, TOS_PERFORMER,
				VIDEO_GAME_PERFORMER, VOICE_PERFORMER, VOY_PERFORMER) >> performerBaseResponse
		0 * _
		performerBaseResponse == performerBaseResponseOutput
	}

	void "gets single entity (V2)"() {
		given:
		PerformerV2FullResponse performerV2FullResponse = Mock()

		when:
		PerformerV2FullResponse performerV2FullResponseOutput = performer.getV2(UID)

		then:
		1 * performerApiMock.v2RestPerformerGet(UID, API_KEY) >> performerV2FullResponse
		0 * _
		performerV2FullResponse == performerV2FullResponseOutput
	}

	void "searches entities (v2)"() {
		given:
		PerformerV2BaseResponse performerV2BaseResponse = Mock()

		when:
		PerformerV2BaseResponse performerV2BaseResponseOutput = performer.searchV2(PAGE_NUMBER, PAGE_SIZE, SORT, NAME, BIRTH_NAME, GENDER_STRING,
				DATE_OF_BIRTH_FROM, DATE_OF_BIRTH_TO, PLACE_OF_BIRTH, DATE_OF_DEATH_FROM, DATE_OF_DEATH_TO, PLACE_OF_DEATH, ANIMAL_PERFORMER,
				AUDIOBOOK_PERFORMER, CUT_PERFORMER, DIS_PERFORMER, DS9_PERFORMER, ENT_PERFORMER, FILM_PERFORMER, LD_PERFORMER, PIC_PERFORMER,
				PRO_PERFORMER, PUPPETEER, SNW_PERFORMER, STAND_IN_PERFORMER, ST_PERFORMER, STUNT_PERFORMER, TAS_PERFORMER, TNG_PERFORMER,
				TOS_PERFORMER, VIDEO_GAME_PERFORMER, VOICE_PERFORMER, VOY_PERFORMER)

		then:
		1 * performerApiMock.v2RestPerformerSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, API_KEY, NAME, BIRTH_NAME, GENDER_STRING, DATE_OF_BIRTH_FROM,
				DATE_OF_BIRTH_TO, PLACE_OF_BIRTH, DATE_OF_DEATH_FROM, DATE_OF_DEATH_TO, PLACE_OF_DEATH, ANIMAL_PERFORMER, AUDIOBOOK_PERFORMER,
				CUT_PERFORMER, DIS_PERFORMER, DS9_PERFORMER, ENT_PERFORMER, FILM_PERFORMER, LD_PERFORMER, PIC_PERFORMER, PRO_PERFORMER, PUPPETEER,
				SNW_PERFORMER, STAND_IN_PERFORMER, ST_PERFORMER, STUNT_PERFORMER, TAS_PERFORMER, TNG_PERFORMER, TOS_PERFORMER, VIDEO_GAME_PERFORMER,
				VOICE_PERFORMER, VOY_PERFORMER) >> performerV2BaseResponse
		0 * _
		performerV2BaseResponse == performerV2BaseResponseOutput
	}

}
