package com.cezarykluczynski.stapi.server.character.mapper

import com.cezarykluczynski.stapi.client.v1.soap.BloodTypeEnum
import com.cezarykluczynski.stapi.client.v1.soap.Character as SOAPCharacter
import com.cezarykluczynski.stapi.client.v1.soap.CharacterRequest
import com.cezarykluczynski.stapi.client.v1.soap.GenderEnum
import com.cezarykluczynski.stapi.client.v1.soap.MaritalStatusEnum
import com.cezarykluczynski.stapi.model.character.dto.CharacterRequestDTO
import com.cezarykluczynski.stapi.model.character.entity.Character as DBCharacter
import com.cezarykluczynski.stapi.model.common.entity.enums.BloodType
import com.cezarykluczynski.stapi.model.common.entity.enums.Gender
import com.cezarykluczynski.stapi.model.common.entity.enums.MaritalStatus
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.util.AbstractIndividualTest
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.mapstruct.factory.Mappers

class CharacterSoapMapperTest extends AbstractIndividualTest {

	private static final GenderEnum SOAP_GENDER = GenderEnum.F
	private static final Gender ENTITY_GENDER = Gender.F
	private static final BloodTypeEnum SOAP_BLOOD_TYPE = BloodTypeEnum.B_NEGATIVE
	private static final BloodType ENTITY_BLOOD_TYPE = BloodType.B_NEGATIVE
	private static final MaritalStatusEnum SOAP_MARITAL_STATUS = MaritalStatusEnum.MARRIED
	private static final MaritalStatus ENTITY_MARITAL_STATUS = MaritalStatus.MARRIED

	private CharacterSoapMapper characterSoapMapper

	def setup() {
		characterSoapMapper = Mappers.getMapper(CharacterSoapMapper)
	}

	def "maps SOAP CharacterRequest to CharacterRequestDTO"() {
		given:
		CharacterRequest characterRequest = new CharacterRequest(
				guid: GUID,
				name: NAME,
				gender: SOAP_GENDER,
				deceased: DECEASED
		)

		when:
		CharacterRequestDTO characterRequestDTO = characterSoapMapper.map characterRequest

		then:
		characterRequestDTO.guid == GUID
		characterRequestDTO.name == NAME
		characterRequestDTO.gender == ENTITY_GENDER
		characterRequestDTO.deceased == DECEASED
	}

	def "maps DB entity to SOAP entity"() {
		given:
		DBCharacter dbCharacter = new DBCharacter(
				name: NAME,
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
				performers: Sets.newHashSet(new Performer())
		)

		when:
		SOAPCharacter soapCharacter = characterSoapMapper.map(Lists.newArrayList(dbCharacter))[0]

		then:
		soapCharacter.name == NAME
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
		soapCharacter.isDeceased() == DECEASED
		soapCharacter.bloodType == SOAP_BLOOD_TYPE
		soapCharacter.maritalStatus == SOAP_MARITAL_STATUS
		soapCharacter.serialNumber == SERIAL_NUMBER
		soapCharacter.performerHeaders.size() == dbCharacter.performers.size()
	}

}
