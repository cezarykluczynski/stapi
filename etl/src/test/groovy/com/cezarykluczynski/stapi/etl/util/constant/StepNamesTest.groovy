package com.cezarykluczynski.stapi.etl.util.constant

import com.cezarykluczynski.stapi.etl.configuration.job.StepConfigurationValidator
import spock.lang.Specification

class StepNamesTest extends Specification {

	void "there should be as much steps in create as there are steps in StepConfigurationValidator"() {
		expect:
		StepNames.JOB_STEPS.get(JobName.JOB_CREATE).size() == StepConfigurationValidator.NUMBER_OF_STEPS
	}

}
