package com.cezarykluczynski.stapi.etl.configuration.job;

import com.cezarykluczynski.stapi.etl.configuration.job.success.PreviousStepSuccessValidator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NextJobRunner {

	private final JobBuilder jobBuilder;
	private final JobLauncher jobLauncher;
	private final PreviousStepSuccessValidator previousStepSuccessValidator;

	@SneakyThrows
	@SuppressWarnings("ReturnCount")
	public void scheduleNext(StepExecution previousStepExecution) {
		String previousStepName = previousStepExecution.getStepName();
		if (!ExitStatus.COMPLETED.equals(previousStepExecution.getExitStatus())) {
			log.error("Not launching next step, because previous step {} was not successful.", previousStepName);
			return;
		}

		JobExecution previousJobExecution = previousStepExecution.getJobExecution();
		if (!ExitStatus.COMPLETED.equals(previousStepExecution.getJobExecution().getExitStatus())) {
			log.error("Not launching next step, because previous job {} was not successful.", previousJobExecution.getJobInstance().getJobName());
			return;
		}

		if (!previousStepSuccessValidator.wasSuccessful(previousStepExecution)) {
			log.error("Not launching next step, because previous step {} validation was not considered successful.", previousStepName);
			return;
		}

		final Job job = jobBuilder.buildNext();
		if (job == null) {
			return;
		}
		log.info("Launching new job with next step, because previous step {} was successful.", previousStepName);
		jobLauncher.run(job, previousJobExecution.getJobParameters());
	}

}
