package com.cezarykluczynski.stapi.model.common.cache;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = CacheProperties.PREFIX)
public class CacheProperties {

	public static final String PREFIX = "cache";

	private CachingStrategyType cachingStrategyType;

}
