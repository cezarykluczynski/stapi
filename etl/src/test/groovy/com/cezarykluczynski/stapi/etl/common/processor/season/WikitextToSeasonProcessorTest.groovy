package com.cezarykluczynski.stapi.etl.common.processor.season

import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource
import com.cezarykluczynski.stapi.model.season.entity.Season
import com.cezarykluczynski.stapi.model.season.repository.SeasonRepository
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.google.common.collect.Lists
import spock.lang.Specification

class WikitextToSeasonProcessorTest extends Specification {

	private static final MediaWikiSource SOURCE = MediaWikiSource.MEMORY_ALPHA_EN
	private static final String LINK_TITLE_1 = 'LINK_TITLE_1'
	private static final String LINK_TITLE_2 = 'LINK_TITLE_2'
	private static final String WIKITEXT = 'WIKITEXT'

	private WikitextApi wikitextApiMock

	private SeasonRepository seasonRepositoryMock

	private WikitextToSeasonProcessor wikitextToSeasonProcessor

	void setup() {
		wikitextApiMock = Mock()
		seasonRepositoryMock = Mock()
		wikitextToSeasonProcessor = new WikitextToSeasonProcessor(wikitextApiMock, seasonRepositoryMock)
	}

	void "given wikitext, gets links from it, then returns seasons that have associated pages with matching titles"() {
		given:
		Season season = Mock()

		when:
		Set<Season> seasonSet = wikitextToSeasonProcessor.process(WIKITEXT)

		then:
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT) >> Lists.newArrayList(LINK_TITLE_1, LINK_TITLE_2)
		1 * seasonRepositoryMock.findByPageTitleAndPageMediaWikiSource(LINK_TITLE_1, SOURCE) >> Optional.of(season)
		1 * seasonRepositoryMock.findByPageTitleAndPageMediaWikiSource(LINK_TITLE_2, SOURCE) >> Optional.empty()
		0 * _
		seasonSet.size() == 1
		seasonSet.contains season
	}

}
