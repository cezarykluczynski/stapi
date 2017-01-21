package com.cezarykluczynski.stapi.sources.genderize.client

import spock.lang.Specification

class GenderizeClientNoopImplTest extends Specification {

	private GenderizeClientNoopImpl genderizeClientNoopImpl

	void setup() {
		genderizeClientNoopImpl = new GenderizeClientNoopImpl()
	}

	void "returns null"() {
		expect:
		genderizeClientNoopImpl.getNameGender('') == null
	}

}
