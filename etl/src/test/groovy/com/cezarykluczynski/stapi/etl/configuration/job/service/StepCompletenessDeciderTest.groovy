package com.cezarykluczynski.stapi.etl.configuration.job.service

import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepProperties
import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepToStepPropertiesProvider
import com.google.common.collect.Lists
import com.google.common.collect.Maps
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.StepExecution
import spock.lang.Specification

class StepCompletenessDeciderTest extends Specification {

	private static final String JOB_NAME = 'JOB_NAME'
	private static final String STEP_NAME = 'STEP_NAME'

	private StepToStepPropertiesProvider stepToStepPropertiesProviderMock

	private AllStepExecutionsProvider allStepExecutionsProviderMock

	private StepCompletenessDecider jobCompletenessDecider

	void setup() {
		stepToStepPropertiesProviderMock = Mock()
		allStepExecutionsProviderMock = Mock()
		jobCompletenessDecider = new StepCompletenessDecider(stepToStepPropertiesProviderMock, allStepExecutionsProviderMock)
	}

	void "returns true when step is not enabled"() {
		given:
		StepProperties stepProperties = Mock()
		Map<String, StepProperties> stepPropertiesMap = Maps.newHashMap()
		stepPropertiesMap.put(STEP_NAME, stepProperties)

		when:
		boolean complete = jobCompletenessDecider.isStepComplete(JOB_NAME, STEP_NAME)

		then:
		1 * stepProperties.isEnabled() >> false
		1 * stepToStepPropertiesProviderMock.provide() >> stepPropertiesMap
		0 * _
		complete
	}

	void "returns true when step is completed"() {
		given:
		StepExecution stepExecution = Mock()

		when:
		boolean complete = jobCompletenessDecider.isStepComplete(JOB_NAME, STEP_NAME)

		then:
		1 * stepToStepPropertiesProviderMock.provide() >> Maps.newHashMap()
		1 * allStepExecutionsProviderMock.provide(JOB_NAME) >> Lists.newArrayList(stepExecution)
		1 * stepExecution.stepName >> STEP_NAME
		1 * stepExecution.status >> BatchStatus.COMPLETED
		0 * _
		complete
	}

	void "returns false when step is not completed"() {
		given:
		StepExecution stepExecution = Mock()

		when:
		boolean complete = jobCompletenessDecider.isStepComplete(JOB_NAME, STEP_NAME)

		then:
		1 * stepToStepPropertiesProviderMock.provide() >> Maps.newHashMap()
		1 * allStepExecutionsProviderMock.provide(JOB_NAME) >> Lists.newArrayList(stepExecution)
		1 * stepExecution.stepName >> STEP_NAME
		1 * stepExecution.status >> BatchStatus.UNKNOWN
		0 * _
		!complete
	}

}
