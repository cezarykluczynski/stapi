package com.cezarykluczynski.stapi.model.common.throttle;

import com.cezarykluczynski.stapi.model.throttle.repository.ThrottleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
@Slf4j
public class ExpiredIPLimitsDeletingScheduler {

	private final ThrottleRepository throttleRepository;

	@Inject
	public ExpiredIPLimitsDeletingScheduler(ThrottleRepository throttleRepository) {
		this.throttleRepository = throttleRepository;
	}

	@Scheduled(cron = "0 30 * * * *")
	public void delete() {
		throttleRepository.deleteExpiredIPLimits();
		log.info("Expired entries for IP limits were deleted");
	}

}
