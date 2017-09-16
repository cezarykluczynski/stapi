package com.cezarykluczynski.stapi.server.character.mapper

import com.cezarykluczynski.stapi.client.v1.soap.CharacterRelation as SoapCharacterRelation
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.character.entity.CharacterRelation
import org.mapstruct.factory.Mappers

class CharacterRelationSoapMapperTest extends AbstractCharacterMapperTest {

	private static final String TYPE = 'TYPE'

	private CharacterRelationSoapMapper characterRelationSoapMapper

	void setup() {
		characterRelationSoapMapper = Mappers.getMapper(CharacterRelationSoapMapper)
	}

	void "maps model CharacterRelation to SOAP CharacterRelation"() {
		given:
		Character source = new Character(name: 'character 1', uid: 'uid 1')
		Character target = new Character(name: 'character 2', uid: 'uid 2')
		CharacterRelation modelCharacterRelation = new CharacterRelation(
				type: TYPE,
				source: source,
				target: target)

		when:
		SoapCharacterRelation soapCharacterRelation = characterRelationSoapMapper.map(modelCharacterRelation)

		then:
		soapCharacterRelation.type == TYPE
		soapCharacterRelation.source.name == source.name
		soapCharacterRelation.source.uid == source.uid
		soapCharacterRelation.target.name == target.name
		soapCharacterRelation.target.uid == target.uid
	}

}
