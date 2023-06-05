package com.cezarykluczynski.stapi.etl.configuration.job

import com.cezarykluczynski.stapi.etl.common.backup.BackupAfterStepExecutor
import org.springframework.batch.core.StepExecution
import spock.lang.Specification

class AsyncAfterJobExecutorTest extends Specification {

	private BackupAfterStepExecutor backupAfterStepExecutorMock
	private NextJobRunner nextJobRunnerMock
	private AsyncAfterJobExecutor asyncAfterJobExecutor

	void setup() {
		backupAfterStepExecutorMock = Mock()
		nextJobRunnerMock = Mock()
		asyncAfterJobExecutor = new AsyncAfterJobExecutor(backupAfterStepExecutorMock, nextJobRunnerMock)
	}

	void "interacts with dependencies in order"() {
		given:
		StepExecution stepExecution = Mock()

		when:
		asyncAfterJobExecutor.execute(stepExecution)

		then:
		1 * backupAfterStepExecutorMock.execute(stepExecution)

		then:
		1 * nextJobRunnerMock.scheduleNext(stepExecution)

		then:
		0 * _
	}

}
