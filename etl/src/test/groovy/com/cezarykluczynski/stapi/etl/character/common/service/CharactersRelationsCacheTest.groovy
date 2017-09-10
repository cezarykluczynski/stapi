package com.cezarykluczynski.stapi.etl.character.common.service

import com.cezarykluczynski.stapi.etl.character.common.dto.CharacterRelationCacheKey
import com.cezarykluczynski.stapi.etl.character.common.dto.CharacterRelationsMap
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import spock.lang.Specification

class CharactersRelationsCacheTest extends Specification {

	private static final Long KEY = 1L

	private CharactersRelationsCache charactersRelationsCache

	void setup() {
		charactersRelationsCache = new CharactersRelationsCache()
	}

	void "returns null for non existing entry"() {
		expect:
		charactersRelationsCache.get(KEY) == null
	}

	void "returns records put info cache"() {
		given:
		CharacterRelationCacheKey characterRelationCacheKey1 = Mock()
		CharacterRelationCacheKey characterRelationCacheKey2 = Mock()
		CharacterRelationCacheKey characterRelationCacheKey3 = Mock()
		CharacterRelationCacheKey characterRelationCacheKey4 = Mock()
		Template.Part templatePart1 = Mock()
		Template.Part templatePart2 = Mock()
		Template.Part templatePart3 = Mock()
		Template.Part templatePart4 = Mock()
		CharacterRelationsMap characterRelationsMap1 = new CharacterRelationsMap()
		characterRelationsMap1.put(characterRelationCacheKey1, templatePart1)
		characterRelationsMap1.put(characterRelationCacheKey2, templatePart2)
		CharacterRelationsMap characterRelationsMap2 = new CharacterRelationsMap()
		characterRelationsMap2.put(characterRelationCacheKey3, templatePart3)
		characterRelationsMap2.put(characterRelationCacheKey4, templatePart4)

		when:
		charactersRelationsCache.put(KEY, characterRelationsMap1)
		CharacterRelationsMap characterRelationsMapResult1 = charactersRelationsCache.get(KEY)

		then:
		characterRelationsMapResult1.size() == 2
		characterRelationsMapResult1[characterRelationCacheKey1] == templatePart1
		characterRelationsMapResult1[characterRelationCacheKey2] == templatePart2

		when:
		charactersRelationsCache.put(KEY, characterRelationsMap2)
		CharacterRelationsMap characterRelationsMapResult2 = charactersRelationsCache.get(KEY)

		then:
		characterRelationsMapResult2.size() == 4
		characterRelationsMapResult2[characterRelationCacheKey1] == templatePart1
		characterRelationsMapResult2[characterRelationCacheKey2] == templatePart2
		characterRelationsMapResult2[characterRelationCacheKey3] == templatePart3
		characterRelationsMapResult2[characterRelationCacheKey4] == templatePart4
	}

}
