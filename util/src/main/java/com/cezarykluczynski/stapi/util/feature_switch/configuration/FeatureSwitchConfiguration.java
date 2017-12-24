package com.cezarykluczynski.stapi.util.feature_switch.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(FeatureSwitchProperties.class)
public class FeatureSwitchConfiguration {
}
