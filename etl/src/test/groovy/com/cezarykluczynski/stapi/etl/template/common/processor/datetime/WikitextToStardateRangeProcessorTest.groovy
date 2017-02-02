package com.cezarykluczynski.stapi.etl.template.common.processor.datetime

import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.StardateRange
import spock.lang.Specification
import spock.lang.Unroll

class WikitextToStardateRangeProcessorTest extends Specification {

	private WikitextToStardateRangeProcessor wikitextToStardateRangeProcessor

	void setup() {
		wikitextToStardateRangeProcessor = new WikitextToStardateRangeProcessor()
	}

	@Unroll('when #text is passed, #stardateRange is returned')
	void "parses various text values into stardateRange"() {
		expect:
		stardateRange == wikitextToStardateRangeProcessor.process(text)

		where:
		text                      | stardateRange
		''                        | null
		'"18:09.2" to "72:35.3"'  | null
		'7413.2 to 7911.8'        | StardateRange.of(7413.2F, 7911.8F)
		'Begins on 47268.4'       | StardateRange.of(47268.4F, null)
		'ends on 53181.9'         | StardateRange.of(null, 53181.9F)
		'48987.3'                 | StardateRange.of(48987.3F, 48987.3F)
		'8812.1-8854.3 / 48015.1' | StardateRange.of(8812.1F, 48015.1F)
		'around 48501.9'          | null
	}
}
