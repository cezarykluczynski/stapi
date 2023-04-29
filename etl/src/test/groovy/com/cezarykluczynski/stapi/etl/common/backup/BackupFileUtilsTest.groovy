package com.cezarykluczynski.stapi.etl.common.backup

import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepProperties
import org.apache.commons.lang3.RandomUtils
import org.springframework.batch.core.StepExecution
import spock.lang.Specification

class BackupFileUtilsTest extends Specification {

	static final String STEP_NAME = 'STEP_NAME'

	BackupAfterStepProperties backupAfterStepPropertiesMock

	BackupFileUtils backupFileUtils

	void setup() {
		backupAfterStepPropertiesMock = Mock()
		backupFileUtils = new BackupFileUtils(backupAfterStepPropertiesMock)
	}

	void "validates directory existence of an existing directory"() {
		when:
		boolean result = backupFileUtils.validateSourceDirectoryExistence()

		then:
		1 * backupAfterStepPropertiesMock.targetDirectory >> new File(System.getProperty('java.io.tmpdir')).absolutePath
		0 * _
		result
	}

	void "validates directory existence of an non-existing directory"() {
		when:
		boolean result = backupFileUtils.validateSourceDirectoryExistence()

		then:
		1 * backupAfterStepPropertiesMock.targetDirectory >> new File(System.getProperty('java.io.tmpdir'), UUID.randomUUID().toString()).absolutePath
		0 * _
		!result
	}

	void "creates temp file, then backup file"() {
		given:
		String fileName = UUID.randomUUID()
		StepExecution stepExecution = new StepExecution(STEP_NAME)
		StepProperties stepProperties = new StepProperties(order: RandomUtils.nextInt(0, 100))

		when:
		File tempFile = backupFileUtils.createTempFile(fileName)

		then:
		tempFile.exists()

		when:
		Optional<File> fileOptional = backupFileUtils.createBackupFile(stepExecution, stepProperties)

		then:
		1 * backupAfterStepPropertiesMock.targetDirectory >> new File(System.getProperty('java.io.tmpdir'), UUID.randomUUID().toString()).absolutePath
		0 * _
		fileOptional.present
	}

	void "creates process builder"() {
		given:
		File output = Mock()
		File error = Mock()

		when:
		ProcessBuilder processBuilder = backupFileUtils.createProcessBuilder(['echo', 'test'], output, error)

		then:
		0 * _
		processBuilder != null
	}

}
