package com.cezarykluczynski.stapi.etl.common.service

import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepProperties
import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepToStepPropertiesProvider
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.google.common.collect.Lists
import com.google.common.collect.Maps
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.StepExecution
import org.springframework.batch.core.repository.JobRepository
import spock.lang.Specification

class JobCompletenessDeciderTest extends Specification {

	private JobRepository jobRepositoryMock

	private StepToStepPropertiesProvider stepToStepPropertiesProviderMock

	private JobCompletenessDecider jobCompletenessDecider

	def setup() {
		stepToStepPropertiesProviderMock = Mock(StepToStepPropertiesProvider)
		jobRepositoryMock = Mock(JobRepository)
		jobCompletenessDecider = new JobCompletenessDecider(jobRepositoryMock, stepToStepPropertiesProviderMock)
	}

	def "returns false when there is no last job execution"() {
		when:
		boolean complete = jobCompletenessDecider.isStepComplete(StepName.CREATE_SERIES)

		then:
		1 * stepToStepPropertiesProviderMock.provide() >> Maps.newHashMap()
		1 * jobRepositoryMock.getLastJobExecution(*_) >> null
		0 * _
		!complete
	}

	def "returns false when there job is not complete"() {
		when:
		boolean complete = jobCompletenessDecider.isStepComplete(StepName.CREATE_SERIES)

		then:
		1 * stepToStepPropertiesProviderMock.provide() >> Maps.newHashMap()
		1 * jobRepositoryMock.getLastJobExecution(*_) >>  Mock(JobExecution) {
			getStepExecutions() >> Lists.newArrayList(
					Mock(StepExecution) {
						getStepName() >> StepName.CREATE_SERIES
						getStatus() >> BatchStatus.FAILED
					}
			)
		}
		0 * _
		!complete
	}

	def "returns true when there job is complete"() {
		when:
		boolean complete = jobCompletenessDecider.isStepComplete(StepName.CREATE_PERFORMERS)

		then:
		1 * stepToStepPropertiesProviderMock.provide() >> Maps.newHashMap()
		1 * jobRepositoryMock.getLastJobExecution(*_) >>  Mock(JobExecution) {
			getStepExecutions() >> Lists.newArrayList(
					Mock(StepExecution) {
						getStepName() >> StepName.CREATE_PERFORMERS
						getStatus() >> BatchStatus.COMPLETED
					}
			)
		}
		0 * _
		complete
	}

	def "returns true when step is disabled"() {
		given:
		StepProperties stepProperties = Mock(StepProperties)
		Map<String, StepProperties> stepPropertiesMap = Maps.newHashMap()
		stepPropertiesMap.put(StepName.CREATE_PERFORMERS, stepProperties)

		when:
		boolean complete = jobCompletenessDecider.isStepComplete(StepName.CREATE_PERFORMERS)

		then:
		1 * stepToStepPropertiesProviderMock.provide() >> stepPropertiesMap
		1 * stepProperties.isEnabled() >> false
		0 * _
		complete
	}

}
