package com.cezarykluczynski.stapi.server.character.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.BloodType as RestBloodType
import com.cezarykluczynski.stapi.client.v1.rest.model.Character as RESTCharacter
import com.cezarykluczynski.stapi.client.v1.rest.model.Gender as RestGender
import com.cezarykluczynski.stapi.client.v1.rest.model.MaritalStatus as RestMaritalStatus
import com.cezarykluczynski.stapi.model.character.dto.CharacterRequestDTO
import com.cezarykluczynski.stapi.model.character.entity.Character as DBCharacter
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.server.character.dto.CharacterRestBeanParams
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.mapstruct.factory.Mappers

class CharacterRestMapperTest extends AbstractCharacterMapperTest {

	private static final RestGender REST_GENDER = RestGender.F
	private static final RestBloodType REST_BLOOD_TYPE = RestBloodType.B_NEGATIVE
	private static final RestMaritalStatus REST_MARITAL_STATUS = RestMaritalStatus.MARRIED

	private CharacterRestMapper characterRestMapper

	def setup() {
		characterRestMapper = Mappers.getMapper(CharacterRestMapper)
	}

	def "maps CharacterRestBeanParams to CharacterRequestDTO"() {
		given:
		CharacterRestBeanParams characterRestBeanParams = new CharacterRestBeanParams(
				guid: GUID,
				name: NAME,
				gender: ENTITY_GENDER,
				deceased: DECEASED
		)

		when:
		CharacterRequestDTO characterRequestDTO = characterRestMapper.map characterRestBeanParams

		then:
		characterRequestDTO.guid == GUID
		characterRequestDTO.name == NAME
		characterRequestDTO.gender == ENTITY_GENDER
		characterRequestDTO.getDeceased() == DECEASED
	}

	def "maps DB entity to REST entity"() {
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
		RESTCharacter restCharacter = characterRestMapper.map(Lists.newArrayList(dbCharacter))[0]

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
		restCharacter.performerHeaders.size() == dbCharacter.performers.size()
	}

}
