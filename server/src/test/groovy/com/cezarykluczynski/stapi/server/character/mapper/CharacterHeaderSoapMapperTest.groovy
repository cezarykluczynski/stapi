package com.cezarykluczynski.stapi.server.character.mapper

import com.cezarykluczynski.stapi.client.v1.soap.CharacterHeader
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.server.common.mapper.AbstractRealWorldPersonMapperTest
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class CharacterHeaderSoapMapperTest extends AbstractRealWorldPersonMapperTest {

	private CharacterHeaderSoapMapper characterHeaderSoapMapper

	void setup() {
		characterHeaderSoapMapper = Mappers.getMapper(CharacterHeaderSoapMapper)
	}

	void "maps DB entity to SOAP header"() {
		given:
		Character character = new Character(
				name: NAME,
				uid: UID)

		when:
		CharacterHeader characterHeader = characterHeaderSoapMapper.map(Lists.newArrayList(character))[0]

		then:
		characterHeader.name == NAME
		characterHeader.uid == UID
	}

}
