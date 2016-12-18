package com.cezarykluczynski.stapi.server.performer.mapper

import com.cezarykluczynski.stapi.client.v1.soap.Performer as SOAPPerformer
import com.cezarykluczynski.stapi.client.v1.soap.PerformerRequest
import com.cezarykluczynski.stapi.model.performer.dto.PerformerRequestDTO
import com.cezarykluczynski.stapi.model.performer.entity.Performer as DBPerformer
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class PerformerSoapMapperTest extends AbstractPerformerMapperTest {

	private PerformerSoapMapper performerSoapMapper

	def setup() {
		performerSoapMapper = Mappers.getMapper(PerformerSoapMapper)
	}

	def "maps SOAP PerformerRequest to PerformerRequestDTO"() {
		given:
		PerformerRequest performerRequest = new PerformerRequest(
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
		PerformerRequestDTO performerRequestDTO = performerSoapMapper.map performerRequest

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

	def "maps DB entity to SOAP entity"() {
		given:
		DBPerformer dBPerformer = createPerformer()

		when:
		SOAPPerformer soapPerformer = performerSoapMapper.map(Lists.newArrayList(dBPerformer))[0]

		then:
		soapPerformer.name == NAME
		soapPerformer.guid == GUID
		soapPerformer.birthName == BIRTH_NAME
		soapPerformer.gender == GENDER_ENUM_SOAP
		soapPerformer.dateOfBirth == DATE_OF_BIRTH_FROM_SOAP
		soapPerformer.dateOfDeath == DATE_OF_DEATH_FROM_SOAP
		soapPerformer.placeOfBirth == PLACE_OF_BIRTH
		soapPerformer.placeOfDeath == PLACE_OF_DEATH
		soapPerformer.animalPerformer == ANIMAL_PERFORMER
		soapPerformer.disPerformer == DIS_PERFORMER
		soapPerformer.ds9Performer == DS9_PERFORMER
		soapPerformer.entPerformer == ENT_PERFORMER
		soapPerformer.filmPerformer == FILM_PERFORMER
		soapPerformer.standInPerformer == STAND_IN_PERFORMER
		soapPerformer.stuntPerformer == STUNT_PERFORMER
		soapPerformer.tasPerformer == TAS_PERFORMER
		soapPerformer.tngPerformer == TNG_PERFORMER
		soapPerformer.tosPerformer == TOS_PERFORMER
		soapPerformer.videoGamePerformer == VIDEO_GAME_PERFORMER
		soapPerformer.voicePerformer == VOICE_PERFORMER
		soapPerformer.voyPerformer == VOY_PERFORMER
		soapPerformer.performanceHeaders.size() == dBPerformer.performances.size()
		soapPerformer.stuntPerformanceHeaders.size() == dBPerformer.stuntPerformances.size()
		soapPerformer.standInPerformanceHeaders.size() == dBPerformer.standInPerformances.size()
		soapPerformer.characterHeaders.size() == dBPerformer.characters.size()
	}

}
