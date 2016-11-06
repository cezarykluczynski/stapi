package com.cezarykluczynski.stapi.server.performer.mapper

import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class PerformerRestMapperTest extends AbstractPerformerMapperTest {

	private PerformerRestMapper performerRestMapper

	def setup() {
		performerRestMapper = Mappers.getMapper(PerformerRestMapper)
	}

	def "maps DB entity to REST entity"() {
		given:
		Performer dBPerformer = new Performer(
				name: NAME,
				birthName: BIRTH_NAME,
				gender: GENDER,
				dateOfBirth: DATE_OF_BIRTH_FROM_DB,
				dateOfDeath: DATE_OF_DEATH_FROM_DB,
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
		com.cezarykluczynski.stapi.client.v1.rest.model.Performer restPerformer = performerRestMapper.map(Lists.newArrayList(dBPerformer))[0]

		then:
		restPerformer.name == NAME
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
	}

}
