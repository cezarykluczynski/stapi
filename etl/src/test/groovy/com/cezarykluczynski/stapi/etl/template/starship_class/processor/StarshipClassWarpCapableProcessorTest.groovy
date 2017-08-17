package com.cezarykluczynski.stapi.etl.template.starship_class.processor

import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import com.google.common.collect.Lists
import spock.lang.Specification

class StarshipClassWarpCapableProcessorTest extends Specification {

	private static final String WIKITEXT = 'WIKITEXT'

	private WikitextApi wikitextApiMock

	private StarshipClassWarpCapableProcessor starshipClassWarpCapableProcessor

	void setup() {
		wikitextApiMock = Mock()
		starshipClassWarpCapableProcessor = new StarshipClassWarpCapableProcessor(wikitextApiMock)
	}

	void "when page title matches any of warp-related titles, true is returned"() {
		when:
		Boolean warpCapable = starshipClassWarpCapableProcessor.process(WIKITEXT)

		then:
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT) >> Lists
				.newArrayList(RandomUtil.randomItem(Lists.newArrayList(StarshipClassWarpCapableProcessor.WARP_PAGE_TITLES)))
		0 * _
		warpCapable
	}

	void "when page title does not match warp-related title, false is returned"() {
		when:
		Boolean warpCapable = starshipClassWarpCapableProcessor.process(WIKITEXT)

		then:
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT) >> Lists.newArrayList('not', 'related')
		0 * _
		!warpCapable
	}

}
