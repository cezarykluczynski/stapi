package com.cezarykluczynski.stapi.etl.common.backup

import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepProperties
import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepToStepPropertiesProvider
import com.zaxxer.hikari.HikariDataSource
import org.springframework.batch.core.StepExecution
import spock.lang.Specification

class BackupAfterStepExecutorTest extends Specification {

	static final String STEP_NAME = 'STEP_NAME'
	static final String DRIVER_CLASS_NAME = 'DRIVER_CLASS_NAME'

	BackupAfterStepProperties backupAfterStepPropertiesMock
	StepToStepPropertiesProvider stepToStepPropertiesProviderMock
	HikariDataSource hikariDataSourceMock
	AfterStepBackupWorker afterStepBackupWorkerToIgnoreMock
	AfterStepBackupWorker afterStepBackupWorkerToUseMock
	BackupAfterStepExecutor backupAfterStepExecutor

	void setup() {
		backupAfterStepPropertiesMock = Mock()
		stepToStepPropertiesProviderMock = Mock()
		hikariDataSourceMock = Mock()
		afterStepBackupWorkerToIgnoreMock = Mock()
		afterStepBackupWorkerToUseMock = Mock()
		backupAfterStepExecutor = new BackupAfterStepExecutor(backupAfterStepPropertiesMock, stepToStepPropertiesProviderMock,
				hikariDataSourceMock, [afterStepBackupWorkerToIgnoreMock, afterStepBackupWorkerToUseMock])
	}

	void "when backup after step is disabled, nothing happens"() {
		when:
		backupAfterStepExecutor.execute(null)

		then:
		1 * backupAfterStepPropertiesMock.enabled >> false
		0 * _
	}

	void "when backup after step is enabled, it is delegated"() {
		given:
		StepExecution stepExecution = new StepExecution(STEP_NAME)
		StepProperties stepProperties = new StepProperties()

		when:
		backupAfterStepExecutor.execute(stepExecution)

		then:
		1 * backupAfterStepPropertiesMock.enabled >> true
		1 * stepToStepPropertiesProviderMock.provide() >> Map.of(STEP_NAME, stepProperties)
		1 * hikariDataSourceMock.driverClassName >> DRIVER_CLASS_NAME

		then:
		1 * afterStepBackupWorkerToIgnoreMock.supports(DRIVER_CLASS_NAME) >> false

		then:
		1 * afterStepBackupWorkerToUseMock.supports(DRIVER_CLASS_NAME) >> true
		1 * afterStepBackupWorkerToUseMock.backupStep(stepExecution, stepProperties)
		0 * _
	}

}
