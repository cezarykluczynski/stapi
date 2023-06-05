package com.cezarykluczynski.stapi.etl.configuration.job.success;

import com.cezarykluczynski.stapi.etl.common.service.step.diff.StepDiff;
import com.cezarykluczynski.stapi.etl.common.service.step.diff.StepDiffProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PreviousStepSuccessValidator {

	private final StepSuccessParametersProvider stepSuccessParametersProvider;
	private final StepDiffProvider stepDiffProvider;

	public boolean wasSuccessful(StepExecution stepExecution) {
		String stepName = stepExecution.getStepName();
		final StepDiff stepDiff = stepDiffProvider.provide(stepExecution);
		final StepSuccessParameters stepSuccessParameters = stepSuccessParametersProvider.getStepSuccessParameters(stepName);
		final int uniquePreviousNamesSize = stepDiff.getUniquePreviousNames().size();
		final int uniqueCurrentNamesSize = stepDiff.getUniqueCurrentNames().size();
		final double uniquePreviousNamesPercentage = 100 * (double) uniquePreviousNamesSize / (double) stepDiff.getPreviousNamesSize();
		final double uniqueCurrentNamesPercentage = 100 * (double) uniqueCurrentNamesSize / (double) stepDiff.getCurrentNamesSize();
		boolean addedOk;
		if (stepSuccessParameters.getMaxEntriesAdded() != null) {
			addedOk = stepSuccessParameters.getMaxEntriesAdded() >= uniqueCurrentNamesSize;
		} else if (stepSuccessParameters.getMaxEntriesAddedPercentage() != null) {
			addedOk = stepSuccessParameters.getMaxEntriesAddedPercentage() >= uniqueCurrentNamesPercentage;
		} else {
			addedOk = true;
		}
		if (!addedOk) {
			log.info("Added entries for step {} were not matched with the required parameters: {}.", stepName, stepSuccessParameters);
		}


		boolean removedOk;
		if (stepSuccessParameters.getMaxEntriesRemoved() != null) {
			removedOk = stepSuccessParameters.getMaxEntriesRemoved() >= uniquePreviousNamesSize;
		} else if (stepSuccessParameters.getMaxEntriesRemovedPercentage() != null) {
			removedOk = stepSuccessParameters.getMaxEntriesRemovedPercentage() >= uniquePreviousNamesPercentage;
		} else {
			removedOk = true;
		}
		if (!removedOk) {
			log.info("Removed entries for step {} were not matched with the required parameters: {}.", stepName, stepSuccessParameters);
		}

		return addedOk && removedOk;
	}

}
