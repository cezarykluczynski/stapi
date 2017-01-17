package com.cezarykluczynski.stapi.server.character.mapper

import com.cezarykluczynski.stapi.model.common.entity.enums.BloodType
import com.cezarykluczynski.stapi.model.common.entity.enums.Gender
import com.cezarykluczynski.stapi.model.common.entity.enums.MaritalStatus
import com.cezarykluczynski.stapi.model.episode.entity.Episode
import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.util.AbstractIndividualTest
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.google.common.collect.Sets

abstract class AbstractCharacterMapperTest extends AbstractIndividualTest {

	protected static final Gender ENTITY_GENDER = Gender.F
	protected static final BloodType ENTITY_BLOOD_TYPE = BloodType.B_NEGATIVE
	protected static final MaritalStatus ENTITY_MARITAL_STATUS = MaritalStatus.MARRIED

	protected Character createCharacter() {
		return new Character(
				name: NAME,
				guid: GUID,
				gender: ENTITY_GENDER,
				yearOfBirth: YEAR_OF_BIRTH,
				monthOfBirth: MONTH_OF_BIRTH,
				dayOfBirth: DAY_OF_BIRTH,
				placeOfBirth: PLACE_OF_BIRTH,
				yearOfDeath: YEAR_OF_DEATH,
				monthOfDeath: MONTH_OF_DEATH,
				dayOfDeath: DAY_OF_DEATH,
				placeOfDeath: PLACE_OF_DEATH,
				height: HEIGHT,
				weight: WEIGHT,
				deceased: DECEASED,
				bloodType: ENTITY_BLOOD_TYPE,
				maritalStatus: ENTITY_MARITAL_STATUS,
				serialNumber: SERIAL_NUMBER,
				mirror: MIRROR,
				alternateReality: ALTERNATE_REALITY,
				performers: Sets.newHashSet(Mock(Performer)),
				episodes: Sets.newHashSet(Mock(Episode)),
				movies: Sets.newHashSet(Mock(Movie))
		)
	}

}
