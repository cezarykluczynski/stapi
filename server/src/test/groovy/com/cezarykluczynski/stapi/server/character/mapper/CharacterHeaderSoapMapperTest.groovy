package com.cezarykluczynski.stapi.server.character.mapper

import com.cezarykluczynski.stapi.client.v1.soap.CharacterHeader
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.server.common.mapper.AbstractRealWorldPersonMapperTest
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class CharacterHeaderSoapMapperTest extends AbstractRealWorldPersonMapperTest {

	private CharacterHeaderSoapMapper characterHeaderSoapMapper

	def setup() {
		characterHeaderSoapMapper = Mappers.getMapper(CharacterHeaderSoapMapper)
	}

	def "maps DB entity to SOAP entity"() {
		given:
		Character character = new Character(
				name: NAME,
				guid: GUID)

		when:
		CharacterHeader characterHeader = characterHeaderSoapMapper.map(Lists.newArrayList(character))[0]

		then:
		characterHeader.name == NAME
		characterHeader.guid == GUID
	}

}
