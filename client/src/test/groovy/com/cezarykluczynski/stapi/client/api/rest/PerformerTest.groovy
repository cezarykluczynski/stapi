package com.cezarykluczynski.stapi.client.api.rest

import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT
import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.api.dto.PerformerV2SearchCriteria
import com.cezarykluczynski.stapi.client.rest.api.PerformerApi
import com.cezarykluczynski.stapi.client.rest.model.Gender
import com.cezarykluczynski.stapi.client.rest.model.PerformerBaseResponse
import com.cezarykluczynski.stapi.client.rest.model.PerformerFullResponse
import com.cezarykluczynski.stapi.client.rest.model.PerformerV2BaseResponse
import com.cezarykluczynski.stapi.client.rest.model.PerformerV2FullResponse
import com.cezarykluczynski.stapi.util.AbstractRealWorldPersonTest

class PerformerTest extends AbstractRealWorldPersonTest {

	private PerformerApi performerApiMock

	private Performer performer

	void setup() {
		performerApiMock = Mock()
		performer = new Performer(performerApiMock)
	}

	void "gets single entity"() {
		given:
		PerformerFullResponse performerFullResponse = Mock()

		when:
		PerformerFullResponse performerFullResponseOutput = performer.get(UID)

		then:
		1 * performerApiMock.v1RestPerformerGet(UID) >> performerFullResponse
		0 * _
		performerFullResponse == performerFullResponseOutput
	}

	void "gets single entity (V2)"() {
		given:
		PerformerV2FullResponse performerV2FullResponse = Mock()

		when:
		PerformerV2FullResponse performerV2FullResponseOutput = performer.getV2(UID)

		then:
		1 * performerApiMock.v2RestPerformerGet(UID) >> performerV2FullResponse
		0 * _
		performerV2FullResponse == performerV2FullResponseOutput
	}

