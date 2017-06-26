package com.cezarykluczynski.stapi.etl.season.creation.processor

import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException
import spock.lang.Specification

class SeasonNumberProcessorTest extends Specification {

	private SeasonNumberProcessor seasonNumberProcessor

	void setup() {
		seasonNumberProcessor = new SeasonNumberProcessor()
	}

	void "throws exception when season number could not be extracted"() {
		when:
		seasonNumberProcessor.process('')

		then:
		thrown(StapiRuntimeException)
	}

	void "extracts season number"() {
		when:
		Integer seasonNumber = seasonNumberProcessor.process('TNG Season 4')

		then:
		seasonNumber == 4
	}

}
