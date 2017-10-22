package com.cezarykluczynski.stapi.server.character.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.BloodType
import com.cezarykluczynski.stapi.client.v1.rest.model.CharacterFull
import com.cezarykluczynski.stapi.client.v1.rest.model.Gender
import com.cezarykluczynski.stapi.client.v1.rest.model.MaritalStatus
import com.cezarykluczynski.stapi.model.character.entity.Character
import org.mapstruct.factory.Mappers

class CharacterFullRestMapperTest extends AbstractCharacterMapperTest {

	private static final Gender REST_GENDER = Gender.F
	private static final BloodType REST_BLOOD_TYPE = BloodType.B_NEGATIVE
	private static final MaritalStatus REST_MARITAL_STATUS = MaritalStatus.MARRIED

	private CharacterFullRestMapper characterFullRestMapper

	void setup() {
		characterFullRestMapper = Mappers.getMapper(CharacterFullRestMapper)
	}

	void "maps DB entity to full REST entity"() {
		given:
		Character character = createCharacter()

		when:
		CharacterFull characterFull = characterFullRestMapper.mapFull(character)

		then:
		characterFull.name == NAME
		characterFull.gender == REST_GENDER
		characterFull.yearOfBirth == YEAR_OF_BIRTH
		characterFull.monthOfBirth == MONTH_OF_BIRTH
		characterFull.dayOfBirth == DAY_OF_BIRTH
		characterFull.placeOfBirth == PLACE_OF_BIRTH
		characterFull.yearOfDeath == YEAR_OF_DEATH
		characterFull.monthOfDeath == MONTH_OF_DEATH
		characterFull.dayOfDeath == DAY_OF_DEATH
		characterFull.placeOfDeath == PLACE_OF_DEATH
		characterFull.height == HEIGHT
		characterFull.weight == WEIGHT
		characterFull.deceased == DECEASED
		characterFull.bloodType == REST_BLOOD_TYPE
		characterFull.maritalStatus == REST_MARITAL_STATUS
		characterFull.serialNumber == SERIAL_NUMBER
		characterFull.hologramActivationDate == HOLOGRAM_ACTIVATION_DATE
		characterFull.hologramStatus == HOLOGRAM_STATUS
		characterFull.hologramDateStatus == HOLOGRAM_DATE_STATUS
		characterFull.hologram == HOLOGRAM
		characterFull.fictionalCharacter == FICTIONAL_CHARACTER
		characterFull.mirror == MIRROR
		characterFull.alternateReality == ALTERNATE_REALITY
		characterFull.performers.size() == character.performers.size()
		characterFull.episodes.size() == character.episodes.size()
		characterFull.movies.size() == character.movies.size()
		characterFull.characterSpecies.size() == character.characterSpecies.size()
		characterFull.characterRelations.size() == character.characterRelations.size()
		characterFull.titles.size() == character.titles.size()
		characterFull.occupations.size() == character.occupations.size()
		characterFull.organizations.size() == character.organizations.size()
	}

}
