package com.cezarykluczynski.stapi.etl.common.backup;

import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepProperties;
import com.google.common.base.Stopwatch;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostgresAfterStepBackupWorker implements AfterStepBackupWorker {

	private final BackupFileUtils backupFileUtils;
	private final PostgresCommandProvider postgresCommandProvider;

	@Override
	public boolean supports(String driveClassName) {
		return "org.postgresql.Driver".equals(driveClassName);
	}

	@SneakyThrows
	@Override
	@SuppressWarnings("ReturnCount")
	public void backupStep(StepExecution stepExecution, StepProperties stepProperties) {
		@SuppressWarnings("VariableDeclarationUsageDistance")
		Stopwatch stopwatch = Stopwatch.createStarted();
		if (!backupFileUtils.validateSourceDirectoryExistence()) {
			return;
		}

		String stepName = stepExecution.getStepName();
		Optional<File> backupFileOptional = backupFileUtils.createBackupFile(stepExecution, stepProperties);
		if (backupFileOptional.isEmpty()) {
			return;
		}

		File backupFile = backupFileOptional.get();
		List<String> command = postgresCommandProvider.provide(backupFile, stepName);
		if (command.isEmpty()) {
			return;
		}
		log.info("Postgres backup command is: {}", String.join(" ", command));
		File postgresOutput = backupFileUtils.createTempFile("stapi_pg_output.txt");
		File postgresError = backupFileUtils.createTempFile("stapi_pg_error.txt");
		ProcessBuilder processBuilder = backupFileUtils.createProcessBuilder(command, postgresOutput, postgresError);
		int exitCode;
		try {
			Process process = processBuilder.start();
			exitCode = process.waitFor();
		} catch (Exception e) {
			log.error("Failed to backup PostgreSQL for step {}.", stepName, e);
			return;
		}

		if (exitCode != 0) {
			log.error("Failed to backup PostgreSQL for step {}. Exit code was {}.", stepName, exitCode);
			return;
		}

		log.info("PostgreSQL backup for step {} successfully performed in {} seconds.", stepName, stopwatch.elapsed(TimeUnit.SECONDS));
	}

}
