package com.cezarykluczynski.stapi.etl.character.link.processor

import com.cezarykluczynski.stapi.etl.character.common.dto.CharacterRelationsMap
import com.cezarykluczynski.stapi.etl.character.common.service.CharactersRelationsCache
import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.mapper.MediaWikiSourceMapper
import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.character.repository.CharacterRepository
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource as ModelMediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource as SourcesMediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException
import spock.lang.Specification

class CharacterLinkProcessorTest extends Specification {

	private static final Long PAGE_ID = 5L
	private static final ModelMediaWikiSource MODEL_MEDIA_WIKI_SOURCE = ModelMediaWikiSource.MEMORY_ALPHA_EN
	private static final SourcesMediaWikiSource SOURCES_MEDIA_WIKI_SOURCE = SourcesMediaWikiSource.MEMORY_ALPHA_EN

	private CharactersRelationsCache charactersRelationsCacheMock

	private CharacterRepository characterRepositoryMock

	private PageHeaderProcessor pageHeaderProcessorMock

	private CharacterLinkCacheStoringProcessor characterLinkCacheStoringProcessorMock

	private CharacterLinkRelationsEnrichingProcessor characterLinkRelationsEnrichingProcessorMock

	private MediaWikiSourceMapper mediaWikiSourceMapperMock

	private CharacterLinkProcessor characterLinkProcessor

	void setup() {
		charactersRelationsCacheMock = Mock()
		characterRepositoryMock = Mock()
		pageHeaderProcessorMock = Mock()
		characterLinkCacheStoringProcessorMock = Mock()
		characterLinkRelationsEnrichingProcessorMock = Mock()
		mediaWikiSourceMapperMock = Mock()
		characterLinkProcessor = new CharacterLinkProcessor(charactersRelationsCacheMock, characterRepositoryMock, pageHeaderProcessorMock,
				characterLinkCacheStoringProcessorMock, characterLinkRelationsEnrichingProcessorMock, mediaWikiSourceMapperMock)
	}

	void "validates once on first call"() {
		when:
		characterLinkProcessor.process(null)

		then:
		1 * charactersRelationsCacheMock.isEmpty() >> false
		0 * _

		when:
		characterLinkProcessor.process(null)

		then:
		0 * _
	}

	void "throws exception when validation is not successful"() {
		when:
		characterLinkProcessor.process(null)

		then:
		1 * charactersRelationsCacheMock.isEmpty() >> true
		1 * characterRepositoryMock.count() >> 0
		0 * _
		thrown(StapiRuntimeException)
	}

	void "when cache is found, it is used, and character is returned"() {
		given:
		PageHeader pageHeader = new PageHeader(
				pageId: PAGE_ID,
				mediaWikiSource: SOURCES_MEDIA_WIKI_SOURCE)
		CharacterRelationsMap characterRelationsMap = new CharacterRelationsMap()
		Character character = new Character()

		when:
		Character characterOutput = characterLinkProcessor.process(pageHeader)

		then:
		1 * charactersRelationsCacheMock.isEmpty() >> false
		1 * charactersRelationsCacheMock.get(PAGE_ID) >> characterRelationsMap
		1 * mediaWikiSourceMapperMock.fromSourcesToEntity(SOURCES_MEDIA_WIKI_SOURCE) >> MODEL_MEDIA_WIKI_SOURCE
		1 * characterRepositoryMock.findByPagePageIdAndPageMediaWikiSource(PAGE_ID, MODEL_MEDIA_WIKI_SOURCE) >> Optional.of(character)
		1 * characterLinkRelationsEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<CharacterRelationsMap, Character> enrichablePair ->
			assert enrichablePair.input == characterRelationsMap
			assert enrichablePair.output == character
		}
		0 * _
		characterOutput == character
	}

	void "when cache is found, but character is not, null is returned"() {
		given:
		PageHeader pageHeader = new PageHeader(
				pageId: PAGE_ID,
				mediaWikiSource: SOURCES_MEDIA_WIKI_SOURCE)
		CharacterRelationsMap characterRelationsMap = new CharacterRelationsMap()

		when:
		Character character = characterLinkProcessor.process(pageHeader)

		then:
		1 * charactersRelationsCacheMock.isEmpty() >> false
		1 * charactersRelationsCacheMock.get(PAGE_ID) >> characterRelationsMap
		1 * mediaWikiSourceMapperMock.fromSourcesToEntity(SOURCES_MEDIA_WIKI_SOURCE) >> MODEL_MEDIA_WIKI_SOURCE
		1 * characterRepositoryMock.findByPagePageIdAndPageMediaWikiSource(PAGE_ID, MODEL_MEDIA_WIKI_SOURCE) >> Optional.empty()
		0 * _
		character == null
	}

	void "when cache can be produced, it is used, and character is returned"() {
		given:
		PageHeader pageHeader = new PageHeader(
				pageId: PAGE_ID,
				mediaWikiSource: SOURCES_MEDIA_WIKI_SOURCE)
		Page page = Mock()
		CharacterRelationsMap characterRelationsMap = new CharacterRelationsMap()
		Character character = new Character()

		when:
		Character characterOutput = characterLinkProcessor.process(pageHeader)

		then:
		1 * charactersRelationsCacheMock.isEmpty() >> false
		1 * charactersRelationsCacheMock.get(PAGE_ID) >> null
		1 * pageHeaderProcessorMock.process(pageHeader) >> page
		1 * characterLinkCacheStoringProcessorMock.process(page) >> characterRelationsMap
		1 * charactersRelationsCacheMock.put(PAGE_ID, characterRelationsMap)
		1 * mediaWikiSourceMapperMock.fromSourcesToEntity(SOURCES_MEDIA_WIKI_SOURCE) >> MODEL_MEDIA_WIKI_SOURCE
		1 * characterRepositoryMock.findByPagePageIdAndPageMediaWikiSource(PAGE_ID, MODEL_MEDIA_WIKI_SOURCE) >> Optional.of(character)
		1 * characterLinkRelationsEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<CharacterRelationsMap, Character> enrichablePair ->
			assert enrichablePair.input == characterRelationsMap
			assert enrichablePair.output == character
		}
		0 * _
		characterOutput == character
	}

	void "when CharacterRelationsMap cannot be produced, null is returned"() {
		given:
		PageHeader pageHeader = new PageHeader(
				pageId: PAGE_ID,
				mediaWikiSource: SOURCES_MEDIA_WIKI_SOURCE)
		Page page = Mock()

		when:
		Character character = characterLinkProcessor.process(pageHeader)

		then:
		1 * charactersRelationsCacheMock.isEmpty() >> false
		1 * charactersRelationsCacheMock.get(PAGE_ID) >> null
		1 * pageHeaderProcessorMock.process(pageHeader) >> page
		1 * characterLinkCacheStoringProcessorMock.process(page) >> null
		0 * _
		character == null
	}

	void "when page cannot be found, null is returned"() {
		given:
		PageHeader pageHeader = new PageHeader(
				pageId: PAGE_ID,
				mediaWikiSource: SOURCES_MEDIA_WIKI_SOURCE)

		when:
		Character character = characterLinkProcessor.process(pageHeader)

		then:
		1 * charactersRelationsCacheMock.isEmpty() >> false
		1 * charactersRelationsCacheMock.get(PAGE_ID) >> null
		1 * pageHeaderProcessorMock.process(pageHeader) >> null
		0 * _
		character == null
	}

}
