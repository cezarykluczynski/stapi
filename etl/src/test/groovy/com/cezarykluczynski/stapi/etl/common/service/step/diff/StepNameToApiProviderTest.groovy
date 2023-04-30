package com.cezarykluczynski.stapi.etl.common.service.step.diff

import com.cezarykluczynski.stapi.client.rest.facade.AstronomicalObject
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import spock.lang.Specification

class StepNameToApiProviderTest extends Specification {

	StepNameToApiProvider stepNameToApiProvider

	void setup() {
		stepNameToApiProvider = new StepNameToApiProvider()
	}

	void "gets field for supported step"() {
		expect:
		stepNameToApiProvider.provide(StepName.CREATE_ASTRONOMICAL_OBJECTS).type == AstronomicalObject
	}

	void "gets null for non-supported step"() {
		stepNameToApiProvider.provide(StepName.LINK_ASTRONOMICAL_OBJECTS) == null
	}

}
