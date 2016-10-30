package com.cezarykluczynski.stapi.server.performer.mapper

import com.cezarykluczynski.stapi.model.performer.entity.Performer as DBPerformer
import com.cezarykluczynski.stapi.client.soap.Performer as SOAPPerformer
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class PerformerSoapMapperTest extends AbstractPerformerMapperTest {

	private PerformerSoapMapper performerSoapMapper

	def setup() {
		performerSoapMapper = Mappers.getMapper(PerformerSoapMapper)
	}

	def "maps DB entity to SOAP entity"() {
		given:
		DBPerformer dBPerformer = new DBPerformer(
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
		SOAPPerformer soapPerformer = performerSoapMapper.map(Lists.newArrayList(dBPerformer))[0]

		then:
		soapPerformer.name == NAME
		soapPerformer.birthName == BIRTH_NAME
		soapPerformer.gender == GENDER_ENUM
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
	}

}
