package com.cezarykluczynski.stapi.server.common.throttle;

import com.cezarykluczynski.stapi.model.configuration.ThrottleProperties;
import com.cezarykluczynski.stapi.server.common.service.NowProvider;
import com.cezarykluczynski.stapi.server.common.throttle.credential.RequestCredential;
import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import com.google.common.cache.Cache;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Service
@Profile(SpringProfile.API_THROTTLE)
class FrequentRequestsValidator {

	private final ThrottleProperties throttleProperties;

	private final FrequentRequestsCacheBuilder frequentRequestsCacheBuilder;

	private final FrequentRequestsKeysFactory frequentRequestsKeysFactory;

	private final NowProvider nowProvider;

	private Cache<FrequentRequestsKey, List<LocalDateTime>> cache;

	FrequentRequestsValidator(ThrottleProperties throttleProperties, FrequentRequestsCacheBuilder frequentRequestsCacheBuilder,
			FrequentRequestsKeysFactory frequentRequestsKeysFactory, NowProvider nowProvider) {
		this.throttleProperties = throttleProperties;
		this.frequentRequestsCacheBuilder = frequentRequestsCacheBuilder;
		this.frequentRequestsKeysFactory = frequentRequestsKeysFactory;
		this.nowProvider = nowProvider;
	}

	@PostConstruct
	public void postConstruct() {
		cache = frequentRequestsCacheBuilder.build();
	}

	ThrottleResult validate(RequestCredential requestCredential) {
		Set<FrequentRequestsKey> frequentRequestsKeySet = frequentRequestsKeysFactory.create(requestCredential);
		LocalDateTime now = nowProvider.now();
		LocalDateTime threshold = now.minusSeconds(throttleProperties.getFrequentRequestsPeriodLengthInSeconds());

		try {
			for (FrequentRequestsKey frequentRequestsKey : frequentRequestsKeySet) {
				cache.get(frequentRequestsKey, () -> Collections.synchronizedList(Lists.newArrayList()));

				List<LocalDateTime> callTimes = cache.getIfPresent(frequentRequestsKey);
				if (callTimes != null) {
					callTimes.add(now);
					callTimes.removeIf(localDateTime -> localDateTime.isBefore(threshold));
					if (callTimes.size() > throttleProperties.getFrequentRequestsMaxRequestsPerPeriod()) {
						return ThrottleResult.TOO_SHORT_INTERVAL_BETWEEN_REQUESTS;
					}
				}
			}
		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		}

		return ThrottleResult.NOT_THROTTLED;
	}

}
