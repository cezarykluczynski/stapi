package com.cezarykluczynski.stapi.model.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = ThrottleProperties.PREFIX)
public class ThrottleProperties {

	public static final String PREFIX = "throttle";

	private Integer ipAddressHourlyLimit;

}
