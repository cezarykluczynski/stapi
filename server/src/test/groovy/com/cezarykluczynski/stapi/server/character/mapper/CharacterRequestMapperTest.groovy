package com.cezarykluczynski.stapi.server.character.mapper

import com.cezarykluczynski.stapi.client.v1.soap.CharacterRequest
import com.cezarykluczynski.stapi.client.v1.soap.GenderEnum
import com.cezarykluczynski.stapi.model.character.dto.CharacterRequestDTO
import com.cezarykluczynski.stapi.model.common.entity.Gender
import com.cezarykluczynski.stapi.util.tool.LogicUtil
import org.mapstruct.factory.Mappers
import spock.lang.Specification

class CharacterRequestMapperTest extends Specification {

	private static final String GUID = 'GUID'
	private static final String NAME = 'NAME'
	private static final GenderEnum GENDER_SOAP = GenderEnum.F
	private static final Gender GENDER = Gender.F
	private static final Boolean DECEASED = LogicUtil.nextBoolean()

	private CharacterRequestMapper characterRequestMapper

	def setup() {
		characterRequestMapper = Mappers.getMapper(CharacterRequestMapper)
	}

	def "maps SOAP PerformerRequest to PerformerRequestDTO"() {
		given:
		CharacterRequest characterRequest = new CharacterRequest(
				guid: GUID,
				name: NAME,
				gender: GENDER_SOAP,
				deceased: DECEASED
		)

		when:
		CharacterRequestDTO characterRequestDTO = characterRequestMapper.map characterRequest

		then:
		characterRequestDTO.guid == GUID
		characterRequestDTO.name == NAME
		characterRequestDTO.gender == GENDER
		characterRequestDTO.deceased == DECEASED
	}

}
