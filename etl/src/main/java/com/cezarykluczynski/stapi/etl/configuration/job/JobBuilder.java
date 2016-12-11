package com.cezarykluczynski.stapi.etl.configuration.job;

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

	@Inject
	public JobBuilder(ApplicationContext applicationContext, JobBuilderFactory jobBuilderFactory,
			StepConfigurationValidator stepConfigurationValidator) {
		this.applicationContext = applicationContext;
		this.jobBuilderFactory = jobBuilderFactory;
		this.stepConfigurationValidator = stepConfigurationValidator;
	}

	public synchronized Job build() {
		stepConfigurationValidator.validate();

		org.springframework.batch.core.job.builder.JobBuilder jobBuilder = jobBuilderFactory.get(JobName.JOB_CREATE);
		SimpleJobBuilder simpleJobBuilder = new SimpleJobBuilder(jobBuilder);

		Flow flow1 = new FlowBuilder<Flow>("flow1")
				.from(applicationContext.getBean(StepName.CREATE_SERIES, Step.class))
				.end();

		Flow flow2 = new FlowBuilder<Flow>("flow2")
				.from(applicationContext.getBean(StepName.CREATE_PERFORMERS, Step.class))
				.next(applicationContext.getBean(StepName.CREATE_STAFF, Step.class))
				.next(applicationContext.getBean(StepName.CREATE_CHARACTERS, Step.class))
				.next(applicationContext.getBean(StepName.CREATE_EPISODES, Step.class))
				.end();

		return simpleJobBuilder
				.split(applicationContext.getBean(TaskExecutor.class))
				.add(flow1, flow2)
				.end()
				.build();
	}
}

