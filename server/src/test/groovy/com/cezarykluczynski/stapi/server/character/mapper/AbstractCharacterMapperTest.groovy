package com.cezarykluczynski.stapi.server.character.mapper

import com.cezarykluczynski.stapi.client.v1.soap.BloodTypeEnum
import com.cezarykluczynski.stapi.client.v1.soap.GenderEnum
import com.cezarykluczynski.stapi.client.v1.soap.MaritalStatusEnum
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.character.entity.CharacterRelation
import com.cezarykluczynski.stapi.model.character.entity.CharacterSpecies
import com.cezarykluczynski.stapi.model.common.entity.enums.BloodType
import com.cezarykluczynski.stapi.model.common.entity.enums.Gender
import com.cezarykluczynski.stapi.model.common.entity.enums.MaritalStatus
import com.cezarykluczynski.stapi.model.episode.entity.Episode
import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.model.occupation.entity.Occupation
import com.cezarykluczynski.stapi.model.organization.entity.Organization
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.model.title.entity.Title
import com.cezarykluczynski.stapi.util.AbstractIndividualTest

abstract class AbstractCharacterMapperTest extends AbstractIndividualTest {

	protected static final Gender MODEL_GENDER = Gender.F
	protected static final BloodType ENTITY_BLOOD_TYPE = BloodType.B_NEGATIVE
	protected static final MaritalStatus ENTITY_MARITAL_STATUS = MaritalStatus.MARRIED
	protected static final Integer NUMERATOR = 1
	protected static final Integer DENOMINATOR = 2
	protected static final GenderEnum SOAP_GENDER = GenderEnum.F
	protected static final BloodTypeEnum SOAP_BLOOD_TYPE = BloodTypeEnum.B_NEGATIVE
	protected static final MaritalStatusEnum SOAP_MARITAL_STATUS = MaritalStatusEnum.MARRIED

	protected Character createCharacter() {
		new Character(
				name: NAME,
				uid: UID,
				gender: MODEL_GENDER,
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
				hologramActivationDate: HOLOGRAM_ACTIVATION_DATE,
				hologramStatus: HOLOGRAM_STATUS,
				hologramDateStatus: HOLOGRAM_DATE_STATUS,
				serialNumber: SERIAL_NUMBER,
				hologram: HOLOGRAM,
				fictionalCharacter: FICTIONAL_CHARACTER,
				mirror: MIRROR,
				alternateReality: ALTERNATE_REALITY,
				performers: createSetOfRandomNumberOfMocks(Performer),
				episodes: createSetOfRandomNumberOfMocks(Episode),
				movies: createSetOfRandomNumberOfMocks(Movie),
				characterSpecies: createSetOfRandomNumberOfMocks(CharacterSpecies),
				characterRelations: createSetOfRandomNumberOfMocks(CharacterRelation),
				titles: createSetOfRandomNumberOfMocks(Title),
				occupations: createSetOfRandomNumberOfMocks(Occupation),
				organizations: createSetOfRandomNumberOfMocks(Organization))
	}

}
