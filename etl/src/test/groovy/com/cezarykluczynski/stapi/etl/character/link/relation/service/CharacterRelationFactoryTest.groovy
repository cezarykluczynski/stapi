package com.cezarykluczynski.stapi.etl.character.link.relation.service

import com.cezarykluczynski.stapi.etl.character.common.dto.CharacterRelationCacheKey
import com.cezarykluczynski.stapi.etl.character.link.relation.dto.CharacterPageLinkWithRelationName
import com.cezarykluczynski.stapi.etl.character.link.relation.service.normalization.CharacterRelationNormalizationService
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.character.entity.CharacterRelation
import com.cezarykluczynski.stapi.model.character.repository.CharacterRepository
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.google.common.collect.Lists
import spock.lang.Specification

class CharacterRelationFactoryTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final String TITLE_AFTER_REDIRECT = 'TITLE_AFTER_REDIRECT'
	private static final String RELATION_NAME_RAW = 'RELATION_NAME_RAW'
	private static final String RELATION_NAME = 'RELATION_NAME'

	private CharacterRepository characterRepositoryMock

	private PageApi pageApiMock

	private CharacterRelationsSidebarTemplateMappingsProvider characterRelationsSidebarTemplateMappingsProviderMock

	private CharacterRelationNormalizationService characterRelationNormalizationServiceMock

	private CharacterRelationFactory characterRelationFactory

	void setup() {
		characterRepositoryMock = Mock()
		pageApiMock = Mock()
		characterRelationsSidebarTemplateMappingsProviderMock = Mock()
		characterRelationNormalizationServiceMock = Mock()
		characterRelationFactory = new CharacterRelationFactory(characterRepositoryMock, pageApiMock,
				characterRelationsSidebarTemplateMappingsProviderMock, characterRelationNormalizationServiceMock)
	}

	void "creates character relation with character found in database, and relation taken from sidebar template mapper"() {
		given:
		PageLink pageLink = new PageLink(title: TITLE)
		Character target = new Character()
		CharacterPageLinkWithRelationName characterPageLinkWithRelationName = CharacterPageLinkWithRelationName.of(pageLink, null)
		Character source = Mock()

		CharacterRelationCacheKey characterRelationCacheKey = Mock()

		when:
		CharacterRelation characterRelation = characterRelationFactory.create(target, characterPageLinkWithRelationName, characterRelationCacheKey)

		then:
		1 * characterRepositoryMock.findByPageTitleAndPageMediaWikiSource(TITLE, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(source)
		1 * characterRelationsSidebarTemplateMappingsProviderMock.provideFor(characterRelationCacheKey) >> RELATION_NAME
		0 * _
		characterRelation.source == source
		characterRelation.target == target
		characterRelation.type == RELATION_NAME
	}

	void "creates character relation with character found in database, and relation taken from normalization service"() {
		given:
		PageLink pageLink = new PageLink(title: TITLE)
		Character target = new Character()
		CharacterPageLinkWithRelationName characterPageLinkWithRelationName = CharacterPageLinkWithRelationName.of(pageLink, RELATION_NAME_RAW)
		Character source = Mock()

		CharacterRelationCacheKey characterRelationCacheKey = Mock()

		when:
		CharacterRelation characterRelation = characterRelationFactory.create(target, characterPageLinkWithRelationName, characterRelationCacheKey)

		then:
		1 * characterRepositoryMock.findByPageTitleAndPageMediaWikiSource(TITLE, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(source)
		1 * characterRelationNormalizationServiceMock.normalize(characterRelationCacheKey, RELATION_NAME_RAW) >> RELATION_NAME
		0 * _
		characterRelation.source == source
		characterRelation.target == target
		characterRelation.type == RELATION_NAME
	}

	void "creates character relation with character found in API, and relation taken from sidebar template mapper"() {
		given:
		PageLink pageLink = new PageLink(title: TITLE)
		Character target = new Character()
		CharacterPageLinkWithRelationName characterPageLinkWithRelationName = CharacterPageLinkWithRelationName.of(pageLink, null)
		Character source = Mock()
		PageHeader pageHeader = Mock()
		Page page = new Page(title: TITLE_AFTER_REDIRECT, redirectPath: Lists.newArrayList(pageHeader))

		CharacterRelationCacheKey characterRelationCacheKey = Mock()

		when:
		CharacterRelation characterRelation = characterRelationFactory.create(target, characterPageLinkWithRelationName, characterRelationCacheKey)

		then:
		1 * characterRepositoryMock.findByPageTitleAndPageMediaWikiSource(TITLE, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.empty()
		1 * pageApiMock.getPage(TITLE, com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource.MEMORY_ALPHA_EN) >> page
		1 * characterRepositoryMock.findByPageTitleAndPageMediaWikiSource(TITLE_AFTER_REDIRECT, MediaWikiSource.MEMORY_ALPHA_EN) >>
				Optional.of(source)
		1 * characterRelationsSidebarTemplateMappingsProviderMock.provideFor(characterRelationCacheKey) >> RELATION_NAME
		0 * _
		characterRelation.source == source
		characterRelation.target == target
		characterRelation.type == RELATION_NAME
	}

	void "creates character relation with character found in API, and relation taken from normalization service"() {
		given:
		PageLink pageLink = new PageLink(title: TITLE)
		Character target = new Character()
		CharacterPageLinkWithRelationName characterPageLinkWithRelationName = CharacterPageLinkWithRelationName.of(pageLink, RELATION_NAME_RAW)
		Character source = Mock()
		PageHeader pageHeader = Mock()
		Page page = new Page(title: TITLE_AFTER_REDIRECT, redirectPath: Lists.newArrayList(pageHeader))

		CharacterRelationCacheKey characterRelationCacheKey = Mock()

		when:
		CharacterRelation characterRelation = characterRelationFactory.create(target, characterPageLinkWithRelationName, characterRelationCacheKey)

		then:
		1 * characterRepositoryMock.findByPageTitleAndPageMediaWikiSource(TITLE, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.empty()
		1 * pageApiMock.getPage(TITLE, com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource.MEMORY_ALPHA_EN) >> page
		1 * characterRepositoryMock.findByPageTitleAndPageMediaWikiSource(TITLE_AFTER_REDIRECT, MediaWikiSource.MEMORY_ALPHA_EN) >>
				Optional.of(source)
		1 * characterRelationNormalizationServiceMock.normalize(characterRelationCacheKey, RELATION_NAME_RAW) >> RELATION_NAME
		0 * _
		characterRelation.source == source
		characterRelation.target == target
		characterRelation.type == RELATION_NAME
	}

	void "returns null when character cannot be found found in API"() {
		given:
		PageLink pageLink = new PageLink(title: TITLE)
		Character target = new Character()
		CharacterPageLinkWithRelationName characterPageLinkWithRelationName = CharacterPageLinkWithRelationName.of(pageLink, RELATION_NAME_RAW)

		CharacterRelationCacheKey characterRelationCacheKey = Mock()

		when:
		CharacterRelation characterRelation = characterRelationFactory.create(target, characterPageLinkWithRelationName, characterRelationCacheKey)

		then:
		1 * characterRepositoryMock.findByPageTitleAndPageMediaWikiSource(TITLE, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.empty()
		1 * pageApiMock.getPage(TITLE, com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource.MEMORY_ALPHA_EN) >> null
		0 * _
		characterRelation == null
	}

	void "returns null when page found in API is not a redirect"() {
		given:
		PageLink pageLink = new PageLink(title: TITLE)
		Character target = new Character()
		CharacterPageLinkWithRelationName characterPageLinkWithRelationName = CharacterPageLinkWithRelationName.of(pageLink, RELATION_NAME_RAW)
		Page page = new Page(title: TITLE_AFTER_REDIRECT, redirectPath: Lists.newArrayList())

		CharacterRelationCacheKey characterRelationCacheKey = Mock()

		when:
		CharacterRelation characterRelation = characterRelationFactory.create(target, characterPageLinkWithRelationName, characterRelationCacheKey)

		then:
		1 * characterRepositoryMock.findByPageTitleAndPageMediaWikiSource(TITLE, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.empty()
		1 * pageApiMock.getPage(TITLE, com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource.MEMORY_ALPHA_EN) >> page
		0 * _
		characterRelation == null
	}

}
