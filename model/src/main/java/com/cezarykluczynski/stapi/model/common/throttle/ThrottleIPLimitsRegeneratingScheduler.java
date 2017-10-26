package com.cezarykluczynski.stapi.model.common.throttle;

import com.cezarykluczynski.stapi.model.throttle.repository.ThrottleRepository;
import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Profile(SpringProfile.API_THROTTLE)
public class ThrottleIPLimitsRegeneratingScheduler {

	private final ThrottleRepository throttleRepository;

	public ThrottleIPLimitsRegeneratingScheduler(ThrottleRepository throttleRepository) {
		this.throttleRepository = throttleRepository;
	}

	@Scheduled(cron = "0 0 * * * *")
	public void regenerate() {
		throttleRepository.regenerateIPAddressesRemainingHits();
		log.info("IP addresses remaining hits were regenerated");
	}

}
