package com.cezarykluczynski.stapi.etl.configuration.job.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = StepsProperties.PREFIX)
public class StepsProperties {

	public static final String PREFIX = "step";

	private StepProperties createSeries;

	private StepProperties createPerformers;

	private StepProperties createStaff;

	private StepProperties createCharacters;

	private StepProperties createEpisodes;

	private StepProperties createMovies;

}
