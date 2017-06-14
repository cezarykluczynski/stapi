package com.cezarykluczynski.stapi.etl.template.publishable_series.processor

import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.google.common.collect.Lists
import spock.lang.Specification

class PublishableSeriesTemplateMiniseriesProcessorTest extends Specification {

	private static final String SERIES = 'SERIES'

	private WikitextApi wikitextApiMock

	private PublishableSeriesTemplateMiniseriesProcessor publishableSeriesTemplateMiniseriesProcessor

	void setup() {
		wikitextApiMock = Mock()
		publishableSeriesTemplateMiniseriesProcessor = new PublishableSeriesTemplateMiniseriesProcessor(wikitextApiMock)
	}

	void "returns true when WikitextApi finds links in text"() {
		when:
		Boolean miniseries = publishableSeriesTemplateMiniseriesProcessor.process(SERIES)

		then:
		1 * wikitextApiMock.getPageTitlesFromWikitext(SERIES) >> Lists.newArrayList('Link')
		0 * _
		miniseries
	}

	void "returns false when WikitextApi does not find links in text"() {
		when:
		Boolean miniseries = publishableSeriesTemplateMiniseriesProcessor.process(SERIES)

		then:
		1 * wikitextApiMock.getPageTitlesFromWikitext(SERIES) >> Lists.newArrayList()
		0 * _
		!miniseries
	}

}
