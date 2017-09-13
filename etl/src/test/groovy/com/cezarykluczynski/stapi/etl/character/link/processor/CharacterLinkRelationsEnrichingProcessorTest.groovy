package com.cezarykluczynski.stapi.etl.character.link.processor

import com.cezarykluczynski.stapi.etl.character.common.dto.CharacterRelationCacheKey
import com.cezarykluczynski.stapi.etl.character.common.dto.CharacterRelationsMap
import com.cezarykluczynski.stapi.etl.character.link.relation.dto.CharacterPageLinkWithRelationName
import com.cezarykluczynski.stapi.etl.character.link.relation.service.CharacterLinkExtendedRelationsExtractor
import com.cezarykluczynski.stapi.etl.character.link.relation.service.CharacterRelationFactory
import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.character.entity.CharacterRelation
import com.cezarykluczynski.stapi.model.character.repository.CharacterRelationRepository
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

class CharacterLinkRelationsEnrichingProcessorTest extends Specification {

	private static final String KEY_1 = 'KEY_1'
	private static final String KEY_2 = 'KEY_2'
	private static final String WIKITEXT_1 = 'WIKITEXT_1'
	private static final String WIKITEXT_2 = 'WIKITEXT_2'

	private CharacterLinkExtendedRelationsExtractor characterLinkExtendedRelationsExtractorMock

	private CharacterRelationFactory characterRelationFactoryMock

	private  CharacterRelationRepository characterRelationRepositoryMock

	private CharacterLinkRelationsEnrichingProcessor characterLinkRelationsEnrichingProcessor

	void setup() {
		characterLinkExtendedRelationsExtractorMock = Mock()
		characterRelationFactoryMock = Mock()
		characterRelationRepositoryMock = Mock()
		characterLinkRelationsEnrichingProcessor = new CharacterLinkRelationsEnrichingProcessor(characterLinkExtendedRelationsExtractorMock,
				characterRelationFactoryMock, characterRelationRepositoryMock)
	}

	void "creates and saves relations from CharacterRelationsMap"() {
		given:
		CharacterRelationCacheKey characterRelationCacheKey1 = Mock()
		CharacterRelationCacheKey characterRelationCacheKey2 = Mock()
		CharacterRelationCacheKey characterRelationCacheKey3 = Mock()
		Template.Part templatePart1 = new Template.Part(key: KEY_1, value: WIKITEXT_1)
		Template.Part templatePart2 = new Template.Part()
		Template.Part templatePart3 = new Template.Part(key: KEY_2, value: WIKITEXT_2)
		CharacterRelationsMap characterRelationsMap = new CharacterRelationsMap()
		characterRelationsMap.put(characterRelationCacheKey1, templatePart1)
		characterRelationsMap.put(characterRelationCacheKey2, templatePart2)
		characterRelationsMap.put(characterRelationCacheKey3, templatePart3)
		Character character = Mock()
		CharacterPageLinkWithRelationName characterPageLinkWithRelationName1 = Mock()
		CharacterPageLinkWithRelationName characterPageLinkWithRelationName2 = Mock()
		CharacterPageLinkWithRelationName characterPageLinkWithRelationName3 = Mock()
		CharacterRelation characterRelation1 = Mock()
		CharacterRelation characterRelation2 = Mock()

		when:
		characterLinkRelationsEnrichingProcessor.enrich(EnrichablePair.of(characterRelationsMap, character))

		then:
		1 * characterLinkExtendedRelationsExtractorMock.extract(WIKITEXT_1) >> Lists
				.newArrayList(characterPageLinkWithRelationName1, characterPageLinkWithRelationName2)
		1 * characterRelationFactoryMock.create(character, characterPageLinkWithRelationName1, characterRelationCacheKey1) >> null
		1 * characterRelationFactoryMock.create(character, characterPageLinkWithRelationName2, characterRelationCacheKey1) >> characterRelation1
		1 * characterLinkExtendedRelationsExtractorMock.extract(WIKITEXT_2) >> Lists.newArrayList(characterPageLinkWithRelationName3)
		1 * characterRelationFactoryMock.create(character, characterPageLinkWithRelationName3, characterRelationCacheKey3) >> characterRelation2
		1 * characterRelationRepositoryMock.linkAndSave(_ as List) >> { args ->
			List<CharacterRelation> characterRelationList = args[0]
			assert characterRelationList.size() == 2
			assert characterRelationList.contains(characterRelation1)
			assert characterRelationList.contains(characterRelation2)
		}
		0 * _
	}

}
