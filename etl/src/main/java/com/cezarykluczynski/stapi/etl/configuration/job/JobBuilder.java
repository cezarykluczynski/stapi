package com.cezarykluczynski.stapi.etl.configuration.job;

import com.cezarykluczynski.stapi.etl.common.backup.BackupAfterStepJobExecutionListener;
import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepProperties;
import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepToStepPropertiesProvider;
import com.cezarykluczynski.stapi.etl.configuration.job.service.JobCompletenessDecider;
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

@Service
@RequiredArgsConstructor
public class JobBuilder {

	private final ApplicationContext applicationContext;

	private final JobBuilderFactory jobBuilderFactory;

	private final StepConfigurationValidator stepConfigurationValidator;

	private final JobCompletenessDecider jobCompletenessDecider;

	private final StepToStepPropertiesProvider stepToStepPropertiesProvider;

	private final BackupAfterStepJobExecutionListener backupAfterStepJobExecutionListener;

	public synchronized Job build() {
		stepConfigurationValidator.validate();

		if (jobCompletenessDecider.isJobCompleted(JobName.JOB_CREATE)) {
			return null;
		}

		org.springframework.batch.core.job.builder.JobBuilder jobBuilder = jobBuilderFactory.get(JobName.JOB_CREATE);
		SimpleJobBuilder simpleJobBuilder = new SimpleJobBuilder(jobBuilder);

		FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("create");
		Map<String, StepProperties> stepPropertiesMap = stepToStepPropertiesProvider.provide();

		List<String> stepNameList = StepNames.JOB_STEPS.get(JobName.JOB_CREATE);
		boolean fromCalled = false;
		boolean allStepsAreDisabled = true;

		for (String stepName : stepNameList) {
			if (stepPropertiesMap.get(stepName).isEnabled()) {
				allStepsAreDisabled = false;
				Step step = applicationContext.getBean(stepName, Step.class);
				if (fromCalled) {
					flowBuilder.next(step);
				} else {
					fromCalled = true;
					flowBuilder.from(step);
				}
			}
		}

		return allStepsAreDisabled ? null : simpleJobBuilder
				.split(applicationContext.getBean(SimpleAsyncTaskExecutor.class))
				.add(flowBuilder.build())
				.end()
				.listener(backupAfterStepJobExecutionListener)
				.build();
	}

}

