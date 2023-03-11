package com.cezarykluczynski.stapi.server.performer.mapper

import com.cezarykluczynski.stapi.client.rest.model.PerformerFull
import com.cezarykluczynski.stapi.client.rest.model.PerformerV2Full
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

	void "maps DB entity to full REST V2 entity"() {
		given:
		Performer performer = createPerformer()

		when:
		PerformerV2Full performerV2Full = performerFullRestMapper.mapV2Full(performer)

		then:
		performerV2Full.name == NAME
		performerV2Full.uid == UID
		performerV2Full.birthName == BIRTH_NAME
		performerV2Full.gender == GENDER_ENUM_REST
		performerV2Full.dateOfBirth == DATE_OF_BIRTH_FROM_DB
		performerV2Full.dateOfDeath == DATE_OF_DEATH_FROM_DB
		performerV2Full.placeOfBirth == PLACE_OF_BIRTH
		performerV2Full.placeOfDeath == PLACE_OF_DEATH
		performerV2Full.animalPerformer == ANIMAL_PERFORMER
		performerV2Full.audiobookPerformer == AUDIOBOOK_PERFORMER
		performerV2Full.cutPerformer == CUT_PERFORMER
		performerV2Full.disPerformer == DIS_PERFORMER
		performerV2Full.ds9Performer == DS9_PERFORMER
		performerV2Full.entPerformer == ENT_PERFORMER
		performerV2Full.filmPerformer == FILM_PERFORMER
		performerV2Full.ldPerformer == LD_PERFORMER
		performerV2Full.picPerformer == PIC_PERFORMER
		performerV2Full.proPerformer == PRO_PERFORMER
		performerV2Full.puppeteer == PUPPETEER
		performerV2Full.snwPerformer == SNW_PERFORMER
		performerV2Full.standInPerformer == STAND_IN_PERFORMER
		performerV2Full.stPerformer == ST_PERFORMER
		performerV2Full.stuntPerformer == STUNT_PERFORMER
		performerV2Full.tasPerformer == TAS_PERFORMER
		performerV2Full.tngPerformer == TNG_PERFORMER
		performerV2Full.tosPerformer == TOS_PERFORMER
		performerV2Full.videoGamePerformer == VIDEO_GAME_PERFORMER
		performerV2Full.voicePerformer == VOICE_PERFORMER
		performerV2Full.voyPerformer == VOY_PERFORMER
		performerV2Full.episodesPerformances.size() == performer.episodesPerformances.size()
		performerV2Full.episodesStuntPerformances.size() == performer.episodesStuntPerformances.size()
		performerV2Full.episodesStandInPerformances.size() == performer.episodesStandInPerformances.size()
		performerV2Full.moviesPerformances.size() == performer.moviesPerformances.size()
		performerV2Full.moviesStuntPerformances.size() == performer.moviesStuntPerformances.size()
		performerV2Full.moviesStandInPerformances.size() == performer.moviesStandInPerformances.size()
		performerV2Full.characters.size() == performer.characters.size()
	}

}
