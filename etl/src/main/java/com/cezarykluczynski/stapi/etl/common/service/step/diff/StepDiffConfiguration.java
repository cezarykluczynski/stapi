package com.cezarykluczynski.stapi.etl.common.service.step.diff;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({StepDiffProperties.class})
public class StepDiffConfiguration {
}
