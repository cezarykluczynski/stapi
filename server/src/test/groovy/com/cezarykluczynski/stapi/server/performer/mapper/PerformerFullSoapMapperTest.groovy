package com.cezarykluczynski.stapi.server.performer.mapper

import com.cezarykluczynski.stapi.client.v1.soap.PerformerFull
import com.cezarykluczynski.stapi.client.v1.soap.PerformerFullRequest
import com.cezarykluczynski.stapi.model.performer.dto.PerformerRequestDTO
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import org.mapstruct.factory.Mappers

class PerformerFullSoapMapperTest extends AbstractPerformerMapperTest {

	private PerformerFullSoapMapper performerFullSoapMapper

	void setup() {
		performerFullSoapMapper = Mappers.getMapper(PerformerFullSoapMapper)
	}

	void "maps SOAP PerformerFullRequest to PerformerBaseRequestDTO"() {
		given:
		PerformerFullRequest performerFullRequest = new PerformerFullRequest(uid: UID)

		when:
		PerformerRequestDTO performerRequestDTO = performerFullSoapMapper.mapFull performerFullRequest

		then:
		performerRequestDTO.uid == UID
	}

	void "maps DB entity to full SOAP entity"() {
		given:
		Performer performer = createPerformer()

		when:
		PerformerFull performerFull = performerFullSoapMapper.mapFull(performer)

		then:
		performerFull.name == NAME
		performerFull.uid == UID
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
		performerFull.episodesPerformances.size() == performer.episodesPerformances.size()
		performerFull.episodesStuntPerformances.size() == performer.episodesStuntPerformances.size()
		performerFull.episodesStandInPerformances.size() == performer.episodesStandInPerformances.size()
		performerFull.moviesPerformances.size() == performer.moviesPerformances.size()
		performerFull.moviesStuntPerformances.size() == performer.moviesStuntPerformances.size()
		performerFull.moviesStandInPerformances.size() == performer.moviesStandInPerformances.size()
		performerFull.characters.size() == performer.characters.size()
	}

}
