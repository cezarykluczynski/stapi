package com.cezarykluczynski.stapi.model.throttle.repository;

import com.cezarykluczynski.stapi.model.api_key.entity.ApiKey;
import com.cezarykluczynski.stapi.model.api_key.repository.ApiKeyRepository;
import com.cezarykluczynski.stapi.model.configuration.ThrottleProperties;
import com.cezarykluczynski.stapi.model.throttle.dto.ThrottleStatistics;
import com.cezarykluczynski.stapi.model.throttle.entity.Throttle;
import com.cezarykluczynski.stapi.model.throttle.entity.enums.ThrottleType;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ThrottleRepositoryImpl implements ThrottleRepositoryCustom {

	private final ThrottleProperties throttleProperties;

	@Inject
	private ThrottleRepository throttleRepository;

	@Inject
	private ApiKeyRepository apiKeyRepository;

	public ThrottleRepositoryImpl(ThrottleProperties throttleProperties) {
		this.throttleProperties = throttleProperties;
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
	public void deleteExpiredIPLimits() {
		LocalDateTime localDateTime = LocalDateTime.now();
		LocalDateTime thresholdDate = localDateTime.minusMinutes(throttleProperties.getMinutesToDeleteExpiredIpAddresses());
		throttleRepository.deleteIPAddressesOlderThan(thresholdDate);
	}

}
