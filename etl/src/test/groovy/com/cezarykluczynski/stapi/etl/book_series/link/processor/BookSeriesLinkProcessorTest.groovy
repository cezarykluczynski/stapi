package com.cezarykluczynski.stapi.etl.book_series.link.processor

import com.cezarykluczynski.stapi.etl.common.mapper.MediaWikiSourceMapper
import com.cezarykluczynski.stapi.etl.template.book_series.dto.BookSeriesTemplateParameter
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.model.book_series.entity.BookSeries
import com.cezarykluczynski.stapi.model.book_series.repository.BookSeriesRepository
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource as ModelMediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource as SourcesMediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page as SourcesPage
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.google.common.collect.Lists
import spock.lang.Specification

class BookSeriesLinkProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final String PARENT_SERIES_WIKITEXT = 'PARENT_SERIES_WIKITEXT'
	private static final String PARENT_SERIES_TITLE = 'PARENT_SERIES_TITLE'
	private static final String PARENT_SERIES_TITLE_AFTER_REDIRECT = 'PARENT_SERIES_TITLE_AFTER_REDIRECT'
	private static final ModelMediaWikiSource MODEL_MEDIA_WIKI_SOURCE = ModelMediaWikiSource.MEMORY_ALPHA_EN
	private static
	final SourcesMediaWikiSource SOURCES_MEDIA_WIKI_SOURCE = SourcesMediaWikiSource.MEMORY_ALPHA_EN

	private MediaWikiSourceMapper mediaWikiSourceMapperMock

	private PageApi pageApiMock

	private TemplateFinder templateFinderMock

	private WikitextApi wikitextApiMock

	private BookSeriesRepository bookSeriesRepositoryMock

	private BookSeriesLinkProcessor bookSeriesLinkProcessor

	void setup() {
		mediaWikiSourceMapperMock = Mock()
		pageApiMock = Mock()
		templateFinderMock = Mock()
		wikitextApiMock = Mock()
		bookSeriesRepositoryMock = Mock()
		bookSeriesLinkProcessor = new BookSeriesLinkProcessor(mediaWikiSourceMapperMock, pageApiMock, templateFinderMock, wikitextApiMock,
				bookSeriesRepositoryMock)
	}

	void "when page for book series is not found, no further processing is made"() {
		given:
		BookSeries bookSeries = new BookSeries(page: new Page(
				mediaWikiSource: MODEL_MEDIA_WIKI_SOURCE,
				title: TITLE))

		when:
		bookSeriesLinkProcessor.process(bookSeries)

		then:
		1 * mediaWikiSourceMapperMock.fromEntityToSources(MODEL_MEDIA_WIKI_SOURCE) >> SOURCES_MEDIA_WIKI_SOURCE
		1 * pageApiMock.getPage(TITLE, SOURCES_MEDIA_WIKI_SOURCE) >> null
		0 * _
		bookSeries.parentSeries.empty
	}

	void "when page for book series is found, but no sidebar book series template is found, no further processing is made"() {
		given:
		BookSeries bookSeries = new BookSeries(page: new Page(
				mediaWikiSource: MODEL_MEDIA_WIKI_SOURCE,
				title: TITLE))
		SourcesPage sourcesPage = new SourcesPage()

		when:
		bookSeriesLinkProcessor.process(bookSeries)

		then:
		1 * mediaWikiSourceMapperMock.fromEntityToSources(MODEL_MEDIA_WIKI_SOURCE) >> SOURCES_MEDIA_WIKI_SOURCE
		1 * pageApiMock.getPage(TITLE, SOURCES_MEDIA_WIKI_SOURCE) >> sourcesPage
		1 * templateFinderMock.findTemplate(sourcesPage, TemplateTitle.SIDEBAR_NOVEL_SERIES) >> Optional.empty()
		0 * _
		bookSeries.parentSeries.empty
	}

	void "gets parent series from side book series template"() {
		given:
		BookSeries bookSeries = new BookSeries(page: new Page(
				mediaWikiSource: MODEL_MEDIA_WIKI_SOURCE,
				title: TITLE))
		SourcesPage sourcesPage = new SourcesPage()
		Template sidebarBookSeriesTemplate = new Template(parts: Lists.newArrayList(
				new Template.Part(
						key: BookSeriesTemplateParameter.SERIES,
						value: PARENT_SERIES_WIKITEXT)
		))
		BookSeries parentBookSeries = Mock()

		when:
		bookSeriesLinkProcessor.process(bookSeries)

		then:
		1 * mediaWikiSourceMapperMock.fromEntityToSources(MODEL_MEDIA_WIKI_SOURCE) >> SOURCES_MEDIA_WIKI_SOURCE
		1 * pageApiMock.getPage(TITLE, SOURCES_MEDIA_WIKI_SOURCE) >> sourcesPage
		1 * templateFinderMock.findTemplate(sourcesPage, TemplateTitle.SIDEBAR_NOVEL_SERIES) >> Optional.of(sidebarBookSeriesTemplate)
		1 * wikitextApiMock.getPageTitlesFromWikitext(PARENT_SERIES_WIKITEXT) >> Lists.newArrayList(PARENT_SERIES_TITLE)
		1 * bookSeriesRepositoryMock.findByPageTitleAndPageMediaWikiSource(PARENT_SERIES_TITLE, MODEL_MEDIA_WIKI_SOURCE) >> Optional
				.of(parentBookSeries)
		0 * _
		bookSeries.parentSeries.size() == 1
		bookSeries.parentSeries.contains parentBookSeries
	}

	void "gets parent series from side book series template, when page is a redirect"() {
		given:
		BookSeries bookSeries = new BookSeries(page: new Page(
				mediaWikiSource: MODEL_MEDIA_WIKI_SOURCE,
				title: TITLE))
		SourcesPage sourcesPage = new SourcesPage()
		Template sidebarBookSeriesTemplate = new Template(parts: Lists.newArrayList(
				new Template.Part(
						key: BookSeriesTemplateParameter.SERIES,
						value: PARENT_SERIES_WIKITEXT)
		))
		SourcesPage parentSourcesPage = new SourcesPage(title: PARENT_SERIES_TITLE_AFTER_REDIRECT)
		BookSeries parentBookSeries = Mock()

		when:
		bookSeriesLinkProcessor.process(bookSeries)

		then:
		1 * mediaWikiSourceMapperMock.fromEntityToSources(MODEL_MEDIA_WIKI_SOURCE) >> SOURCES_MEDIA_WIKI_SOURCE
		1 * pageApiMock.getPage(TITLE, SOURCES_MEDIA_WIKI_SOURCE) >> sourcesPage
		1 * templateFinderMock.findTemplate(sourcesPage, TemplateTitle.SIDEBAR_NOVEL_SERIES) >> Optional.of(sidebarBookSeriesTemplate)
		1 * wikitextApiMock.getPageTitlesFromWikitext(PARENT_SERIES_WIKITEXT) >> Lists.newArrayList(PARENT_SERIES_TITLE)
		1 * bookSeriesRepositoryMock.findByPageTitleAndPageMediaWikiSource(PARENT_SERIES_TITLE, MODEL_MEDIA_WIKI_SOURCE) >> Optional.empty()
		1 * mediaWikiSourceMapperMock.fromEntityToSources(MODEL_MEDIA_WIKI_SOURCE) >> SOURCES_MEDIA_WIKI_SOURCE
		1 * pageApiMock.getPage(PARENT_SERIES_TITLE, SOURCES_MEDIA_WIKI_SOURCE) >> parentSourcesPage
		1 * bookSeriesRepositoryMock.findByPageTitleAndPageMediaWikiSource(PARENT_SERIES_TITLE_AFTER_REDIRECT, MODEL_MEDIA_WIKI_SOURCE) >>
				Optional.of(parentBookSeries)
		0 * _
		bookSeries.parentSeries.size() == 1
		bookSeries.parentSeries.contains parentBookSeries
	}

}
