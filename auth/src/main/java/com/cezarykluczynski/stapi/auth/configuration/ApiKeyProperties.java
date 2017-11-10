package com.cezarykluczynski.stapi.auth.configuration;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = ApiKeyProperties.PREFIX)
public class ApiKeyProperties {

	public static final String PREFIX = "apiKey";

	private int keyLimitPerAccount;

	private int requestLimitPerKey;

	private int adminPageSize;

}
