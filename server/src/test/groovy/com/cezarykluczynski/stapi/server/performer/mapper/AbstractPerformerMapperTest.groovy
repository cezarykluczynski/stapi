package com.cezarykluczynski.stapi.server.performer.mapper

import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.episode.entity.Episode
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.server.common.mapper.AbstractRealWorldPersonMapperTest
import com.google.common.collect.Lists

abstract class AbstractPerformerMapperTest extends AbstractRealWorldPersonMapperTest {

	protected Performer createPerformer() {
		return new Performer(
				name: NAME,
				guid: GUID,
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
				performances: Lists.newArrayList(Mock(Episode)),
				stuntPerformances: Lists.newArrayList(Mock(Episode)),
				standInPerformances: Lists.newArrayList(Mock(Episode)),
				characters: Lists.newArrayList(Mock(Character)))
	}

}
