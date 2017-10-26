package com.cezarykluczynski.stapi.server.common.throttle;

import com.cezarykluczynski.stapi.model.configuration.ThrottleProperties;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
class FrequentRequestsCacheBuilder {

	private final ThrottleProperties throttleProperties;

	private Boolean built = false;

	FrequentRequestsCacheBuilder(ThrottleProperties throttleProperties) {
		this.throttleProperties = throttleProperties;
	}

	Cache<FrequentRequestsKey, List<LocalDateTime>> build() {
		synchronized (this) {
			if (built) {
				throw new IllegalStateException("ToMuchRequestsCacheBuilder already built cache");
			}

			built = true;

			int frequentRequestsPeriodLengthInSeconds = throttleProperties.getFrequentRequestsPeriodLengthInSeconds();

			return CacheBuilder.newBuilder()
				.concurrencyLevel(50)
				.expireAfterWrite(frequentRequestsPeriodLengthInSeconds * 2, TimeUnit.SECONDS)
				.expireAfterAccess(frequentRequestsPeriodLengthInSeconds * 3, TimeUnit.SECONDS)
				.build();
		}
	}


}
