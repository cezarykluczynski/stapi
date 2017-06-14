package com.cezarykluczynski.stapi.etl.common.processor.comic_series

import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries
import com.cezarykluczynski.stapi.model.comic_series.repository.ComicSeriesRepository
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.google.common.collect.Lists
import spock.lang.Specification

class WikitextToComicSeriesProcessorTest extends Specification {

	private static final MediaWikiSource SOURCE = MediaWikiSource.MEMORY_ALPHA_EN
	private static final String LINK_TITLE_1 = 'LINK_TITLE_1'
	private static final String LINK_TITLE_2 = 'LINK_TITLE_2'
	private static final String WIKITEXT = 'WIKITEXT'

	private WikitextApi wikitextApiMock

	private ComicSeriesRepository comicSeriesRepositoryMock

	private WikitextToComicSeriesProcessor wikitextToComicSeriesProcessor

	void setup() {
		wikitextApiMock = Mock()
		comicSeriesRepositoryMock = Mock()
		wikitextToComicSeriesProcessor = new WikitextToComicSeriesProcessor(wikitextApiMock, comicSeriesRepositoryMock)
	}

	void "given wikitext, gets links from it, then returns comic series that have associated pages with matching titles"() {
		given:
		ComicSeries comicSeries = Mock()

		when:
		Set<ComicSeries> comicSeriesSet = wikitextToComicSeriesProcessor.process(WIKITEXT)

		then:
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT) >> Lists.newArrayList(LINK_TITLE_1, LINK_TITLE_2)
		1 * comicSeriesRepositoryMock.findByPageTitleAndPageMediaWikiSource(LINK_TITLE_1, SOURCE) >> Optional.of(comicSeries)
		1 * comicSeriesRepositoryMock.findByPageTitleAndPageMediaWikiSource(LINK_TITLE_2, SOURCE) >> Optional.empty()
		0 * _
		comicSeriesSet.size() == 1
		comicSeriesSet.contains comicSeries
	}

}
