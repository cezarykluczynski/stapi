package com.cezarykluczynski.stapi.server.performer.mapper

import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.episode.entity.Episode
import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.server.common.mapper.AbstractRealWorldPersonMapperTest

abstract class AbstractPerformerMapperTest extends AbstractRealWorldPersonMapperTest {

	protected Performer createPerformer() {
		new Performer(
				name: NAME,
				uid: UID,
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
				voyPerformer: VOY_PERFORMER,
				episodesPerformances: createSetOfRandomNumberOfMocks(Episode),
				episodesStuntPerformances: createSetOfRandomNumberOfMocks(Episode),
				episodesStandInPerformances: createSetOfRandomNumberOfMocks(Episode),
				moviesPerformances: createSetOfRandomNumberOfMocks(Movie),
				moviesStuntPerformances: createSetOfRandomNumberOfMocks(Movie),
				moviesStandInPerformances: createSetOfRandomNumberOfMocks(Movie),
				characters: createSetOfRandomNumberOfMocks(Character))
	}

}
