package com.cezarykluczynski.stapi.etl.location.creation.service

import spock.lang.Specification

class LocationNameFilterTest extends Specification {

	private LocationNameFilter locationNameFilter

	void setup() {
		locationNameFilter = new LocationNameFilter()
	}

	void "returns false for not a location"() {
		expect:
		!locationNameFilter.isLocation('Democrat')
	}

	void "returns null for entity that is not listed as not a location"() {
		expect:
		locationNameFilter.isLocation('Not a real title') == null
	}

}
