package com.cezarykluczynski.stapi.etl.vendor.genderize.client

import spock.lang.Specification

class GenderizeClientNoopImplTest extends Specification {

	private GenderizeClientNoopImpl genderizeClientNoopImpl

	def setup() {
		genderizeClientNoopImpl = new GenderizeClientNoopImpl()
	}

	def "returns null"() {
		expect:
		genderizeClientNoopImpl.getNameGender("") == null
	}

}