	void "searches entities"() {
		given:
		PerformerBaseResponse performerBaseResponse = Mock()

		when:
		PerformerBaseResponse performerBaseResponseOutput = performer.search(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, BIRTH_NAME, GENDER,
				DATE_OF_BIRTH_FROM, DATE_OF_BIRTH_TO, PLACE_OF_BIRTH, DATE_OF_DEATH_FROM, DATE_OF_DEATH_TO, PLACE_OF_DEATH, ANIMAL_PERFORMER,
				DIS_PERFORMER, DS9_PERFORMER, ENT_PERFORMER, FILM_PERFORMER, STAND_IN_PERFORMER, STUNT_PERFORMER, TAS_PERFORMER, TNG_PERFORMER,
				TOS_PERFORMER, VIDEO_GAME_PERFORMER, VOICE_PERFORMER, VOY_PERFORMER)

		then:
		1 * performerApiMock.v1RestPerformerSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, BIRTH_NAME, GENDER, DATE_OF_BIRTH_FROM,
				DATE_OF_BIRTH_TO, PLACE_OF_BIRTH, DATE_OF_DEATH_FROM, DATE_OF_DEATH_TO, PLACE_OF_DEATH, ANIMAL_PERFORMER, DIS_PERFORMER,
				DS9_PERFORMER, ENT_PERFORMER, FILM_PERFORMER, STAND_IN_PERFORMER, STUNT_PERFORMER, TAS_PERFORMER, TNG_PERFORMER, TOS_PERFORMER,
				VIDEO_GAME_PERFORMER, VOICE_PERFORMER, VOY_PERFORMER) >> performerBaseResponse
		0 * _
		performerBaseResponse == performerBaseResponseOutput
	}

	void "searches entities (v2)"() {
		given:
		PerformerV2BaseResponse performerV2BaseResponse = Mock()

		when:
		PerformerV2BaseResponse performerV2BaseResponseOutput = performer.searchV2(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, BIRTH_NAME, GENDER,
				DATE_OF_BIRTH_FROM, DATE_OF_BIRTH_TO, PLACE_OF_BIRTH, DATE_OF_DEATH_FROM, DATE_OF_DEATH_TO, PLACE_OF_DEATH, ANIMAL_PERFORMER,
				AUDIOBOOK_PERFORMER, CUT_PERFORMER, DIS_PERFORMER, DS9_PERFORMER, ENT_PERFORMER, FILM_PERFORMER, LD_PERFORMER, PIC_PERFORMER,
				PRO_PERFORMER, PUPPETEER, SNW_PERFORMER, STAND_IN_PERFORMER, ST_PERFORMER, STUNT_PERFORMER, TAS_PERFORMER, TNG_PERFORMER,
				TOS_PERFORMER, VIDEO_GAME_PERFORMER, VOICE_PERFORMER, VOY_PERFORMER)

		then:
		1 * performerApiMock.v2RestPerformerSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, BIRTH_NAME, GENDER, DATE_OF_BIRTH_FROM,
				DATE_OF_BIRTH_TO, PLACE_OF_BIRTH, DATE_OF_DEATH_FROM, DATE_OF_DEATH_TO, PLACE_OF_DEATH, ANIMAL_PERFORMER, AUDIOBOOK_PERFORMER,
				CUT_PERFORMER, DIS_PERFORMER, DS9_PERFORMER, ENT_PERFORMER, FILM_PERFORMER, LD_PERFORMER, PIC_PERFORMER, PRO_PERFORMER, PUPPETEER,
				SNW_PERFORMER, STAND_IN_PERFORMER, ST_PERFORMER, STUNT_PERFORMER, TAS_PERFORMER, TNG_PERFORMER, TOS_PERFORMER, VIDEO_GAME_PERFORMER,
				VOICE_PERFORMER, VOY_PERFORMER) >> performerV2BaseResponse
		0 * _
		performerV2BaseResponse == performerV2BaseResponseOutput
	}

	void "searches entities with criteria (v2)"() {
		given:
		PerformerV2BaseResponse performerV2BaseResponse = Mock()
		PerformerV2SearchCriteria performerV2SearchCriteria = new PerformerV2SearchCriteria(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				name: NAME,
				birthName: BIRTH_NAME,
				gender: Gender.valueOf(GENDER),
				dateOfBirthFrom: DATE_OF_BIRTH_FROM,
				dateOfBirthTo: DATE_OF_BIRTH_TO,
				dateOfDeathFrom: DATE_OF_DEATH_FROM,
				dateOfDeathTo: DATE_OF_DEATH_TO,
				placeOfBirth: PLACE_OF_BIRTH,
				placeOfDeath: PLACE_OF_DEATH,
				animalPerformer: ANIMAL_PERFORMER,
				audiobookPerformer: AUDIOBOOK_PERFORMER,
				cutPerformer: CUT_PERFORMER,
				disPerformer: DIS_PERFORMER,
				ds9Performer: DS9_PERFORMER,
				entPerformer: ENT_PERFORMER,
				filmPerformer: FILM_PERFORMER,
				ldPerformer: LD_PERFORMER,
				picPerformer: PIC_PERFORMER,
				proPerformer: PRO_PERFORMER,
				puppeteer: PUPPETEER,
				snwPerformer: SNW_PERFORMER,
				standInPerformer: STAND_IN_PERFORMER,
				stPerformer: ST_PERFORMER,
				stuntPerformer: STUNT_PERFORMER,
				tasPerformer: TAS_PERFORMER,
				tngPerformer: TNG_PERFORMER,
				tosPerformer: TOS_PERFORMER,
				videoGamePerformer: VIDEO_GAME_PERFORMER,
				voicePerformer: VOICE_PERFORMER,
				voyPerformer: VOY_PERFORMER)
		performerV2SearchCriteria.sort.addAll(SORT)

		when:
		PerformerV2BaseResponse performerV2BaseResponseOutput = performer.searchV2(performerV2SearchCriteria)

		then:
		1 * performerApiMock.v2RestPerformerSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, BIRTH_NAME, GENDER, DATE_OF_BIRTH_FROM,
				DATE_OF_BIRTH_TO, PLACE_OF_BIRTH, DATE_OF_DEATH_FROM, DATE_OF_DEATH_TO, PLACE_OF_DEATH, ANIMAL_PERFORMER, AUDIOBOOK_PERFORMER,
				CUT_PERFORMER, DIS_PERFORMER, DS9_PERFORMER, ENT_PERFORMER, FILM_PERFORMER, LD_PERFORMER, PIC_PERFORMER, PRO_PERFORMER, PUPPETEER,
				SNW_PERFORMER, STAND_IN_PERFORMER, ST_PERFORMER, STUNT_PERFORMER, TAS_PERFORMER, TNG_PERFORMER, TOS_PERFORMER, VIDEO_GAME_PERFORMER,
				VOICE_PERFORMER, VOY_PERFORMER) >> performerV2BaseResponse
		0 * _
		performerV2BaseResponse == performerV2BaseResponseOutput
	}

}
