package com.cezarykluczynski.stapi.etl.template.series.processor

import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.model.series.repository.SeriesRepository
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.google.common.collect.Lists
import spock.lang.Specification

class WikitextToSeriesProcessorTest extends Specification {

	private static final MediaWikiSource SOURCE = MediaWikiSource.MEMORY_ALPHA_EN
	private static final String LINK_TITLE_1 = 'LINK_TITLE_1'
	private static final String LINK_TITLE_2 = 'LINK_TITLE_2'
	private static final String WIKITEXT = 'WIKITEXT'

	private WikitextApi wikitextApiMock

	private SeriesRepository seriesRepositoryMock

	private WikitextToSeriesProcessor wikitextToSeriesProcessor

	void setup() {
		wikitextApiMock = Mock()
		seriesRepositoryMock = Mock()
		wikitextToSeriesProcessor = new WikitextToSeriesProcessor(wikitextApiMock, seriesRepositoryMock)
	}

	void "find series by title"() {
		given:
		Series series = Mock()

		when:
		Series seriesOutput = wikitextToSeriesProcessor.process(WIKITEXT)

		then:
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT) >> Lists.newArrayList(LINK_TITLE_1, LINK_TITLE_2)
		1 * seriesRepositoryMock.findByPageTitleAndPageMediaWikiSource(LINK_TITLE_1, SOURCE) >> Optional.empty()
		1 * seriesRepositoryMock.findByAbbreviation(LINK_TITLE_1) >> Optional.empty()
		1 * seriesRepositoryMock.findByPageTitleAndPageMediaWikiSource(LINK_TITLE_2, SOURCE) >> Optional.of(series)
		0 * _
		seriesOutput == series
	}

	void "find series by abbreviation"() {
		given:
		Series series = Mock()

		when:
		Series seriesOutput = wikitextToSeriesProcessor.process(WIKITEXT)

		then:
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT) >> Lists.newArrayList(LINK_TITLE_1, LINK_TITLE_2)
		1 * seriesRepositoryMock.findByPageTitleAndPageMediaWikiSource(LINK_TITLE_1, SOURCE) >> Optional.empty()
		1 * seriesRepositoryMock.findByAbbreviation(LINK_TITLE_1) >> Optional.empty()
		1 * seriesRepositoryMock.findByPageTitleAndPageMediaWikiSource(LINK_TITLE_2, SOURCE) >> Optional.empty()
		1 * seriesRepositoryMock.findByAbbreviation(LINK_TITLE_2) >> Optional.of(series)
		0 * _
		seriesOutput == series
	}

}
