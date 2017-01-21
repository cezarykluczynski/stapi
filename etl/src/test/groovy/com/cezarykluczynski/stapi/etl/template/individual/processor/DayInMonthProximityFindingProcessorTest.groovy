package com.cezarykluczynski.stapi.etl.template.individual.processor

import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink
import org.apache.commons.lang3.tuple.Pair
import spock.lang.Specification
import spock.lang.Unroll

class DayInMonthProximityFindingProcessorTest extends Specification {

	private DayInMonthProximityFindingProcessor dayInMonthProximityFindingProcessor

	void setup() {
		dayInMonthProximityFindingProcessor = new DayInMonthProximityFindingProcessor()
	}

	@Unroll('when #wikitext and #PageLink is passed, #day is returned')
	void "when wikitext and PageLink is passed, day is returned"() {
		expect:
		dayInMonthProximityFindingProcessor.process(Pair.of(wikitext, pageLink)) == day

		where:
		wikitext       | pageLink                                        | day
		null           | null                                            | null
		'[[July]]'     | new PageLink(startPosition: 0, endPosition: 6)  | null
		'4 [[July]]'   | new PageLink(startPosition: 2, endPosition: 10) | 4
		'3rd [[July]]' | new PageLink(startPosition: 4, endPosition: 12) | 3
		'[[June]] 6'   | new PageLink(startPosition: 0, endPosition: 8)  | 6
		'[[June]] 7th' | new PageLink(startPosition: 0, endPosition: 8)  | 7
	}

}
