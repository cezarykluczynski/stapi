package com.cezarykluczynski.stapi.etl.common.service.step.diff;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "etl.diff-logger-after-step")
public class StepDiffProperties {

	private boolean enabled;

	private String previousVersionUrl;

	private String currentVersionUrl;

}
