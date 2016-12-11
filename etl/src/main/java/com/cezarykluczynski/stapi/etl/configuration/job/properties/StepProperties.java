package com.cezarykluczynski.stapi.etl.configuration.job.properties;

import lombok.Data;

@Data
public class StepProperties {

	private boolean enabled;

	private int commitInterval;

	private int order;

}
