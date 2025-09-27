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

	void "returns null for specials seasons"() {
		expect:
		seasonNumberProcessor.process('The Ready Room Prodigy Specials') == null
	}

	void "returns null for LD season"() {
		expect:
		seasonNumberProcessor.process('TRR Season LD') == null
	}

	void "returns null for PRO season"() {
		expect:
		seasonNumberProcessor.process('TRR Season PRO') == null
	}

	void "returns null for SNW season"() {
		expect:
		seasonNumberProcessor.process('TRR Season SNW') == null
	}

	void "extracts season number"() {
		expect:
		seasonNumberProcessor.process('TNG Season 4') == 4
	}

}
