package com.cezarykluczynski.stapi.server.character.mapper

import com.cezarykluczynski.stapi.client.v1.soap.CharacterFull
import com.cezarykluczynski.stapi.client.v1.soap.CharacterFullRequest
import com.cezarykluczynski.stapi.model.character.dto.CharacterRequestDTO
import com.cezarykluczynski.stapi.model.character.entity.Character
import org.mapstruct.factory.Mappers

class CharacterFullSoapMapperTest extends AbstractCharacterMapperTest {

	private CharacterFullSoapMapper characterFullSoapMapper

	void setup() {
		characterFullSoapMapper = Mappers.getMapper(CharacterFullSoapMapper)
	}

	void "maps SOAP CharacterFullRequest to CharacterBaseRequestDTO"() {
		given:
		CharacterFullRequest characterFullRequest = new CharacterFullRequest(uid: UID)

		when:
		CharacterRequestDTO characterRequestDTO = characterFullSoapMapper.mapFull characterFullRequest

		then:
		characterRequestDTO.uid == UID
	}

	void "maps DB entity to full SOAP entity"() {
		given:
		Character character = createCharacter()

		when:
		CharacterFull characterFull = characterFullSoapMapper.mapFull(character)

		then:
		characterFull.name == NAME
		characterFull.uid == UID
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
