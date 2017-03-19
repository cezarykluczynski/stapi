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
		CharacterFull restCharacter = characterFullRestMapper.mapFull(character)

		then:
		restCharacter.name == NAME
		restCharacter.gender == REST_GENDER
		restCharacter.yearOfBirth == YEAR_OF_BIRTH
		restCharacter.monthOfBirth == MONTH_OF_BIRTH
		restCharacter.dayOfBirth == DAY_OF_BIRTH
		restCharacter.placeOfBirth == PLACE_OF_BIRTH
		restCharacter.yearOfDeath == YEAR_OF_DEATH
		restCharacter.monthOfDeath == MONTH_OF_DEATH
		restCharacter.dayOfDeath == DAY_OF_DEATH
		restCharacter.placeOfDeath == PLACE_OF_DEATH
		restCharacter.height == HEIGHT
		restCharacter.weight == WEIGHT
		restCharacter.deceased == DECEASED
		restCharacter.bloodType == REST_BLOOD_TYPE
		restCharacter.maritalStatus == REST_MARITAL_STATUS
		restCharacter.serialNumber == SERIAL_NUMBER
		restCharacter.mirror == MIRROR
		restCharacter.alternateReality == ALTERNATE_REALITY
		restCharacter.performers.size() == character.performers.size()
		restCharacter.episodes.size() == character.episodes.size()
		restCharacter.movies.size() == character.movies.size()
		restCharacter.characterSpecies.size() == character.characterSpecies.size()
	}

}
