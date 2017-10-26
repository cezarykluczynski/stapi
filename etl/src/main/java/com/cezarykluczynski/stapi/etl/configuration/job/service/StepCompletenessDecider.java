package com.cezarykluczynski.stapi.etl.configuration.job.service;

import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepProperties;
import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepToStepPropertiesProvider;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

@Service
public class StepCompletenessDecider {

	private final StepToStepPropertiesProvider stepToStepPropertiesProvider;

	private final AllStepExecutionsProvider allStepExecutionsProvider;

	public StepCompletenessDecider(StepToStepPropertiesProvider stepToStepPropertiesProvider,
			AllStepExecutionsProvider allStepExecutionsProvider) {
		this.stepToStepPropertiesProvider = stepToStepPropertiesProvider;
		this.allStepExecutionsProvider = allStepExecutionsProvider;
	}

	public boolean isStepComplete(String jobName, String stepName) {
		Map<String, StepProperties> stepPropertiesMap = stepToStepPropertiesProvider.provide();
		StepProperties stepProperties = stepPropertiesMap.get(stepName);

		if (stepProperties != null && !stepProperties.isEnabled()) {
			return true;
		}

		Collection<StepExecution> stepExecutions = allStepExecutionsProvider.provide(jobName);
		return stepExecutions.stream().anyMatch(stepExecution ->
			stepExecution.getStepName().equals(stepName) && BatchStatus.COMPLETED.equals(stepExecution.getStatus()));
	}

}
