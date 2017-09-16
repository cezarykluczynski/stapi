package com.cezarykluczynski.stapi.server.character.mapper

import com.cezarykluczynski.stapi.client.v1.soap.CharacterBase
import com.cezarykluczynski.stapi.client.v1.soap.CharacterBaseRequest
import com.cezarykluczynski.stapi.model.character.dto.CharacterRequestDTO
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class CharacterBaseSoapMapperTest extends AbstractCharacterMapperTest {

	private CharacterBaseSoapMapper characterSoapMapper

	void setup() {
		characterSoapMapper = Mappers.getMapper(CharacterBaseSoapMapper)
	}

	void "maps SOAP CharacterBaseRequest to CharacterRequestDTO"() {
		given:
		CharacterBaseRequest characterBaseRequest = new CharacterBaseRequest(
				name: NAME,
				gender: SOAP_GENDER,
				deceased: DECEASED,
				hologram: HOLOGRAM,
				fictionalCharacter: FICTIONAL_CHARACTER,
				mirror: MIRROR,
				alternateReality: ALTERNATE_REALITY)

		when:
		CharacterRequestDTO characterRequestDTO = characterSoapMapper.mapBase characterBaseRequest

		then:
		characterRequestDTO.name == NAME
		characterRequestDTO.gender == MODEL_GENDER
		characterRequestDTO.deceased == DECEASED
		characterRequestDTO.hologram == HOLOGRAM
		characterRequestDTO.fictionalCharacter == FICTIONAL_CHARACTER
		characterRequestDTO.mirror == MIRROR
		characterRequestDTO.alternateReality == ALTERNATE_REALITY
	}

	void "maps DB entity to base SOAP entity"() {
		given:
		Character character = createCharacter()

		when:
		CharacterBase characterBase = characterSoapMapper.mapBase(Lists.newArrayList(character))[0]

		then:
		characterBase.name == NAME
		characterBase.uid == UID
		characterBase.gender == SOAP_GENDER
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
		characterBase.bloodType == SOAP_BLOOD_TYPE
		characterBase.maritalStatus == SOAP_MARITAL_STATUS
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
