package com.cezarykluczynski.stapi.etl.location.creation.service

import spock.lang.Specification

class LocationNameFilterTest extends Specification {

	private LocationNameFilter locationNameFilter

	void setup() {
		locationNameFilter = new LocationNameFilter()
	}

	void "returns false match for not a location"() {
		expect:
		locationNameFilter.isLocation('Baldwin') == LocationNameFilter.Match.IS_NOT_A_LOCATION
	}

	void "returns unknown match for entity that is not listed as not a location"() {
		expect:
		locationNameFilter.isLocation('Not a real title') == LocationNameFilter.Match.UNKNOWN_RESULT
	}

}
