package com.cezarykluczynski.stapi.server.performer.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.Performer as RESTPerformer
import com.cezarykluczynski.stapi.model.performer.dto.PerformerRequestDTO
import com.cezarykluczynski.stapi.model.performer.entity.Performer as DBPerformer
import com.cezarykluczynski.stapi.server.performer.dto.PerformerRestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class PerformerRestMapperTest extends AbstractPerformerMapperTest {

	private PerformerRestMapper performerRestMapper

	def setup() {
		performerRestMapper = Mappers.getMapper(PerformerRestMapper)
	}

	def "maps PerformerRestBeanParams to PerformerRequestDTO"() {
		given:
		PerformerRestBeanParams performerRestBeanParams = new PerformerRestBeanParams(
				guid: GUID,
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
		PerformerRequestDTO performerRequestDTO = performerRestMapper.map performerRestBeanParams

		then:
		performerRequestDTO.guid == GUID
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

	def "maps DB entity to REST entity"() {
		given:
		DBPerformer dBPerformer = createPerformer()

		when:
		RESTPerformer restPerformer = performerRestMapper.map(Lists.newArrayList(dBPerformer))[0]

		then:
		restPerformer.name == NAME
		restPerformer.guid == GUID
		restPerformer.birthName == BIRTH_NAME
		restPerformer.gender == GENDER_ENUM_REST
		restPerformer.dateOfBirth == DATE_OF_BIRTH_FROM_DB
		restPerformer.dateOfDeath == DATE_OF_DEATH_FROM_DB
		restPerformer.placeOfBirth == PLACE_OF_BIRTH
		restPerformer.placeOfDeath == PLACE_OF_DEATH
		restPerformer.animalPerformer == ANIMAL_PERFORMER
		restPerformer.disPerformer == DIS_PERFORMER
		restPerformer.ds9Performer == DS9_PERFORMER
		restPerformer.entPerformer == ENT_PERFORMER
		restPerformer.filmPerformer == FILM_PERFORMER
		restPerformer.standInPerformer == STAND_IN_PERFORMER
		restPerformer.stuntPerformer == STUNT_PERFORMER
		restPerformer.tasPerformer == TAS_PERFORMER
		restPerformer.tngPerformer == TNG_PERFORMER
		restPerformer.tosPerformer == TOS_PERFORMER
		restPerformer.videoGamePerformer == VIDEO_GAME_PERFORMER
		restPerformer.voicePerformer == VOICE_PERFORMER
		restPerformer.voyPerformer == VOY_PERFORMER
		restPerformer.performanceHeaders.size() == dBPerformer.performances.size()
		restPerformer.stuntPerformanceHeaders.size() == dBPerformer.stuntPerformances.size()
		restPerformer.standInPerformanceHeaders.size() == dBPerformer.standInPerformances.size()
		restPerformer.characterHeaders.size() == dBPerformer.characters.size()
	}

}
