package com.cezarykluczynski.stapi.model.throttle.repository;

import com.cezarykluczynski.stapi.model.configuration.ThrottleProperties;
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
	public ThrottleRepositoryImpl(ThrottleProperties throttleProperties) {
		this.throttleProperties = throttleProperties;
	}

	@Override
	public boolean decrementByIpAndGetResult(String ipAddress) {
		Optional<Throttle> throttleOptional = throttleRepository.findByIpAddress(ipAddress);

		if (throttleOptional.isPresent()) {
			Throttle throttle = throttleOptional.get();

			if (throttle.getRemainingHits() <= 0) {
				return false;
			}

			throttleRepository.decrementByIp(ipAddress, LocalDateTime.now());
			return true;
		} else {
			Throttle throttle = new Throttle();
			throttle.setThrottleType(ThrottleType.IP_ADDRESS);
			throttle.setIpAddress(ipAddress);
			Integer ipAddressHourlyLimit = throttleProperties.getIpAddressHourlyLimit();
			throttle.setHitsLimit(ipAddressHourlyLimit);
			throttle.setRemainingHits(ipAddressHourlyLimit - 1);
			throttle.setLastHitTime(LocalDateTime.now());
			try {
				throttleRepository.save(throttle);
			} catch (DataIntegrityViolationException exception) {
				throttleRepository.decrementByIp(ipAddress, LocalDateTime.now());
			}

			return true;
		}
	}

	@Override
	public void regenerateIPAddressesRemainingHits() {
		throttleRepository.regenerateIPAddressesWithRemainingHits(throttleProperties.getIpAddressHourlyLimit());
	}

	@Override
	public void deleteExpiredIPLimits() {
		LocalDateTime localDateTime = LocalDateTime.now();
		LocalDateTime thresholdDate = localDateTime.minusDays(throttleProperties.getDaysToDeleteExpiredIpAddresses());
		throttleRepository.deleteIPAddressesOlderThan(thresholdDate);
	}

}
