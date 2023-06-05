package com.cezarykluczynski.stapi.etl.configuration.job;

import com.cezarykluczynski.stapi.etl.common.backup.AfterLatestStepJobExecutionListener;
import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepProperties;
import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepToStepPropertiesProvider;
import com.cezarykluczynski.stapi.etl.configuration.job.service.JobCompletenessDecider;
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider;
import com.cezarykluczynski.stapi.etl.util.constant.JobName;
import com.cezarykluczynski.stapi.etl.util.constant.StepNames;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.SimpleJobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class JobBuilder {

	private final ApplicationContext applicationContext;

	private final JobBuilderFactory jobBuilderFactory;

	private final StepConfigurationValidator stepConfigurationValidator;

	private final JobCompletenessDecider jobCompletenessDecider;

	private final StepToStepPropertiesProvider stepToStepPropertiesProvider;

	private final StepCompletenessDecider stepCompletenessDecider;

	private final AtomicInteger increment = new AtomicInteger(1);

	public synchronized Job buildNext() {
		stepConfigurationValidator.validate();

		if (jobCompletenessDecider.isJobCompleted(JobName.JOB_CREATE)) {
			return null;
		}

		String jobName = String.format("create%d", increment.get());
		FlowBuilder<Flow> flowBuilder = new FlowBuilder<>(jobName);
		Map<String, StepProperties> stepPropertiesMap = stepToStepPropertiesProvider.provide();

		List<String> stepNameList = StepNames.JOB_STEPS.get(JobName.JOB_CREATE);
		boolean stepToExecuteFound = true;

		for (String stepName : stepNameList) {
			if (stepPropertiesMap.get(stepName).isEnabled() && !stepCompletenessDecider.isStepComplete(JobName.JOB_CREATE, stepName)) {
				stepToExecuteFound = false;
				Step step = applicationContext.getBean(stepName, Step.class);
				flowBuilder.from(step);
				break;
			}
		}

		if (stepToExecuteFound) {
			return null;
		}

		increment.incrementAndGet();

		return new SimpleJobBuilder(jobBuilderFactory.get(JobName.JOB_CREATE))
				.split(applicationContext.getBean(SimpleAsyncTaskExecutor.class))
				.add(flowBuilder.build())
				.end()
				.listener(applicationContext.getBean(AfterLatestStepJobExecutionListener.class))
				.build();
	}

}

