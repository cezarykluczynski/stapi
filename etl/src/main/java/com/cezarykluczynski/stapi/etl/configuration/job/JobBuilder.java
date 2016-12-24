package com.cezarykluczynski.stapi.etl.configuration.job;

import com.cezarykluczynski.stapi.etl.configuration.job.service.JobCompletenessDecider;
import com.cezarykluczynski.stapi.etl.util.constant.JobName;
import com.cezarykluczynski.stapi.etl.util.constant.StepName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.SimpleJobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
@Slf4j
public class JobBuilder {

	private ApplicationContext applicationContext;

	private JobBuilderFactory jobBuilderFactory;

	private StepConfigurationValidator stepConfigurationValidator;

	private JobCompletenessDecider jobCompletenessDecider;

	@Inject
	public JobBuilder(ApplicationContext applicationContext, JobBuilderFactory jobBuilderFactory,
			StepConfigurationValidator stepConfigurationValidator, JobCompletenessDecider jobCompletenessDecider) {
		this.applicationContext = applicationContext;
		this.jobBuilderFactory = jobBuilderFactory;
		this.stepConfigurationValidator = stepConfigurationValidator;
		this.jobCompletenessDecider = jobCompletenessDecider;
	}

	public synchronized Job build() {
		stepConfigurationValidator.validate();

		if (jobCompletenessDecider.isJobCompleted(JobName.JOB_CREATE)) {
			return null;
		}

		org.springframework.batch.core.job.builder.JobBuilder jobBuilder = jobBuilderFactory.get(JobName.JOB_CREATE);
		SimpleJobBuilder simpleJobBuilder = new SimpleJobBuilder(jobBuilder);

		Flow flow1 = new FlowBuilder<Flow>("flow1")
				.from(applicationContext.getBean(StepName.CREATE_SERIES, Step.class))
				.next(applicationContext.getBean(StepName.CREATE_PERFORMERS, Step.class))
				.next(applicationContext.getBean(StepName.CREATE_STAFF, Step.class))
				.next(applicationContext.getBean(StepName.CREATE_CHARACTERS, Step.class))
				.next(applicationContext.getBean(StepName.CREATE_EPISODES, Step.class))
				.next(applicationContext.getBean(StepName.CREATE_MOVIES, Step.class))
				.end();

		return simpleJobBuilder
				.split(applicationContext.getBean(TaskExecutor.class))
				.add(flow1)
				.end()
				.build();
	}

}

