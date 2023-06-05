package com.cezarykluczynski.stapi.etl.configuration.job.success;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StepSuccessParameters {

	private Integer maxEntriesRemoved;
	private Double maxEntriesRemovedPercentage;
	private Integer maxEntriesAdded;
	private Double maxEntriesAddedPercentage;

}
