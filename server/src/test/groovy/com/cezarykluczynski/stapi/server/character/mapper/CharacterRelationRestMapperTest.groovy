package com.cezarykluczynski.stapi.server.character.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.CharacterRelation as RestCharacterRelation
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.character.entity.CharacterRelation
import org.mapstruct.factory.Mappers

class CharacterRelationRestMapperTest extends AbstractCharacterMapperTest {

	private static final String TYPE = 'TYPE'

	private CharacterRelationRestMapper characterRelationRestMapper

	void setup() {
		characterRelationRestMapper = Mappers.getMapper(CharacterRelationRestMapper)
	}

	void "maps model CharacterRelation to REST CharacterRelation"() {
		given:
		Character source = new Character(name: 'character 1', uid: 'uid 1')
		Character target = new Character(name: 'character 2', uid: 'uid 2')
		CharacterRelation modelCharacterRelation = new CharacterRelation(
				type: TYPE,
				source: source,
				target: target)

		when:
		RestCharacterRelation restCharacterRelation = characterRelationRestMapper.map(modelCharacterRelation)

		then:
		restCharacterRelation.type == TYPE
		restCharacterRelation.source.name == source.name
		restCharacterRelation.source.uid == source.uid
		restCharacterRelation.target.name == target.name
		restCharacterRelation.target.uid == target.uid
	}

}
