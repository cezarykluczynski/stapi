package com.cezarykluczynski.stapi.etl.common.backup;

import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepProperties;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.StepExecution;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
class BackupFileUtils {

	private static final String STEP_FILE_NAME_TEMPLATE = "step_%s_stapi.backup";
	private static final String STEP_OLDER_VERSION_FILE_NAME_TEMPLATE = "step_%s_stapi_%s.backup";
	private static final DateTimeFormatter STEP_OLDER_VERSION_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("uuuu_MM_dd_HH_mm");
	private static final String PAD_CHAR = "0";
	private static final int PAD_LENGTH = 2;

	private final BackupAfterStepProperties backupAfterStepProperties;

	boolean validateSourceDirectoryExistence() {
		final String targetDirectory = backupAfterStepProperties.getTargetDirectory();
		File directory = new File(targetDirectory);
		if (!directory.exists()) {
			log.error("Target directory {} should exists. Skipping PostgreSQL step backup.", targetDirectory);
			return false;
		}
		if (!directory.isDirectory()) {
			log.error("File {} should be a directory. Skipping PostgreSQL step backup.", targetDirectory);
			return false;
		}

		return true;
	}

	Optional<File> createBackupFile(StepExecution stepExecution, StepProperties stepProperties) {
		final String targetDirectory = backupAfterStepProperties.getTargetDirectory();
		File directory = new File(targetDirectory);
		String stepName = stepExecution.getStepName();
		String backupStepFileName = getFileName(stepProperties);
		File backupFile = new File(directory, backupStepFileName);
		if (backupFile.exists()) {
			ZonedDateTime lastModified = Instant.ofEpochMilli(directory.lastModified()).atZone(ZoneId.systemDefault());
			String backupStepOlderVersionFileName = getOlderVersionFileName(stepProperties, lastModified);
			File dest = new File(directory, backupStepOlderVersionFileName);
			if (!backupFile.renameTo(dest)) {
				log.error("Could not rename {} to {}, skipping PostgreSQL backup for step {}.",
						backupStepFileName, backupStepOlderVersionFileName, stepName);
				return Optional.empty();
			}
		}
		return Optional.of(backupFile);
	}

	@SneakyThrows
	File createTempFile(String fileName) {
		File directory = new File(System.getProperty("java.io.tmpdir"));
		File file = new File(directory, fileName);
		if (!file.exists()) {
			if (!file.createNewFile()) {
				throw new FileNotFoundException();
			}
		}
		return file;
	}

	ProcessBuilder createProcessBuilder(List<String> command, File output, File error) {
		ProcessBuilder processBuilder = new ProcessBuilder(command);
		processBuilder.redirectOutput(ProcessBuilder.Redirect.to(output));
		processBuilder.redirectError(ProcessBuilder.Redirect.to(error));
		return processBuilder;
	}

	private String getFileName(StepProperties stepProperties) {
		String stepNumber = StringUtils.leftPad(String.valueOf(stepProperties.getOrder()), PAD_LENGTH, PAD_CHAR);
		return String.format(STEP_FILE_NAME_TEMPLATE, stepNumber);
	}

	private String getOlderVersionFileName(StepProperties stepProperties, ZonedDateTime lastModified) {
		String stepNumber = StringUtils.leftPad(String.valueOf(stepProperties.getOrder()), PAD_LENGTH, PAD_CHAR);
		return String.format(STEP_OLDER_VERSION_FILE_NAME_TEMPLATE, stepNumber, STEP_OLDER_VERSION_DATE_TIME_FORMATTER.format(lastModified));
	}
}
