package com.cezarykluczynski.stapi.etl.client

import com.cezarykluczynski.stapi.etl.genderize.client.GenderizeClientNoopImpl
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
