package com.cezarykluczynski.stapi.etl.common.processor.magazine_series

import com.cezarykluczynski.stapi.model.magazine_series.entity.MagazineSeries
import com.cezarykluczynski.stapi.model.magazine_series.repository.MagazineSeriesRepository
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.google.common.collect.Lists
import spock.lang.Specification

class WikitextToMagazineSeriesProcessorTest extends Specification {

	private static final MediaWikiSource SOURCE = MediaWikiSource.MEMORY_ALPHA_EN
	private static final String LINK_TITLE_1 = 'LINK_TITLE_1'
	private static final String LINK_TITLE_2 = 'LINK_TITLE_2'
	private static final String WIKITEXT = 'WIKITEXT'

	private WikitextApi wikitextApiMock

	private MagazineSeriesRepository magazineSeriesRepositoryMock

	private WikitextToMagazineSeriesProcessor wikitextToMagazineSeriesProcessor

	void setup() {
		wikitextApiMock = Mock()
		magazineSeriesRepositoryMock = Mock()
		wikitextToMagazineSeriesProcessor = new WikitextToMagazineSeriesProcessor(wikitextApiMock, magazineSeriesRepositoryMock)
	}

	void "given wikitext, gets links from it, then returns magazine series that have associated pages with matching titles"() {
		given:
		MagazineSeries magazineSeries = Mock()

		when:
		Set<MagazineSeries> magazineSeriesSet = wikitextToMagazineSeriesProcessor.process(WIKITEXT)

		then:
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT) >> Lists.newArrayList(LINK_TITLE_1, LINK_TITLE_2)
		1 * magazineSeriesRepositoryMock.findByPageTitleAndPageMediaWikiSource(LINK_TITLE_1, SOURCE) >> Optional.of(magazineSeries)
		1 * magazineSeriesRepositoryMock.findByPageTitleAndPageMediaWikiSource(LINK_TITLE_2, SOURCE) >> Optional.empty()
		0 * _
		magazineSeriesSet.size() == 1
		magazineSeriesSet.contains magazineSeries
	}

}
