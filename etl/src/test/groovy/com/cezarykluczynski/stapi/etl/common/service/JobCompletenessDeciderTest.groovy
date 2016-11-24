package com.cezarykluczynski.stapi.etl.common.service

import com.google.common.collect.Lists
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.StepExecution
import org.springframework.batch.core.repository.JobRepository
import spock.lang.Specification

class JobCompletenessDeciderTest extends Specification {

	private JobRepository jobRepositoryMock

	private JobCompletenessDecider jobCompletenessDecider

	def setup() {
		jobRepositoryMock = Mock(JobRepository)
		jobCompletenessDecider = new JobCompletenessDecider(jobRepositoryMock)
	}

	def "returns false when there is no last job execution"() {
		when:
		boolean complete = jobCompletenessDecider.isStepComplete(JobCompletenessDecider.STEP_001_CREATE_SERIES)

		then:
		1 * jobRepositoryMock.getLastJobExecution(*_) >> null
		!complete
	}

	def "returns false when there job is not complete"() {
		when:
		boolean complete = jobCompletenessDecider.isStepComplete(JobCompletenessDecider.STEP_001_CREATE_SERIES)

		then:
		1 * jobRepositoryMock.getLastJobExecution(*_) >>  Mock(JobExecution) {
			getStepExecutions() >> Lists.newArrayList(
					Mock(StepExecution) {
						getStepName() >> JobCompletenessDecider.STEP_001_CREATE_SERIES
						getStatus() >> BatchStatus.FAILED
					}
			)
		}
		!complete
	}

	def "returns true when there job is complete"() {
		when:
		boolean complete = jobCompletenessDecider.isStepComplete(JobCompletenessDecider.STEP_002_CREATE_PERFORMERS)

		then:
		1 * jobRepositoryMock.getLastJobExecution(*_) >>  Mock(JobExecution) {
			getStepExecutions() >> Lists.newArrayList(
					Mock(StepExecution) {
						getStepName() >> JobCompletenessDecider.STEP_002_CREATE_PERFORMERS
						getStatus() >> BatchStatus.COMPLETED
					}
			)
		}
		complete
	}

}
