package com.cezarykluczynski.stapi.etl.common.backup;

import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepProperties;
import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepToStepPropertiesProvider;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BackupAfterStepExecutor {

	private final BackupAfterStepProperties backupAfterStepProperties;
	private final StepToStepPropertiesProvider stepToStepPropertiesProvider;
	private final HikariDataSource hikariDataSource;
	private final List<AfterStepBackupWorker> afterStepBackupWorkers;

	public void execute(StepExecution stepExecution) {
		if (!backupAfterStepProperties.isEnabled()) {
			return;
		}

		final String stepName = stepExecution.getStepName();
		final StepProperties stepProperties = stepToStepPropertiesProvider.provide().get(stepName);
		final String driverClassName = hikariDataSource.getDriverClassName();
		boolean supportedBackupWorkerFound = false;
		for (AfterStepBackupWorker afterStepBackupWorker : afterStepBackupWorkers) {
			if (afterStepBackupWorker.supports(driverClassName)) {
				supportedBackupWorkerFound = true;
				afterStepBackupWorker.backupStep(stepExecution, stepProperties);
			}
		}
		if (!supportedBackupWorkerFound) {
			log.warn("No implementation for driver class {} found, no backup after step {} was performed.", driverClassName, stepName);
		}
	}

}
