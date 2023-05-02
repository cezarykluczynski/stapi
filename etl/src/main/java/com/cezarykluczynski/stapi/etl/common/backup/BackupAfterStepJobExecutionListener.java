package com.cezarykluczynski.stapi.etl.common.backup;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Entity;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BackupAfterStepJobExecutionListener implements JobExecutionListener {

	private final BackupAfterStepExecutor backupAfterStepExecutor;

	public void afterJob(JobExecution jobExecution) {
		final Collection<StepExecution> stepExecutions = jobExecution.getStepExecutions();
		List<StepExecution> stepExecutionsSorted = stepExecutions.stream()
				.filter(stepExecution -> ExitStatus.COMPLETED.equals(stepExecution.getExitStatus()))
				.sorted(Comparator.comparing(Entity::getId))
				.toList();

		if (stepExecutionsSorted.isEmpty()) {
			return;
		}

		if (stepExecutionsSorted.size() > 1) {
			List<String> stepNamesWithoutBackup = stepExecutionsSorted.stream()
					.limit(stepExecutionsSorted.size() - 1)
					.map(StepExecution::getStepName)
					.toList();
			log.warn("More than one step executed at once, steps {} will not have backup.", stepNamesWithoutBackup);
		}
		StepExecution stepExecutionToBackup = stepExecutionsSorted.get(stepExecutionsSorted.size() - 1);
		backupAfterStepExecutor.execute(stepExecutionToBackup);
	}

}
