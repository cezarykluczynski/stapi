package com.cezarykluczynski.stapi.etl.common.service;

import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepProperties;
import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepToStepPropertiesProvider;
import com.cezarykluczynski.stapi.etl.util.constant.JobName;
import com.google.common.collect.Lists;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Map;

@Service
public class JobCompletenessDecider {

	private JobRepository jobRepository;

	private StepToStepPropertiesProvider stepToStepPropertiesProvider;

	@Inject
	public JobCompletenessDecider(JobRepository jobRepository,
			StepToStepPropertiesProvider stepToStepPropertiesProvider) {
		this.jobRepository = jobRepository;
		this.stepToStepPropertiesProvider = stepToStepPropertiesProvider;
	}

	public boolean isStepComplete(String stepName) {
		Map<String, StepProperties> stepPropertiesMap = stepToStepPropertiesProvider.provide();
		StepProperties stepProperties = stepPropertiesMap.get(stepName);

		if (stepProperties != null && !stepProperties.isEnabled()) {
			return true;
		}

		Collection<StepExecution> stepExecutions = getAllStepExecutions();
		return stepExecutions.stream().anyMatch(stepExecution ->
			stepExecution.getStepName().equals(stepName) && BatchStatus.COMPLETED.equals(stepExecution.getStatus()));
	}

	private Collection<StepExecution> getAllStepExecutions() {
		JobExecution jobExecution;

		try {
			jobExecution = jobRepository
					.getLastJobExecution(JobName.JOB_CREATE, new JobParameters());
		} catch (BadSqlGrammarException e) {
			return Lists.newArrayList();
		}

		return jobExecution == null ? Lists.newArrayList() : jobExecution.getStepExecutions();
	}

}
