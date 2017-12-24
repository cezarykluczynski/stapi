package com.cezarykluczynski.stapi.util.feature_switch.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@ConfigurationProperties
public class FeatureSwitchProperties {

	public static final String PREFIX = "featureSwitch";

	private Map<String, Boolean> featureSwitch;

}
