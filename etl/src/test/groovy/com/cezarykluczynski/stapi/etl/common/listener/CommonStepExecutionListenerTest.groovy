package com.cezarykluczynski.stapi.etl.common.listener

import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.StepExecution
import spock.lang.Specification

class CommonStepExecutionListenerTest extends Specification {

	private StepExecution stepExecutionMock

	private CommonStepExecutionListener commonStepExecutionListener

	def setup() {
		stepExecutionMock = Mock(StepExecution)
		commonStepExecutionListener = new CommonStepExecutionListener()
	}

	def "logs before step"() {
		when: "before step callback is called"
		commonStepExecutionListener.beforeStep(stepExecutionMock)

		then: 'name and start time is used to build message'
		1 * stepExecutionMock.getStepName()
		1 * stepExecutionMock.getStartTime()

		then: 'no other interactions are expected'
		0 * _
	}

	def "logs after step"() {
		given:
		ExitStatus exitStatusMock = Mock(ExitStatus)

		when: 'after step callback is called'
		commonStepExecutionListener.afterStep(stepExecutionMock)

		then: 'name, last update time, exit code, read count, and write count is used to build message'
		1 * stepExecutionMock.getStepName()
		1 * stepExecutionMock.getLastUpdated()
		1 * stepExecutionMock.getExitStatus() >> exitStatusMock
		1 * exitStatusMock.getExitCode()
		1 * stepExecutionMock.getReadCount()
		1 * stepExecutionMock.getWriteCount()

		then: 'no other interactions are expected'
		0 * _
	}

}
