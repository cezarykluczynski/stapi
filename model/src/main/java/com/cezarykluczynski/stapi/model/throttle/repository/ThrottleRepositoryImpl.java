package com.cezarykluczynski.stapi.model.throttle.repository;

import com.cezarykluczynski.stapi.model.api_key.entity.ApiKey;
import com.cezarykluczynski.stapi.model.api_key.entity.ApiKey_;
import com.cezarykluczynski.stapi.model.api_key.query.ApiKeyQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.api_key.repository.ApiKeyRepository;
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.configuration.ThrottleProperties;
import com.cezarykluczynski.stapi.model.throttle.dto.ThrottleStatistics;
import com.cezarykluczynski.stapi.model.throttle.entity.Throttle;
import com.cezarykluczynski.stapi.model.throttle.entity.enums.ThrottleType;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ThrottleRepositoryImpl implements ThrottleRepositoryCustom {

	private final ThrottleProperties throttleProperties;

	private final ApiKeyQueryBuilderFactory apiKeyQueryBuilderFactory;

	@Inject
	private ThrottleRepository throttleRepository;

	@Inject
	private ApiKeyRepository apiKeyRepository;

	public ThrottleRepositoryImpl(ThrottleProperties throttleProperties, ApiKeyQueryBuilderFactory apiKeyQueryBuilderFactory) {
		this.throttleProperties = throttleProperties;
		this.apiKeyQueryBuilderFactory = apiKeyQueryBuilderFactory;
	}

	@Override
	public ThrottleStatistics decrementByIpAndGetStatistics(String ipAddress) {
		Integer ipAddressHourlyLimit = throttleProperties.getIpAddressHourlyLimit();
		return decrementAndGetStatistics(ipAddress, null, ipAddressHourlyLimit, ThrottleType.IP_ADDRESS);
	}

	@Override
	public ThrottleStatistics decrementByApiKeyAndGetStatistics(String apiKey, String ipAddress) {
		Optional<ApiKey> apiKeyOptional = apiKeyRepository.findByApiKey(apiKey);
		if (!apiKeyOptional.isPresent()) {
			return decrementByIpAndGetStatistics(ipAddress);
		}
		return decrementAndGetStatistics(ipAddress, apiKey, apiKeyOptional.get().getLimit(), ThrottleType.API_KEY);
	}

	private ThrottleStatistics decrementAndGetStatistics(String ipAddress, String apiKey, Integer hourlyLimit, ThrottleType throttleType) {
		ThrottleStatistics throttleStatistics = new ThrottleStatistics();
		throttleStatistics.setTotal(hourlyLimit);

		Optional<Throttle> throttleOptional;
		if (ThrottleType.IP_ADDRESS.equals(throttleType)) {
			throttleOptional = throttleRepository.findByIpAddress(ipAddress);
		} else {
			throttleOptional = throttleRepository.findByApiKeyAndActiveTrue(apiKey);
		}

		if (throttleOptional.isPresent()) {
			Throttle throttle = throttleOptional.get();
			int remainingHits = throttle.getRemainingHits();

			if (remainingHits <= 0) {
				throttleStatistics.setRemaining(0);
				return throttleStatistics;
			}

			if (ThrottleType.IP_ADDRESS.equals(throttleType)) {
				throttleRepository.decrementByIp(ipAddress, LocalDateTime.now());
			} else {
				throttleRepository.decrementByApiKey(apiKey, LocalDateTime.now());
			}
			throttleStatistics.setRemaining(remainingHits - 1);
			throttleStatistics.setDecremented(true);
			return throttleStatistics;
		} else {
			Throttle throttle = new Throttle();
			throttle.setThrottleType(throttleType);
			throttle.setIpAddress(ipAddress);
			throttle.setHitsLimit(hourlyLimit);
			throttle.setRemainingHits(hourlyLimit - 1);
			throttle.setLastHitTime(LocalDateTime.now());
			try {
				throttleRepository.save(throttle);
			} catch (DataIntegrityViolationException exception) {
				throttleRepository.decrementByIp(ipAddress, LocalDateTime.now());
			}

			throttleStatistics.setRemaining(hourlyLimit - 1);
			throttleStatistics.setDecremented(true);

			return throttleStatistics;
		}
	}

	@Override
	public void regenerateIPAddressesRemainingHits() {
		throttleRepository.regenerateIPAddressesWithRemainingHits(throttleProperties.getIpAddressHourlyLimit());
	}

	@Override
	@Transactional
	public void regenerateApiKeysRemainingHits() {
		QueryBuilder<ApiKey> apiKeyQueryBuilder = createApiKeyQueryBuilderWithThrottlesFetched();
		List<ApiKey> apiKeyList = apiKeyQueryBuilder.findAll();
		List<Throttle> throttleList = getThrottlesWithRegeneratedLimits(apiKeyList);
		throttleRepository.save(throttleList);
	}

	@Override
	public void deleteExpiredIPLimits() {
		LocalDateTime localDateTime = LocalDateTime.now();
		LocalDateTime thresholdDate = localDateTime.minusMinutes(throttleProperties.getMinutesToDeleteExpiredIpAddresses());
		throttleRepository.deleteIPAddressesOlderThan(thresholdDate);
	}

	private QueryBuilder<ApiKey> createApiKeyQueryBuilderWithThrottlesFetched() {
		QueryBuilder<ApiKey> apiKeyQueryBuilder = apiKeyQueryBuilderFactory.createQueryBuilder(new PageRequest(0, Integer.MAX_VALUE));
		apiKeyQueryBuilder.fetch(ApiKey_.throttle);
		return apiKeyQueryBuilder;
	}

	private List<Throttle> getThrottlesWithRegeneratedLimits(List<ApiKey> apiKeyList) {
		return apiKeyList.stream()
				.peek(this::regeneratedApiKeysThrottle)
				.map(ApiKey::getThrottle)
				.collect(Collectors.toList());
	}

	private void regeneratedApiKeysThrottle(ApiKey apiKey) {
		int limit = apiKey.getLimit();
		apiKey.getThrottle().setRemainingHits(limit);
	}

}
