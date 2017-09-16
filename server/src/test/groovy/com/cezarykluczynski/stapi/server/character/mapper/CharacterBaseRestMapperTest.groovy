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
				hologram: HOLOGRAM,
				fictionalCharacter: FICTIONAL_CHARACTER,
				mirror: MIRROR,
				alternateReality: ALTERNATE_REALITY)

		when:
		CharacterRequestDTO characterRequestDTO = characterRestMapper.mapBase characterRestBeanParams

		then:
		characterRequestDTO.uid == UID
		characterRequestDTO.name == NAME
		characterRequestDTO.gender == MODEL_GENDER
		characterRequestDTO.deceased == DECEASED
		characterRequestDTO.hologram == HOLOGRAM
		characterRequestDTO.fictionalCharacter == FICTIONAL_CHARACTER
		characterRequestDTO.mirror == MIRROR
		characterRequestDTO.alternateReality == ALTERNATE_REALITY
	}

	void "maps DB entity to base REST entity"() {
		given:
		Character character = createCharacter()

		when:
		CharacterBase characterBase = characterRestMapper.mapBase(Lists.newArrayList(character))[0]

		then:
		characterBase.name == NAME
		characterBase.gender == REST_GENDER
		characterBase.yearOfBirth == YEAR_OF_BIRTH
		characterBase.monthOfBirth == MONTH_OF_BIRTH
		characterBase.dayOfBirth == DAY_OF_BIRTH
		characterBase.placeOfBirth == PLACE_OF_BIRTH
		characterBase.yearOfDeath == YEAR_OF_DEATH
		characterBase.monthOfDeath == MONTH_OF_DEATH
		characterBase.dayOfDeath == DAY_OF_DEATH
		characterBase.placeOfDeath == PLACE_OF_DEATH
		characterBase.height == HEIGHT
		characterBase.weight == WEIGHT
		characterBase.deceased == DECEASED
		characterBase.bloodType == REST_BLOOD_TYPE
		characterBase.maritalStatus == REST_MARITAL_STATUS
		characterBase.serialNumber == SERIAL_NUMBER
		characterBase.hologramActivationDate == HOLOGRAM_ACTIVATION_DATE
		characterBase.hologramStatus == HOLOGRAM_STATUS
		characterBase.hologramDateStatus == HOLOGRAM_DATE_STATUS
		characterBase.hologram == HOLOGRAM
		characterBase.fictionalCharacter == FICTIONAL_CHARACTER
		characterBase.mirror == MIRROR
		characterBase.alternateReality == ALTERNATE_REALITY
	}

}
