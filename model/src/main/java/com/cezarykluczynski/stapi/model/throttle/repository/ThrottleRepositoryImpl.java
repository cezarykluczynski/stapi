package com.cezarykluczynski.stapi.model.throttle.repository;

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

	public ThrottleRepositoryImpl(ThrottleProperties throttleProperties) {
		this.throttleProperties = throttleProperties;
	}

	@Override
	public ThrottleStatistics decrementByIpAndGetStatistics(String ipAddress) {
		ThrottleStatistics throttleStatistics = new ThrottleStatistics();
		Integer ipAddressHourlyLimit = throttleProperties.getIpAddressHourlyLimit();
		throttleStatistics.setTotal(ipAddressHourlyLimit);

		Optional<Throttle> throttleOptional = throttleRepository.findByIpAddress(ipAddress);

		if (throttleOptional.isPresent()) {
			Throttle throttle = throttleOptional.get();
			int remainingHits = throttle.getRemainingHits();

			if (remainingHits <= 0) {
				throttleStatistics.setRemaining(0);
				return throttleStatistics;
			}

			throttleRepository.decrementByIp(ipAddress, LocalDateTime.now());
			throttleStatistics.setRemaining(remainingHits - 1);
			throttleStatistics.setDecremented(true);
			return throttleStatistics;
		} else {
			Throttle throttle = new Throttle();
			throttle.setThrottleType(ThrottleType.IP_ADDRESS);
			throttle.setIpAddress(ipAddress);
			throttle.setHitsLimit(ipAddressHourlyLimit);
			throttle.setRemainingHits(ipAddressHourlyLimit - 1);
			throttle.setLastHitTime(LocalDateTime.now());
			try {
				throttleRepository.save(throttle);
			} catch (DataIntegrityViolationException exception) {
				throttleRepository.decrementByIp(ipAddress, LocalDateTime.now());
			}

			throttleStatistics.setRemaining(ipAddressHourlyLimit - 1);
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
