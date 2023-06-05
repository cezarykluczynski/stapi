package com.cezarykluczynski.stapi.etl.configuration.job.success

import com.cezarykluczynski.stapi.etl.util.constant.StepName
import spock.lang.Specification

class StepSuccessParametersProviderTest extends Specification {

	StepSuccessParametersProvider stepSuccessParametersProvider

	void setup() {
		stepSuccessParametersProvider = new StepSuccessParametersProvider()
	}

	void "provides parameters for a known step"() {
		when:
		StepSuccessParameters stepSuccessParameters = stepSuccessParametersProvider.getStepSuccessParameters(StepName.CREATE_COMPANIES)

		then:
		stepSuccessParameters.maxEntriesRemoved == 2
		stepSuccessParameters.maxEntriesRemovedPercentage == null
		stepSuccessParameters.maxEntriesAdded == null
		stepSuccessParameters.maxEntriesAddedPercentage == 3d
	}

	void "provides parameters for an unknown step"() {
		when:
		StepSuccessParameters stepSuccessParameters = stepSuccessParametersProvider.getStepSuccessParameters('FAKE_STEP_NAME')

		then:
		stepSuccessParameters.maxEntriesRemoved == 0
		stepSuccessParameters.maxEntriesRemovedPercentage == null
		stepSuccessParameters.maxEntriesAdded == null
		stepSuccessParameters.maxEntriesAddedPercentage == 1d
	}

}
