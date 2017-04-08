package com.cezarykluczynski.stapi.etl.template.comicSeries.processor

import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.google.common.collect.Lists
import spock.lang.Specification

class ComicSeriesTemplateMiniseriesProcessorTest extends Specification {

	private static final String SERIES = 'SERIES'

	private WikitextApi wikitextApiMock

	private ComicSeriesTemplateMiniseriesProcessor comicSeriesTemplateMiniseriesProcessor

	void setup() {
		wikitextApiMock = Mock()
		comicSeriesTemplateMiniseriesProcessor = new ComicSeriesTemplateMiniseriesProcessor(wikitextApiMock)
	}

	void "returns true when WikitextApi finds links in text"() {
		when:
		Boolean miniseries = comicSeriesTemplateMiniseriesProcessor.process(SERIES)

		then:
		1 * wikitextApiMock.getPageTitlesFromWikitext(SERIES) >> Lists.newArrayList('Link')
		0 * _
		miniseries
	}

	void "returns false when WikitextApi does not find links in text"() {
		when:
		Boolean miniseries = comicSeriesTemplateMiniseriesProcessor.process(SERIES)

		then:
		1 * wikitextApiMock.getPageTitlesFromWikitext(SERIES) >> Lists.newArrayList()
		0 * _
		!miniseries
	}

}
