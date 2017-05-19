package com.cezarykluczynski.stapi.server.performer.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerFull
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import org.mapstruct.factory.Mappers

class PerformerFullRestMapperTest extends AbstractPerformerMapperTest {

	private PerformerFullRestMapper performerFullRestMapper

	void setup() {
		performerFullRestMapper = Mappers.getMapper(PerformerFullRestMapper)
	}

	void "maps DB entity to full REST entity"() {
		given:
		Performer performer = createPerformer()

		when:
		PerformerFull performerFull = performerFullRestMapper.mapFull(performer)

		then:
		performerFull.name == NAME
		performerFull.uid == UID
		performerFull.birthName == BIRTH_NAME
		performerFull.gender == GENDER_ENUM_REST
		performerFull.dateOfBirth == DATE_OF_BIRTH_FROM_DB
		performerFull.dateOfDeath == DATE_OF_DEATH_FROM_DB
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
