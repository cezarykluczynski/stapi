package com.cezarykluczynski.stapi.etl.comic_series.link.processor

import com.cezarykluczynski.stapi.etl.common.mapper.MediaWikiSourceMapper
import com.cezarykluczynski.stapi.etl.template.comic_series.dto.ComicSeriesTemplateParameter
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries
import com.cezarykluczynski.stapi.model.comic_series.repository.ComicSeriesRepository
import com.cezarykluczynski.stapi.model.page.entity.Page as ModelPage
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource as ModelMediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource as SourcesMediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page as SourcesPage
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.google.common.collect.Lists
import spock.lang.Specification

class ComicSeriesLinkProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final String PARENT_SERIES_WIKITEXT = 'PARENT_SERIES_WIKITEXT'
	private static final String PARENT_SERIES_TITLE = 'PARENT_SERIES_TITLE'
	private static final String PARENT_SERIES_TITLE_AFTER_REDIRECT = 'PARENT_SERIES_TITLE_AFTER_REDIRECT'
	private static final ModelMediaWikiSource MODEL_MEDIA_WIKI_SOURCE = ModelMediaWikiSource.MEMORY_ALPHA_EN
	private static final SourcesMediaWikiSource SOURCES_MEDIA_WIKI_SOURCE = SourcesMediaWikiSource.MEMORY_ALPHA_EN

	private MediaWikiSourceMapper mediaWikiSourceMapperMock

	private PageApi pageApiMock

	private TemplateFinder templateFinderMock

	private WikitextApi wikitextApiMock

	private ComicSeriesRepository comicSeriesRepositoryMock

	private ComicSeriesLinkProcessor comicSeriesLinkProcessor

	void setup() {
		mediaWikiSourceMapperMock = Mock()
		pageApiMock = Mock()
		templateFinderMock = Mock()
		wikitextApiMock = Mock()
		comicSeriesRepositoryMock = Mock()
		comicSeriesLinkProcessor = new ComicSeriesLinkProcessor(mediaWikiSourceMapperMock, pageApiMock, templateFinderMock, wikitextApiMock,
				comicSeriesRepositoryMock)
	}

	void "when page for comic series is not found, no further processing is made"() {
		given:
		ComicSeries comicSeries = new ComicSeries(page: new ModelPage(
				mediaWikiSource: MODEL_MEDIA_WIKI_SOURCE,
				title: TITLE))

		when:
		comicSeriesLinkProcessor.process(comicSeries)

		then:
		1 * mediaWikiSourceMapperMock.fromEntityToSources(MODEL_MEDIA_WIKI_SOURCE) >> SOURCES_MEDIA_WIKI_SOURCE
		1 * pageApiMock.getPage(TITLE, SOURCES_MEDIA_WIKI_SOURCE) >> null
		0 * _
		comicSeries.parentSeries.empty
	}

	void "when page for comic series is found, but no sidebar comic series template is found, no further processing is made"() {
		given:
		ComicSeries comicSeries = new ComicSeries(page: new ModelPage(
				mediaWikiSource: MODEL_MEDIA_WIKI_SOURCE,
				title: TITLE))
		SourcesPage sourcesPage = new SourcesPage()

		when:
		comicSeriesLinkProcessor.process(comicSeries)

		then:
		1 * mediaWikiSourceMapperMock.fromEntityToSources(MODEL_MEDIA_WIKI_SOURCE) >> SOURCES_MEDIA_WIKI_SOURCE
		1 * pageApiMock.getPage(TITLE, SOURCES_MEDIA_WIKI_SOURCE) >> sourcesPage
		1 * templateFinderMock.findTemplate(sourcesPage, TemplateTitle.SIDEBAR_COMIC_SERIES) >> Optional.empty()
		0 * _
		comicSeries.parentSeries.empty
	}

	void "gets parent series from side comic series template"() {
		given:
		ComicSeries comicSeries = new ComicSeries(page: new ModelPage(
				mediaWikiSource: MODEL_MEDIA_WIKI_SOURCE,
				title: TITLE))
		SourcesPage sourcesPage = new SourcesPage()
		Template sidebarComicSeriesTemplate = new Template(parts: Lists.newArrayList(
				new Template.Part(
						key: ComicSeriesTemplateParameter.SERIES,
						value: PARENT_SERIES_WIKITEXT)
		))
		ComicSeries parentComicSeries = Mock()

		when:
		comicSeriesLinkProcessor.process(comicSeries)

		then:
		1 * mediaWikiSourceMapperMock.fromEntityToSources(MODEL_MEDIA_WIKI_SOURCE) >> SOURCES_MEDIA_WIKI_SOURCE
		1 * pageApiMock.getPage(TITLE, SOURCES_MEDIA_WIKI_SOURCE) >> sourcesPage
		1 * templateFinderMock.findTemplate(sourcesPage, TemplateTitle.SIDEBAR_COMIC_SERIES) >> Optional.of(sidebarComicSeriesTemplate)
		1 * wikitextApiMock.getPageTitlesFromWikitext(PARENT_SERIES_WIKITEXT) >> Lists.newArrayList(PARENT_SERIES_TITLE)
		1 * comicSeriesRepositoryMock.findByPageTitleAndPageMediaWikiSource(PARENT_SERIES_TITLE, MODEL_MEDIA_WIKI_SOURCE) >> Optional
				.of(parentComicSeries)
		0 * _
		comicSeries.parentSeries.size() == 1
		comicSeries.parentSeries.contains parentComicSeries
	}

	void "gets parent series from side comic series template, when page is a redirect"() {
		given:
		ComicSeries comicSeries = new ComicSeries(page: new ModelPage(
				mediaWikiSource: MODEL_MEDIA_WIKI_SOURCE,
				title: TITLE))
		SourcesPage sourcesPage = new SourcesPage()
		Template sidebarComicSeriesTemplate = new Template(parts: Lists.newArrayList(
				new Template.Part(
						key: ComicSeriesTemplateParameter.SERIES,
						value: PARENT_SERIES_WIKITEXT)
		))
		SourcesPage parentSourcesPage = new SourcesPage(title: PARENT_SERIES_TITLE_AFTER_REDIRECT)
		ComicSeries parentComicSeries = Mock()

		when:
		comicSeriesLinkProcessor.process(comicSeries)

		then:
		1 * mediaWikiSourceMapperMock.fromEntityToSources(MODEL_MEDIA_WIKI_SOURCE) >> SOURCES_MEDIA_WIKI_SOURCE
		1 * pageApiMock.getPage(TITLE, SOURCES_MEDIA_WIKI_SOURCE) >> sourcesPage
		1 * templateFinderMock.findTemplate(sourcesPage, TemplateTitle.SIDEBAR_COMIC_SERIES) >> Optional.of(sidebarComicSeriesTemplate)
		1 * wikitextApiMock.getPageTitlesFromWikitext(PARENT_SERIES_WIKITEXT) >> Lists.newArrayList(PARENT_SERIES_TITLE)
		1 * comicSeriesRepositoryMock.findByPageTitleAndPageMediaWikiSource(PARENT_SERIES_TITLE, MODEL_MEDIA_WIKI_SOURCE) >> Optional.empty()
		1 * mediaWikiSourceMapperMock.fromEntityToSources(MODEL_MEDIA_WIKI_SOURCE) >> SOURCES_MEDIA_WIKI_SOURCE
		1 * pageApiMock.getPage(PARENT_SERIES_TITLE, SOURCES_MEDIA_WIKI_SOURCE) >> parentSourcesPage
		1 * comicSeriesRepositoryMock.findByPageTitleAndPageMediaWikiSource(PARENT_SERIES_TITLE_AFTER_REDIRECT, MODEL_MEDIA_WIKI_SOURCE) >>
				Optional.of(parentComicSeries)
		0 * _
		comicSeries.parentSeries.size() == 1
		comicSeries.parentSeries.contains parentComicSeries
	}

}
