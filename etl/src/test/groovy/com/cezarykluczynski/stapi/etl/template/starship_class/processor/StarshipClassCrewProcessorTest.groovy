package com.cezarykluczynski.stapi.etl.template.starship_class.processor

import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApiImpl
import spock.lang.Specification
import spock.lang.Unroll

class StarshipClassCrewProcessorTest extends Specification {

	StarshipClassCrewProcessor starshipClassCrewProcessor

	void setup() {
		starshipClassCrewProcessor = new StarshipClassCrewProcessor(new WikitextApiImpl())
	}

	@Unroll('when #value is passed, #crew is returned')
	void "when raw crew value is passed, it is cleansed"() {
		expect:
		starshipClassCrewProcessor.process(value) == crew

		where:
		value                                             | crew
		'2 &ndash; 12 (approx.)'                          | '2 – 12 (approx.)'
		'20 (with command section)<br />None (automated)' | '20 (with command section); None (automated)'
		'At least 3 '                                     | 'At least 3'
		'Up to &asymp;130,000'                            | 'Up to ≈130,000'
		'1 [[Vorta]], 42 [[Jem\'Hadar]]'                  | '1 Vorta, 42 Jem\'Hadar'
	}

}
