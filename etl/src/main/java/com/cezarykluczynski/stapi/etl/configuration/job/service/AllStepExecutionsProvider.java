package com.cezarykluczynski.stapi.etl.configuration.job.service;

import com.google.common.collect.Lists;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.repository.dao.ExecutionContextDao;
import org.springframework.batch.core.repository.dao.JobExecutionDao;
import org.springframework.batch.core.repository.dao.JobInstanceDao;
import org.springframework.batch.core.repository.dao.StepExecutionDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AllStepExecutionsProvider {

	private final JobInstanceDao jobInstanceDao;

	private final JobExecutionDao jobExecutionDao;

	private final StepExecutionDao stepExecutionDao;

	private final ExecutionContextDao executionContextDao;

	public AllStepExecutionsProvider(JobInstanceDao jobInstanceDao, JobExecutionDao jobExecutionDao,
			StepExecutionDao stepExecutionDao, ExecutionContextDao executionContextDao) {
		this.jobInstanceDao = jobInstanceDao;
		this.jobExecutionDao = jobExecutionDao;
		this.stepExecutionDao = stepExecutionDao;
		this.executionContextDao = executionContextDao;
	}

	public List<StepExecution> provide(String jobName) {
		List<StepExecution> stepExecutionList = Lists.newArrayList();

		JobInstance jobInstance = jobInstanceDao.getJobInstance(jobName, new JobParameters());

		if (jobInstance == null) {
			return stepExecutionList;
		}

		List<JobExecution> jobExecutionList = jobExecutionDao.findJobExecutions(jobInstance);

		jobExecutionList.forEach(jobExecution -> {
			jobExecution.setExecutionContext(executionContextDao.getExecutionContext(jobExecution));
			stepExecutionDao.addStepExecutions(jobExecution);
			stepExecutionList.addAll(jobExecution.getStepExecutions());
		});

		return stepExecutionList;
	}

}
