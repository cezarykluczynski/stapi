package com.cezarykluczynski.stapi.auth.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = AccountProperties.PREFIX)
public class AccountProperties {

	public static final String PREFIX = "account";

	private int adminPageSize;

}
