package com.cezarykluczynski.stapi.etl.common.processor

import com.cezarykluczynski.stapi.model.common.service.RepositoryProvider
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource
import com.cezarykluczynski.stapi.model.title.entity.Title
import com.cezarykluczynski.stapi.model.title.repository.TitleRepository
import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.google.common.collect.Lists
import com.google.common.collect.Maps
import org.springframework.data.repository.CrudRepository
import spock.lang.Specification

class WikitextToEntitiesGenericProcessorTest extends Specification {

	private static final String WIKITEXT = 'WIKITEXT'
	private static final String PAGE_TITLE_1 = 'PAGE_TITLE_1'
	private static final String PAGE_TITLE_2 = 'PAGE_TITLE_2'
	private static final String PAGE_TITLE_3 = 'PAGE_TITLE_3'
	private static final String PAGE_TITLE_3_AFTER_REDIRECT = 'PAGE_TITLE_3_AFTER_REDIRECT'
	private static final String PAGE_TITLE_4 = 'PAGE_TITLE_4'
	private static final String PAGE_TITLE_4_AFTER_REDIRECT = 'PAGE_TITLE_4_AFTER_REDIRECT'

	private WikitextApi wikitextApiMock

	private PageApi pageApiMock

	private RepositoryProvider repositoryProviderMock

	private WikitextToEntitiesGenericProcessor wikitextToEntitiesGenericProcessor

	void setup() {
		wikitextApiMock = Mock()
		pageApiMock = Mock()
		repositoryProviderMock = Mock()
		wikitextToEntitiesGenericProcessor = new WikitextToEntitiesGenericProcessor(wikitextApiMock, pageApiMock, repositoryProviderMock)
	}

	void "returns empty list when repository is not a PageAwareRepository"() {
		given:
		CrudRepository crudRepositoryMock = Mock()
		Map<Class, CrudRepository> classCrudRepositoryMap = Maps.newHashMap()
		classCrudRepositoryMap.put(Title, crudRepositoryMock)

		when:
		List<Title> titleList = wikitextToEntitiesGenericProcessor.process(WIKITEXT, Title)

		then:
		1 * repositoryProviderMock.provide() >> classCrudRepositoryMap
		0 * _
		titleList.empty
	}

	void "finds link titles, when searches for them in repository and in api"() {
		given:
		TitleRepository titleRepositoryMock = Mock()
		Map<Class, CrudRepository> classCrudRepositoryMap = Maps.newHashMap()
		classCrudRepositoryMap.put(Title, titleRepositoryMock)
		Title title1 = Mock()
		Title title2 = Mock()
		PageHeader pageHeader = Mock()
		Page page1 = new Page(title: PAGE_TITLE_3_AFTER_REDIRECT, redirectPath: Lists.newArrayList(pageHeader))
		Page page2 = new Page(title: PAGE_TITLE_4_AFTER_REDIRECT)

		when:
		List<Title> titleList = wikitextToEntitiesGenericProcessor.process(WIKITEXT, Title)

		then:
		1 * repositoryProviderMock.provide() >> classCrudRepositoryMap
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT) >> Lists.newArrayList(PAGE_TITLE_1, PAGE_TITLE_2, PAGE_TITLE_3, PAGE_TITLE_4)
		1 * titleRepositoryMock.findByPageTitleAndPageMediaWikiSource(PAGE_TITLE_1, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(title1)
		1 * titleRepositoryMock.findByPageTitleAndPageMediaWikiSource(PAGE_TITLE_2, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.empty()
		1 * pageApiMock.getPage(PAGE_TITLE_2, com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource.MEMORY_ALPHA_EN) >> null
		1 * titleRepositoryMock.findByPageTitleAndPageMediaWikiSource(PAGE_TITLE_3, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.empty()
		1 * pageApiMock.getPage(PAGE_TITLE_3, com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource.MEMORY_ALPHA_EN) >> page1
		1 * titleRepositoryMock.findByPageTitleAndPageMediaWikiSource(PAGE_TITLE_3_AFTER_REDIRECT, MediaWikiSource.MEMORY_ALPHA_EN) >>
				Optional.of(title2)
		1 * titleRepositoryMock.findByPageTitleAndPageMediaWikiSource(PAGE_TITLE_4, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.empty()
		1 * pageApiMock.getPage(PAGE_TITLE_4, com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource.MEMORY_ALPHA_EN) >> page2
		0 * _
		titleList.size() == 2
		titleList.contains title1
		titleList.contains title2
	}

}
