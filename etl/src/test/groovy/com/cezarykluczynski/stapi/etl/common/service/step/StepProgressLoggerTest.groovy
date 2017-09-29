package com.cezarykluczynski.stapi.etl.common.service.step

import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.StepExecution
import spock.lang.Specification

class StepProgressLoggerTest extends Specification {

	private StepExecution stepExecutionMock

	private StepProgressLogger stepProgressLogger

	void setup() {
		stepExecutionMock = Mock()
		stepProgressLogger = new StepProgressLogger()
	}

	void "logs before step"() {
		when: 'step started callback is called'
		stepProgressLogger.stepStarted(stepExecutionMock)

		then: 'name and start time is used to build message'
		1 * stepExecutionMock.stepName
		1 * stepExecutionMock.startTime

		then: 'no other interactions are expected'
		0 * _
	}

	void "logs after step"() {
		given:
		ExitStatus exitStatusMock = Mock()

		when: 'step ended callback is called'
		stepProgressLogger.stepEnded(stepExecutionMock)

		then: 'name, last update time, exit code, read count, and write count is used to build message'
		1 * stepExecutionMock.stepName
		1 * stepExecutionMock.lastUpdated
		1 * stepExecutionMock.exitStatus >> exitStatusMock
		1 * exitStatusMock.exitCode
		1 * stepExecutionMock.readCount
		1 * stepExecutionMock.writeCount
	}

}
