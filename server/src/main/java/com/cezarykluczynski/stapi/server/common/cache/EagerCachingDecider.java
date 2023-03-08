package com.cezarykluczynski.stapi.server.common.cache;

import com.cezarykluczynski.stapi.util.constant.EnvironmentVariable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class EagerCachingDecider {

	@SuppressWarnings("ConstantName")
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(EagerCachingDecider.class);

	private final boolean eagerCachingEnabled;

	public EagerCachingDecider(Environment environment, @Value("${cache.eagerCachingEnabled}") Boolean eagerCachingEnabledProperty) {
		boolean isStapiCoCanonicalDomain = "stapi.co".equals(environment.getProperty(EnvironmentVariable.STAPI_CANONICAL_DOMAIN));
		boolean isEagerCachingEnabledEnv = "true".equalsIgnoreCase(environment.getProperty(EnvironmentVariable.STAPI_EAGER_CACHING_ENABLED));
		eagerCachingEnabled = isStapiCoCanonicalDomain || isEagerCachingEnabledEnv || Boolean.TRUE.equals(eagerCachingEnabledProperty);
		log.info("Eager caching is {}.", eagerCachingEnabled ? "enabled" : "disabled");
	}

	public boolean isEagerCachingEnabled() {
		return eagerCachingEnabled;
	}

}
