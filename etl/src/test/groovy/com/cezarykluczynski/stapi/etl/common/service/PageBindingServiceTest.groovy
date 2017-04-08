package com.cezarykluczynski.stapi.etl.common.service

import com.cezarykluczynski.stapi.etl.common.mapper.MediaWikiSourceMapper
import com.cezarykluczynski.stapi.model.page.entity.Page as PageEntity
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource as ModelMediaWikiSource
import com.cezarykluczynski.stapi.model.page.repository.PageRepository
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource as SourcesMediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import spock.lang.Specification

class PageBindingServiceTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final Long PAGE_ID = 1L
	private static final SourcesMediaWikiSource SOURCES_MEDIA_WIKI_SOURCE = SourcesMediaWikiSource.MEMORY_ALPHA_EN
	private static final ModelMediaWikiSource MODEL_MEDIA_WIKI_SOURCE = ModelMediaWikiSource.MEMORY_ALPHA_EN

	private PageRepository pageRepositoryMock

	private MediaWikiSourceMapper mediaWikiSourceMapperMock

	private PageBindingService pageBindingService

	void setup() {
		pageRepositoryMock = Mock()
		mediaWikiSourceMapperMock = Mock()
		pageBindingService = new PageBindingService(pageRepositoryMock, mediaWikiSourceMapperMock)
	}

	void "returns existing page when it is found using Page"() {
		given:
		PageEntity pageEntity = new PageEntity()

		when:
		PageEntity pageEntityOutput = pageBindingService.fromPageToPageEntity(new Page(
				pageId: PAGE_ID
		))

		then:
		1 * pageRepositoryMock.findByPageId(PAGE_ID) >> Optional.of(pageEntity)
		0 * _
		pageEntityOutput == pageEntity
	}

	void "returns new saved entity when it is not found using Page"() {
		given:
		PageEntity pageEntity = new PageEntity()

		when:
		PageEntity pageEntityOutput = pageBindingService.fromPageToPageEntity(new Page(
				pageId: PAGE_ID,
				title: TITLE,
				mediaWikiSource: SOURCES_MEDIA_WIKI_SOURCE
		))

		then:
		1 * pageRepositoryMock.findByPageId(PAGE_ID) >> Optional.empty()
		1 * mediaWikiSourceMapperMock.fromSourcesToEntity(SOURCES_MEDIA_WIKI_SOURCE) >> MODEL_MEDIA_WIKI_SOURCE
		1 * pageRepositoryMock.save(_ as PageEntity) >> { PageEntity pageEntityInput ->
			pageEntityInput.pageId == PAGE_ID
			pageEntityInput.title == TITLE
			pageEntityInput.mediaWikiSource == MODEL_MEDIA_WIKI_SOURCE
			pageEntity
		}
		0 * _
		pageEntityOutput == pageEntity
	}

	void "returns existing page when it is found using PageHeader"() {
		given:
		PageEntity pageEntity = new PageEntity()

		when:
		PageEntity pageEntityOutput = pageBindingService.fromPageHeaderToPageEntity(new PageHeader(
				pageId: PAGE_ID
		))

		then:
		1 * pageRepositoryMock.findByPageId(PAGE_ID) >> Optional.of(pageEntity)
		0 * _
		pageEntityOutput == pageEntity
	}

	void "returns new saved entity when it is not found using PageHeader"() {
		given:
		PageEntity pageEntity = new PageEntity()

		when:
		PageEntity pageEntityOutput = pageBindingService.fromPageHeaderToPageEntity(new PageHeader(
				pageId: PAGE_ID,
				title: TITLE,
				mediaWikiSource: SOURCES_MEDIA_WIKI_SOURCE
		))

		then:
		1 * pageRepositoryMock.findByPageId(PAGE_ID) >> Optional.empty()
		1 * mediaWikiSourceMapperMock.fromSourcesToEntity(SOURCES_MEDIA_WIKI_SOURCE) >> MODEL_MEDIA_WIKI_SOURCE
		1 * pageRepositoryMock.save(_ as PageEntity) >> { PageEntity pageEntityInput ->
			pageEntityInput.pageId == PAGE_ID
			pageEntityInput.title == TITLE
			pageEntityInput.mediaWikiSource == MODEL_MEDIA_WIKI_SOURCE
			pageEntity
		}
		0 * _
		pageEntityOutput == pageEntity
	}

}
