package com.cezarykluczynski.stapi.server.character.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.BloodType as RestBloodType
import com.cezarykluczynski.stapi.client.v1.rest.model.CharacterBase
import com.cezarykluczynski.stapi.client.v1.rest.model.Gender as RestGender
import com.cezarykluczynski.stapi.client.v1.rest.model.MaritalStatus as RestMaritalStatus
import com.cezarykluczynski.stapi.model.character.dto.CharacterRequestDTO
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.server.character.dto.CharacterRestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class CharacterBaseRestMapperTest extends AbstractCharacterMapperTest {

	private static final RestGender REST_GENDER = RestGender.F
	private static final RestBloodType REST_BLOOD_TYPE = RestBloodType.B_NEGATIVE
	private static final RestMaritalStatus REST_MARITAL_STATUS = RestMaritalStatus.MARRIED

	private CharacterBaseRestMapper characterRestMapper

	void setup() {
		characterRestMapper = Mappers.getMapper(CharacterBaseRestMapper)
	}

	void "maps CharacterRestBeanParams to CharacterRequestDTO"() {
		given:
		CharacterRestBeanParams characterRestBeanParams = new CharacterRestBeanParams(
				uid: UID,
				name: NAME,
				gender: MODEL_GENDER,
				deceased: DECEASED,
				mirror: MIRROR,
				alternateReality: ALTERNATE_REALITY)

		when:
		CharacterRequestDTO characterRequestDTO = characterRestMapper.mapBase characterRestBeanParams

		then:
		characterRequestDTO.uid == UID
		characterRequestDTO.name == NAME
		characterRequestDTO.gender == MODEL_GENDER
		characterRequestDTO.deceased == DECEASED
		characterRequestDTO.mirror == MIRROR
		characterRequestDTO.alternateReality == ALTERNATE_REALITY
	}

	void "maps DB entity to base REST entity"() {
		given:
		Character character = createCharacter()

		when:
		CharacterBase restCharacter = characterRestMapper.mapBase(Lists.newArrayList(character))[0]

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
	}

}
