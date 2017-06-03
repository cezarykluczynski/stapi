package com.cezarykluczynski.stapi.etl.configuration.job.service;

import com.cezarykluczynski.stapi.etl.util.constant.StepNames;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobCompletenessDecider {

	private final AllStepExecutionsProvider allStepExecutionsProvider;

	public JobCompletenessDecider(AllStepExecutionsProvider allStepExecutionsProvider) {
		this.allStepExecutionsProvider = allStepExecutionsProvider;
	}

	public boolean isJobCompleted(String jobName) {
		List<StepExecution> stepExecutionList = allStepExecutionsProvider.provide(jobName);

		return stepExecutionList.stream()
				.map(StepExecution::getStatus)
				.allMatch(BatchStatus.COMPLETED::equals) && stepExecutionList.size() == StepNames.JOB_STEPS.get(jobName).size();
	}
}
