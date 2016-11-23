package com.cezarykluczynski.stapi.server.character.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.BloodType as RestBloodType
import com.cezarykluczynski.stapi.client.v1.rest.model.Character as RESTCharacter
import com.cezarykluczynski.stapi.client.v1.rest.model.Gender as RestGender
import com.cezarykluczynski.stapi.client.v1.rest.model.MaritalStatus as RestMaritalStatus
import com.cezarykluczynski.stapi.model.character.entity.Character as DBCharacter
import com.cezarykluczynski.stapi.model.common.entity.BloodType
import com.cezarykluczynski.stapi.model.common.entity.Gender
import com.cezarykluczynski.stapi.model.common.entity.MaritalStatus
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.util.AbstractIndividualTest
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.mapstruct.factory.Mappers

class CharacterRestMapperTest extends AbstractIndividualTest {

	private static final RestGender REST_GENDER = RestGender.F
	private static final Gender ENTITY_GENDER = Gender.F
	private static final RestBloodType REST_BLOOD_TYPE = RestBloodType.B_NEGATIVE
	private static final BloodType ENTITY_BLOOD_TYPE = BloodType.B_NEGATIVE
	private static final RestMaritalStatus REST_MARITAL_STATUS = RestMaritalStatus.MARRIED
	private static final MaritalStatus ENTITY_MARITAL_STATUS = MaritalStatus.MARRIED

	private CharacterRestMapper characterRestMapper

	def setup() {
		characterRestMapper = Mappers.getMapper(CharacterRestMapper)
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
