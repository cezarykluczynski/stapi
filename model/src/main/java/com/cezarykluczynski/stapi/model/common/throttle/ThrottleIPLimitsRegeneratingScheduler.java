package com.cezarykluczynski.stapi.model.common.throttle;

import com.cezarykluczynski.stapi.model.throttle.repository.ThrottleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
@Slf4j
public class ThrottleIPLimitsRegeneratingScheduler {

	private final ThrottleRepository throttleRepository;

	@Inject
	public ThrottleIPLimitsRegeneratingScheduler(ThrottleRepository throttleRepository) {
		this.throttleRepository = throttleRepository;
	}

	@Scheduled(cron = "0 0 * * * *")
	public void regenerate() {
		throttleRepository.regenerateIPAddressesRemainingHits();
		log.info("IP addresses remaining hits were regenerated");
	}

}
