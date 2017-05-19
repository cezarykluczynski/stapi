package com.cezarykluczynski.stapi.server.performer.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerBase
import com.cezarykluczynski.stapi.model.performer.dto.PerformerRequestDTO
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.server.performer.dto.PerformerRestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class PerformerBaseRestMapperTest extends AbstractPerformerMapperTest {

	private PerformerBaseRestMapper performerRestMapper

	void setup() {
		performerRestMapper = Mappers.getMapper(PerformerBaseRestMapper)
	}

	void "maps PerformerRestBeanParams to PerformerRequestDTO"() {
		given:
		PerformerRestBeanParams performerRestBeanParams = new PerformerRestBeanParams(
				uid: UID,
				name: NAME,
				birthName: BIRTH_NAME,
				gender: GENDER,
				dateOfBirthFrom: DATE_OF_BIRTH_FROM_DB,
				dateOfBirthTo: DATE_OF_BIRTH_TO_DB,
				dateOfDeathFrom: DATE_OF_DEATH_FROM_DB,
				dateOfDeathTo: DATE_OF_DEATH_TO_DB,
				placeOfBirth: PLACE_OF_BIRTH,
				placeOfDeath: PLACE_OF_DEATH,
				animalPerformer: ANIMAL_PERFORMER,
				disPerformer: DIS_PERFORMER,
				ds9Performer: DS9_PERFORMER,
				entPerformer: ENT_PERFORMER,
				filmPerformer: FILM_PERFORMER,
				standInPerformer: STAND_IN_PERFORMER,
				stuntPerformer: STUNT_PERFORMER,
				tasPerformer: TAS_PERFORMER,
				tngPerformer: TNG_PERFORMER,
				tosPerformer: TOS_PERFORMER,
				videoGamePerformer: VIDEO_GAME_PERFORMER,
				voicePerformer: VOICE_PERFORMER,
				voyPerformer: VOY_PERFORMER)

		when:
		PerformerRequestDTO performerRequestDTO = performerRestMapper.mapBase performerRestBeanParams

		then:
		performerRequestDTO.uid == UID
		performerRequestDTO.name == NAME
		performerRequestDTO.birthName == BIRTH_NAME
		performerRequestDTO.gender == GENDER
		performerRequestDTO.dateOfBirthFrom == DATE_OF_BIRTH_FROM_DB
		performerRequestDTO.dateOfBirthTo == DATE_OF_BIRTH_TO_DB
		performerRequestDTO.dateOfDeathFrom == DATE_OF_DEATH_FROM_DB
		performerRequestDTO.dateOfDeathTo == DATE_OF_DEATH_TO_DB
		performerRequestDTO.placeOfBirth == PLACE_OF_BIRTH
		performerRequestDTO.placeOfDeath == PLACE_OF_DEATH
		performerRequestDTO.animalPerformer == ANIMAL_PERFORMER
		performerRequestDTO.disPerformer == DIS_PERFORMER
		performerRequestDTO.ds9Performer == DS9_PERFORMER
		performerRequestDTO.entPerformer == ENT_PERFORMER
		performerRequestDTO.filmPerformer == FILM_PERFORMER
		performerRequestDTO.standInPerformer == STAND_IN_PERFORMER
		performerRequestDTO.stuntPerformer == STUNT_PERFORMER
		performerRequestDTO.tasPerformer == TAS_PERFORMER
		performerRequestDTO.tngPerformer == TNG_PERFORMER
		performerRequestDTO.tosPerformer == TOS_PERFORMER
		performerRequestDTO.videoGamePerformer == VIDEO_GAME_PERFORMER
		performerRequestDTO.voicePerformer == VOICE_PERFORMER
		performerRequestDTO.voyPerformer == VOY_PERFORMER
	}

	void "maps DB entity to base REST entity"() {
		given:
		Performer performer = createPerformer()

		when:
		PerformerBase performerBase = performerRestMapper.mapBase(Lists.newArrayList(performer))[0]

		then:
		performerBase.name == NAME
		performerBase.uid == UID
		performerBase.birthName == BIRTH_NAME
		performerBase.gender == GENDER_ENUM_REST
		performerBase.dateOfBirth == DATE_OF_BIRTH_FROM_DB
		performerBase.dateOfDeath == DATE_OF_DEATH_FROM_DB
		performerBase.placeOfBirth == PLACE_OF_BIRTH
		performerBase.placeOfDeath == PLACE_OF_DEATH
		performerBase.animalPerformer == ANIMAL_PERFORMER
		performerBase.disPerformer == DIS_PERFORMER
		performerBase.ds9Performer == DS9_PERFORMER
		performerBase.entPerformer == ENT_PERFORMER
		performerBase.filmPerformer == FILM_PERFORMER
		performerBase.standInPerformer == STAND_IN_PERFORMER
		performerBase.stuntPerformer == STUNT_PERFORMER
		performerBase.tasPerformer == TAS_PERFORMER
		performerBase.tngPerformer == TNG_PERFORMER
		performerBase.tosPerformer == TOS_PERFORMER
		performerBase.videoGamePerformer == VIDEO_GAME_PERFORMER
		performerBase.voicePerformer == VOICE_PERFORMER
		performerBase.voyPerformer == VOY_PERFORMER
	}

}
