package com.cezarykluczynski.stapi.model.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = ThrottleProperties.PREFIX)
public class ThrottleProperties {

	public static final String PREFIX = "throttle";

	private boolean validateFrequentRequests;

	private boolean throttleIpAddresses;

	private boolean throttleApiKey;

	private Integer ipAddressHourlyLimit;

	private Integer minutesToDeleteExpiredIpAddresses;

	private Integer frequentRequestsPeriodLengthInSeconds;

	private Integer frequentRequestsMaxRequestsPerPeriod;

}
