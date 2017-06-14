package com.cezarykluczynski.stapi.etl.template.book_series.processor

import com.cezarykluczynski.stapi.model.book_series.entity.BookSeries
import com.cezarykluczynski.stapi.model.book_series.repository.BookSeriesRepository
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.google.common.collect.Lists
import spock.lang.Specification

class WikitextToBookSeriesProcessorTest extends Specification {

	private static final MediaWikiSource SOURCE = MediaWikiSource.MEMORY_ALPHA_EN
	private static final String LINK_TITLE_1 = 'LINK_TITLE_1'
	private static final String LINK_TITLE_2 = 'LINK_TITLE_2'
	private static final String WIKITEXT = 'WIKITEXT'

	private WikitextApi wikitextApiMock

	private BookSeriesRepository bookSeriesRepositoryMock

	private WikitextToBookSeriesProcessor wikitextToBookSeriesProcessor

	void setup() {
		wikitextApiMock = Mock()
		bookSeriesRepositoryMock = Mock()
		wikitextToBookSeriesProcessor = new WikitextToBookSeriesProcessor(wikitextApiMock, bookSeriesRepositoryMock)
	}

	void "given wikitext, gets links from it, then returns comic series that have associated pages with matching titles"() {
		given:
		BookSeries bookSeries = Mock()

		when:
		Set<BookSeries> bookSeriesSet = wikitextToBookSeriesProcessor.process(WIKITEXT)

		then:
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT) >> Lists.newArrayList(LINK_TITLE_1, LINK_TITLE_2)
		1 * bookSeriesRepositoryMock.findByPageTitleAndPageMediaWikiSource(LINK_TITLE_1, SOURCE) >> Optional.of(bookSeries)
		1 * bookSeriesRepositoryMock.findByPageTitleAndPageMediaWikiSource(LINK_TITLE_2, SOURCE) >> Optional.empty()
		0 * _
		bookSeriesSet.size() == 1
		bookSeriesSet.contains bookSeries
	}

}
