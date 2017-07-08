package com.cezarykluczynski.stapi.etl.configuration.job.properties

import com.cezarykluczynski.stapi.etl.configuration.job.StepConfigurationValidator
import spock.lang.Specification

class StepToStepPropertiesProviderTest extends Specification {

	private StepsProperties stepsPropertiesMock

	private StepToStepPropertiesProvider stepToStepPropertiesProvider

	void setup() {
		stepsPropertiesMock = Mock()
		stepToStepPropertiesProvider = new StepToStepPropertiesProvider(stepsPropertiesMock)
	}

	void "should provide properties"() {
		when:
		Map<String, StepProperties> stepPropertiesMap = stepToStepPropertiesProvider.provide()

		then:
		stepPropertiesMap.size() == StepConfigurationValidator.NUMBER_OF_STEPS
		stepPropertiesMap instanceof LinkedHashMap
	}

}
