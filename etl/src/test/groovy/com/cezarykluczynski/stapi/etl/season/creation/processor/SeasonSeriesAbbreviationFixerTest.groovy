package com.cezarykluczynski.stapi.etl.season.creation.processor

import spock.lang.Specification

class SeasonSeriesAbbreviationFixerTest extends Specification {

	SeasonSeriesAbbreviationFixer seasonSeriesAbbreviationFixer

	void setup() {
		seasonSeriesAbbreviationFixer = new SeasonSeriesAbbreviationFixer()
	}

	void "should fix SA name"() {
		expect:
		seasonSeriesAbbreviationFixer.fix('SA') == 'SFA'
	}

	void "should pass others unchanged"() {
		expect:
		seasonSeriesAbbreviationFixer.fix('TNG') == 'TNG'
		seasonSeriesAbbreviationFixer.fix('TOS') == 'TOS'
		seasonSeriesAbbreviationFixer.fix('DS9') == 'DS9'
	}

}
