package com.cezarykluczynski.stapi.server.character.mapper

import com.cezarykluczynski.stapi.client.v1.soap.BloodTypeEnum
import com.cezarykluczynski.stapi.client.v1.soap.CharacterBase
import com.cezarykluczynski.stapi.client.v1.soap.CharacterBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.CharacterFull
import com.cezarykluczynski.stapi.client.v1.soap.CharacterFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.GenderEnum
import com.cezarykluczynski.stapi.client.v1.soap.MaritalStatusEnum
import com.cezarykluczynski.stapi.model.character.dto.CharacterRequestDTO
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class CharacterSoapMapperTest extends AbstractCharacterMapperTest {

	private static final GenderEnum SOAP_GENDER = GenderEnum.F
	private static final BloodTypeEnum SOAP_BLOOD_TYPE = BloodTypeEnum.B_NEGATIVE
	private static final MaritalStatusEnum SOAP_MARITAL_STATUS = MaritalStatusEnum.MARRIED

	private CharacterSoapMapper characterSoapMapper

	void setup() {
		characterSoapMapper = Mappers.getMapper(CharacterSoapMapper)
	}

	void "maps SOAP CharacterBaseRequest to CharacterRequestDTO"() {
		given:
		CharacterBaseRequest characterBaseRequest = new CharacterBaseRequest(
				name: NAME,
				gender: SOAP_GENDER,
				deceased: DECEASED,
				mirror: MIRROR,
				alternateReality: ALTERNATE_REALITY)

		when:
		CharacterRequestDTO characterRequestDTO = characterSoapMapper.mapBase characterBaseRequest

		then:
		characterRequestDTO.name == NAME
		characterRequestDTO.gender == MODEL_GENDER
		characterRequestDTO.deceased == DECEASED
		characterRequestDTO.mirror == MIRROR
		characterRequestDTO.alternateReality == ALTERNATE_REALITY
	}

	void "maps SOAP CharacterFullRequest to CharacterBaseRequestDTO"() {
		given:
		CharacterFullRequest characterFullRequest = new CharacterFullRequest(guid: GUID)

		when:
		CharacterRequestDTO characterRequestDTO = characterSoapMapper.mapFull characterFullRequest

		then:
		characterRequestDTO.guid == GUID
	}

	void "maps DB entity to base SOAP entity"() {
		given:
		Character character = createCharacter()

		when:
		CharacterBase characterBase = characterSoapMapper.mapBase(Lists.newArrayList(character))[0]

		then:
		characterBase.name == NAME
		characterBase.guid == GUID
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
		characterBase.mirror == MIRROR
		characterBase.alternateReality == ALTERNATE_REALITY
	}

	void "maps DB entity to full SOAP entity"() {
		given:
		Character character = createCharacter()

		when:
		CharacterFull characterFull = characterSoapMapper.mapFull(character)

		then:
		characterFull.name == NAME
		characterFull.guid == GUID
		characterFull.gender == SOAP_GENDER
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
		characterFull.bloodType == SOAP_BLOOD_TYPE
		characterFull.maritalStatus == SOAP_MARITAL_STATUS
		characterFull.serialNumber == SERIAL_NUMBER
		characterFull.mirror == MIRROR
		characterFull.alternateReality == ALTERNATE_REALITY
		characterFull.performers.size() == character.performers.size()
		characterFull.episodes.size() == character.episodes.size()
		characterFull.movies.size() == character.movies.size()
		characterFull.characterSpecies.size() == character.characterSpecies.size()
	}

}
