package com.cezarykluczynski.stapi.etl.configuration.job.success;

import com.cezarykluczynski.stapi.etl.util.constant.StepName;
import com.google.common.collect.ImmutableMap;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class StepSuccessParametersProvider {

	private static final StepSuccessParameters DEFAULT = StepSuccessParameters.builder()
			.maxEntriesRemoved(0)
			.maxEntriesAddedPercentage(1d)
			.build();

	private static final Map<String, StepSuccessParameters> PARAMETERS = ImmutableMap.<String, StepSuccessParameters>builder()
			.put(StepName.CREATE_COMPANIES, StepSuccessParameters.builder()
					.maxEntriesRemoved(2)
					.maxEntriesAddedPercentage(3d)
					.build())
			.put(StepName.CREATE_SERIES, StepSuccessParameters.builder()
					.maxEntriesRemoved(0)
					.maxEntriesAdded(1)
					.build())
			.put(StepName.CREATE_SEASONS, StepSuccessParameters.builder()
					.maxEntriesRemoved(0)
					.maxEntriesAdded(3)
					.build())
			.put(StepName.CREATE_PERFORMERS, StepSuccessParameters.builder()
					.maxEntriesRemovedPercentage(0.3d)
					.maxEntriesAddedPercentage(2d)
					.build())
			.put(StepName.CREATE_STAFF, StepSuccessParameters.builder()
					.maxEntriesRemovedPercentage(0.3d)
					.maxEntriesAddedPercentage(2d)
					.build())
			.build();

	@SuppressFBWarnings("EI_EXPOSE_REP")
	public StepSuccessParameters getStepSuccessParameters(String stepName) {
		if (PARAMETERS.containsKey(stepName)) {
			return PARAMETERS.get(stepName);
		}
		return DEFAULT;
	}

}
