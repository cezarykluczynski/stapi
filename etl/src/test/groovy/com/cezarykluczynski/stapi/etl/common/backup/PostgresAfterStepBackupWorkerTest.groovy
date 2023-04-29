package com.cezarykluczynski.stapi.etl.common.backup

import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepProperties
import org.springframework.batch.core.StepExecution
import spock.lang.Specification

class PostgresAfterStepBackupWorkerTest extends Specification {

	static final String STEP_NAME = 'STEP_NAME'

	BackupFileUtils backupFileUtilsMock
	PostgresCommandProvider postgresCommandProviderMock
	PostgresAfterStepBackupWorker postgresAfterStepBackupWorker

	void setup() {
		backupFileUtilsMock = Mock()
		postgresCommandProviderMock = Mock()
		postgresAfterStepBackupWorker = new PostgresAfterStepBackupWorker(backupFileUtilsMock, postgresCommandProviderMock)
	}

	void "does nothing more when target directory does not exist"() {
		given:
		StepExecution stepExecution = new StepExecution(STEP_NAME)
		StepProperties stepProperties = Mock()

		when:
		postgresAfterStepBackupWorker.backupStep(stepExecution, stepProperties)

		then:
		1 * backupFileUtilsMock.validateSourceDirectoryExistence() >> false
		0 * _
	}

	void "does nothing more when backup file cannot be created"() {
		given:
		StepExecution stepExecution = new StepExecution(STEP_NAME)
		StepProperties stepProperties = Mock()

		when:
		postgresAfterStepBackupWorker.backupStep(stepExecution, stepProperties)

		then:
		1 * backupFileUtilsMock.validateSourceDirectoryExistence() >> true
		1 * backupFileUtilsMock.createBackupFile(stepExecution, stepProperties) >> Optional.empty()
		0 * _
	}

	void "does nothing more when command is empty"() {
		given:
		StepExecution stepExecution = new StepExecution(STEP_NAME)
		StepProperties stepProperties = Mock()
		File backupFile = Mock()

		when:
		postgresAfterStepBackupWorker.backupStep(stepExecution, stepProperties)

		then:
		1 * backupFileUtilsMock.validateSourceDirectoryExistence() >> true
		1 * backupFileUtilsMock.createBackupFile(stepExecution, stepProperties) >> Optional.of(backupFile)
		1 * postgresCommandProviderMock.provide(backupFile, STEP_NAME) >> []
		0 * _
	}

	void "executes command when it is valid"() {
		given:
		StepExecution stepExecution = new StepExecution(STEP_NAME)
		StepProperties stepProperties = Mock()
		File backupFile = Mock()
		File postgresOutput = Mock()
		File postgresError = Mock()
		List<String> command = List.of('echo', 'test')

		when:
		postgresAfterStepBackupWorker.backupStep(stepExecution, stepProperties)

		then:
		1 * backupFileUtilsMock.validateSourceDirectoryExistence() >> true
		1 * backupFileUtilsMock.createBackupFile(stepExecution, stepProperties) >> Optional.of(backupFile)
		1 * postgresCommandProviderMock.provide(backupFile, STEP_NAME) >> command
		1 * backupFileUtilsMock.createTempFile('stapi_pg_output.txt') >> postgresOutput
		1 * backupFileUtilsMock.createTempFile('stapi_pg_error.txt') >> postgresError
		1 * backupFileUtilsMock.createProcessBuilder(command, postgresOutput, postgresError) >> new ProcessBuilder(command)
		0 * _
	}

}
