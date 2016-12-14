package com.cezarykluczynski.stapi.etl.configuration.job.properties

import com.cezarykluczynski.stapi.etl.configuration.job.StepConfigurationValidator
import spock.lang.Specification

class StepToStepPropertiesProviderTest extends Specification {

	private StepsProperties stepsPropertiesMock

	private StepToStepPropertiesProvider stepToStepPropertiesProvider

	def setup() {
		stepsPropertiesMock = Mock(StepsProperties)
		stepToStepPropertiesProvider = new StepToStepPropertiesProvider(stepsPropertiesMock)
	}

	def "should provide properties"() {
		when:
		Map<String, StepProperties> stepPropertiesMap = stepToStepPropertiesProvider.provide()

		then:
		stepPropertiesMap.size() == StepConfigurationValidator.NUMBER_OF_STEPS
	}

}
