package com.cezarykluczynski.stapi.server.performer.mapper

import com.cezarykluczynski.stapi.client.v1.soap.PerformerBase
import com.cezarykluczynski.stapi.client.v1.soap.PerformerBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.PerformerFull
import com.cezarykluczynski.stapi.client.v1.soap.PerformerFullRequest
import com.cezarykluczynski.stapi.model.performer.dto.PerformerRequestDTO
import com.cezarykluczynski.stapi.model.performer.entity.Performer as DBPerformer
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class PerformerSoapMapperTest extends AbstractPerformerMapperTest {

	private PerformerSoapMapper performerSoapMapper

	void setup() {
		performerSoapMapper = Mappers.getMapper(PerformerSoapMapper)
	}

	void "maps SOAP PerformerRequest to PerformerRequestDTO"() {
		given:
		PerformerBaseRequest performerBaseRequest = new PerformerBaseRequest(
				guid: GUID,
				name: NAME,
				birthName: BIRTH_NAME,
				gender: GENDER_ENUM_SOAP,
				dateOfBirth: DATE_OF_BIRTH_SOAP,
				dateOfDeath: DATE_OF_DEATH_SOAP,
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
				voyPerformer: VOY_PERFORMER
		)

		when:
		PerformerRequestDTO performerRequestDTO = performerSoapMapper.mapBase performerBaseRequest

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

	void "maps SOAP PerformerFullRequest to PerformerBaseRequestDTO"() {
		given:
		PerformerFullRequest performerRequest = new PerformerFullRequest(guid: GUID)

		when:
		PerformerRequestDTO performerRequestDTO = performerSoapMapper.mapFull performerRequest

		then:
		performerRequestDTO.guid == GUID
	}

	void "maps DB entity to base SOAP entity"() {
		given:
		DBPerformer performer = createPerformer()

		when:
		PerformerBase performerBase = performerSoapMapper.mapBase(Lists.newArrayList(performer))[0]

		then:
		performerBase.name == NAME
		performerBase.guid == GUID
		performerBase.birthName == BIRTH_NAME
		performerBase.gender == GENDER_ENUM_SOAP
		performerBase.dateOfBirth == DATE_OF_BIRTH_FROM_SOAP
		performerBase.dateOfDeath == DATE_OF_DEATH_FROM_SOAP
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

	void "maps DB entity to full SOAP entity"() {
		given:
		DBPerformer performer = createPerformer()

		when:
		PerformerFull performerFull = performerSoapMapper.mapFull(performer)

		then:
		performerFull.name == NAME
		performerFull.guid == GUID
		performerFull.birthName == BIRTH_NAME
		performerFull.gender == GENDER_ENUM_SOAP
		performerFull.dateOfBirth == DATE_OF_BIRTH_FROM_SOAP
		performerFull.dateOfDeath == DATE_OF_DEATH_FROM_SOAP
		performerFull.placeOfBirth == PLACE_OF_BIRTH
		performerFull.placeOfDeath == PLACE_OF_DEATH
		performerFull.animalPerformer == ANIMAL_PERFORMER
		performerFull.disPerformer == DIS_PERFORMER
		performerFull.ds9Performer == DS9_PERFORMER
		performerFull.entPerformer == ENT_PERFORMER
		performerFull.filmPerformer == FILM_PERFORMER
		performerFull.standInPerformer == STAND_IN_PERFORMER
		performerFull.stuntPerformer == STUNT_PERFORMER
		performerFull.tasPerformer == TAS_PERFORMER
		performerFull.tngPerformer == TNG_PERFORMER
		performerFull.tosPerformer == TOS_PERFORMER
		performerFull.videoGamePerformer == VIDEO_GAME_PERFORMER
		performerFull.voicePerformer == VOICE_PERFORMER
		performerFull.voyPerformer == VOY_PERFORMER
		performerFull.episodesPerformanceHeaders.size() == performer.episodesPerformances.size()
		performerFull.episodesStuntPerformanceHeaders.size() == performer.episodesStuntPerformances.size()
		performerFull.episodesStandInPerformanceHeaders.size() == performer.episodesStandInPerformances.size()
		performerFull.moviesPerformanceHeaders.size() == performer.moviesPerformances.size()
		performerFull.moviesStuntPerformanceHeaders.size() == performer.moviesStuntPerformances.size()
		performerFull.moviesStandInPerformanceHeaders.size() == performer.moviesStandInPerformances.size()
		performerFull.characterHeaders.size() == performer.characters.size()
	}

}
