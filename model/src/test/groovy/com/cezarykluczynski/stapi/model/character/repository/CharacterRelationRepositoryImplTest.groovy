package com.cezarykluczynski.stapi.model.character.repository

import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.character.entity.CharacterRelation
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import spock.lang.Specification

class CharacterRelationRepositoryImplTest extends Specification {

	private static final Long ID_1 = 11
	private static final Long ID_2 = 12

	private CharacterRepository characterRepositoryMock

	private CharacterRelationRepository characterRelationRepositoryMock

	private CharacterRelationRepositoryImpl characterRelationRepositoryImpl

	void setup() {
		characterRepositoryMock = Mock()
		characterRelationRepositoryMock = Mock()
		characterRelationRepositoryImpl = new CharacterRelationRepositoryImpl(characterRepositoryMock)
		characterRelationRepositoryImpl.characterRelationRepository = characterRelationRepositoryMock
	}

	void "links and saves relations"() {
		given:
		Character subject = new Character(id: 1)
		Character target1 = new Character(id: 2)
		Character target2 = new Character(id: 3)
		CharacterRelation characterRelation1 = new CharacterRelation(
				type: 'Creator',
				source: subject,
				target: target1)
		CharacterRelation characterRelation2 = new CharacterRelation(
				type: 'Relative',
				source: subject,
				target: target2)

		when:
		characterRelationRepositoryImpl.linkAndSave(Lists.newArrayList(characterRelation1, characterRelation2))

		then:
		1 * characterRelationRepositoryMock.save(characterRelation1) >> { CharacterRelation characterRelation ->
			characterRelation.id = ID_1
			characterRelation
		}
		1 * characterRelationRepositoryMock.save(characterRelation2) >> { CharacterRelation characterRelation ->
			characterRelation.id = ID_2
			characterRelation
		}
		1 * characterRepositoryMock.save(Sets.newHashSet(subject, target1, target2))
		0 * _
		subject.characterRelations.contains characterRelation1
		subject.characterRelations.contains characterRelation2
		target1.characterRelations.contains characterRelation1
		target2.characterRelations.contains characterRelation2
		!target1.characterRelations.contains(characterRelation2)
		!target2.characterRelations.contains(characterRelation1)
		characterRelation1.id == ID_1
		characterRelation2.id == ID_2
	}

}
