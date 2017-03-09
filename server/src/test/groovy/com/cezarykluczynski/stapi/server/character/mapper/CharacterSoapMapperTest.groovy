package com.cezarykluczynski.stapi.server.character.mapper

import com.cezarykluczynski.stapi.client.v1.soap.BloodTypeEnum
import com.cezarykluczynski.stapi.client.v1.soap.Character as SOAPCharacter
import com.cezarykluczynski.stapi.client.v1.soap.CharacterRequest
import com.cezarykluczynski.stapi.client.v1.soap.GenderEnum
import com.cezarykluczynski.stapi.client.v1.soap.MaritalStatusEnum
import com.cezarykluczynski.stapi.model.character.dto.CharacterRequestDTO
import com.cezarykluczynski.stapi.model.character.entity.Character as DBCharacter
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

	void "maps SOAP CharacterRequest to CharacterRequestDTO"() {
		given:
		CharacterRequest characterRequest = new CharacterRequest(
				guid: GUID,
				name: NAME,
				gender: SOAP_GENDER,
				deceased: DECEASED,
				mirror: MIRROR,
				alternateReality: ALTERNATE_REALITY)

		when:
		CharacterRequestDTO characterRequestDTO = characterSoapMapper.map characterRequest

		then:
		characterRequestDTO.guid == GUID
		characterRequestDTO.name == NAME
		characterRequestDTO.gender == MODEL_GENDER
		characterRequestDTO.deceased == DECEASED
		characterRequestDTO.mirror == MIRROR
		characterRequestDTO.alternateReality == ALTERNATE_REALITY
	}

	void "maps DB entity to SOAP entity"() {
		given:
		DBCharacter dbCharacter = createCharacter()

		when:
		SOAPCharacter soapCharacter = characterSoapMapper.map(Lists.newArrayList(dbCharacter))[0]

		then:
		soapCharacter.name == NAME
		soapCharacter.guid == GUID
		soapCharacter.gender == SOAP_GENDER
		soapCharacter.yearOfBirth == YEAR_OF_BIRTH
		soapCharacter.monthOfBirth == MONTH_OF_BIRTH
		soapCharacter.dayOfBirth == DAY_OF_BIRTH
		soapCharacter.placeOfBirth == PLACE_OF_BIRTH
		soapCharacter.yearOfDeath == YEAR_OF_DEATH
		soapCharacter.monthOfDeath == MONTH_OF_DEATH
		soapCharacter.dayOfDeath == DAY_OF_DEATH
		soapCharacter.placeOfDeath == PLACE_OF_DEATH
		soapCharacter.height == HEIGHT
		soapCharacter.weight == WEIGHT
		soapCharacter.deceased == DECEASED
		soapCharacter.bloodType == SOAP_BLOOD_TYPE
		soapCharacter.maritalStatus == SOAP_MARITAL_STATUS
		soapCharacter.serialNumber == SERIAL_NUMBER
		soapCharacter.mirror == MIRROR
		soapCharacter.alternateReality == ALTERNATE_REALITY
		soapCharacter.performerHeaders.size() == dbCharacter.performers.size()
		soapCharacter.episodeHeaders.size() == dbCharacter.episodes.size()
		soapCharacter.movieHeaders.size() == dbCharacter.movies.size()
		soapCharacter.characterSpecies.size() == dbCharacter.characterSpecies.size()
	}

}
