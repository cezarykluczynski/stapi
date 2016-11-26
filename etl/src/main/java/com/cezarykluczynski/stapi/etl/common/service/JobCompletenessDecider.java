package com.cezarykluczynski.stapi.etl.common.service;

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

@Service
public class JobCompletenessDecider {

	public static final String JOB_CREATE = "CREATE";
	public static final String STEP_001_CREATE_SERIES = "STEP_001_CREATE_SERIES";
	public static final String STEP_002_CREATE_PERFORMERS = "STEP_002_CREATE_PERFORMERS";
	public static final String STEP_003_CREATE_STAFF = "STEP_003_CREATE_STAFF";
	public static final String STEP_004_CREATE_CHARACTERS = "STEP_004_CREATE_CHARACTERS";
	public static final String STEP_005_CREATE_EPISODES= "STEP_005_CREATE_EPISODES";

	private JobRepository jobRepository;

	@Inject
	public JobCompletenessDecider(JobRepository jobRepository) {
		this.jobRepository = jobRepository;
	}

	public boolean isStepComplete(String stepName) {
		Collection<StepExecution> stepExecutions = getAllStepExecutions();
		return stepExecutions.stream().anyMatch(stepExecution ->
			stepExecution.getStepName().equals(stepName) && BatchStatus.COMPLETED.equals(stepExecution.getStatus()));
	}

	private Collection<StepExecution> getAllStepExecutions() {
		JobExecution jobExecution;

		try {
			jobExecution = jobRepository
					.getLastJobExecution(JOB_CREATE, new JobParameters());
		} catch (BadSqlGrammarException e) {
			return Lists.newArrayList();
		}

		return jobExecution == null ? Lists.newArrayList() : jobExecution.getStepExecutions();
	}

}
