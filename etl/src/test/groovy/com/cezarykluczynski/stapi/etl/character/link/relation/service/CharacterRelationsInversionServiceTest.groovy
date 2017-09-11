package com.cezarykluczynski.stapi.etl.character.link.relation.service

import com.cezarykluczynski.stapi.etl.character.link.relation.dto.CharacterRelationName
import com.google.common.collect.Sets
import spock.lang.Specification

class CharacterRelationsInversionServiceTest extends Specification {

	private CharacterRelationsInversionService characterRelationsInversionService

	void setup() {
		characterRelationsInversionService = new CharacterRelationsInversionService()
	}

	void "provides value for existing key"() {
		expect:
		characterRelationsInversionService.invert(CharacterRelationName.CREATOR) == CharacterRelationName.CREATION
	}

	void "provides value for non-existing key"() {
		expect:
		characterRelationsInversionService.invert('some string') == null
	}

	void "relations names values are also keys"() {
		given:
		Set<String> keySet = CharacterRelationsInversionService.MAPPINGS.keySet()
		Set<String> values = Sets.newHashSet(CharacterRelationsInversionService.MAPPINGS.values())

		when:
		values.removeAll(keySet)

		then:
		values.empty
	}

}
