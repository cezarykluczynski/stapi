package com.cezarykluczynski.stapi.etl.common.backup;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.repository.dao.JobExecutionDao;
import org.springframework.batch.core.repository.dao.JobInstanceDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
class BackupAfterStepConsistencyGuarantee {

	private final JobExecutionDao jobExecutionDao;
	private final JobInstanceDao jobInstanceDao;

	void ensureState() {
		while (true) {
			final List<JobExecution> jobExecutions = jobInstanceDao.getJobNames().stream()
					.flatMap(jobName -> jobExecutionDao.findRunningJobExecutions(jobName).stream())
					.toList();

			if (jobExecutions.stream().anyMatch(jobExecution -> !ExitStatus.COMPLETED.equals(jobExecution.getExitStatus()))) {
				log.info("Unfinished jobs still present, waiting.");
				doWait();
				continue;
			}

			final List<StepExecution> stepExecutions = jobExecutions.stream()
					.flatMap(jobExecution -> jobExecution.getStepExecutions().stream())
					.toList();

			if (stepExecutions.stream().anyMatch(stepExecution -> !ExitStatus.COMPLETED.equals(stepExecution.getExitStatus()))) {
				log.info("Unfinished steps still present, waiting.");
				doWait();
				continue;
			}

			log.info("Spring Batch finished, consistency is expected now.");
			return;
		}
	}

	private void doWait() {
		try {
			Thread.sleep(1000L);
		} catch (Exception e) {
			log.error("Error waiting fpr Spring Batch consistency, backup will no be performed.", e);
			throw new RuntimeException(e);
		}
	}

}
