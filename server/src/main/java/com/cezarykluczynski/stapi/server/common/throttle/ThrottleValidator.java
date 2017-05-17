package com.cezarykluczynski.stapi.server.common.throttle;

import com.cezarykluczynski.stapi.model.throttle.dto.ThrottleStatistics;
import com.cezarykluczynski.stapi.model.throttle.repository.ThrottleRepository;
import com.cezarykluczynski.stapi.server.common.throttle.credential.RequestCredential;
import com.cezarykluczynski.stapi.server.common.throttle.credential.RequestCredentialProvider;
import org.apache.cxf.message.Message;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ThrottleValidator {

	private final ThrottleQualifyingService throttleQualifyingService;

	private final ThrottleRepository throttleRepository;

	private final RequestCredentialProvider requestCredentialProvider;

	private final RequestSpecificThrottleStatistics requestSpecificThrottleStatistics;

	@Inject
	public ThrottleValidator(ThrottleQualifyingService throttleQualifyingService, ThrottleRepository throttleRepository,
			RequestCredentialProvider requestCredentialProvider, RequestSpecificThrottleStatistics requestSpecificThrottleStatistics) {
		this.throttleQualifyingService = throttleQualifyingService;
		this.throttleRepository = throttleRepository;
		this.requestCredentialProvider = requestCredentialProvider;
		this.requestSpecificThrottleStatistics = requestSpecificThrottleStatistics;
	}

	ThrottleResult validate(Message message) {
		if (throttleQualifyingService.isQualifiedForThrottle()) {
			RequestCredential requestCredential = requestCredentialProvider.provideRequestCredential(message);
			return validateByIp(requestCredential);
		} else {
			return ThrottleResult.NOT_THROTTLED;
		}
	}

	private ThrottleResult validateByIp(RequestCredential requestCredential) {
		ThrottleStatistics throttleStatistics = throttleRepository.decrementByIpAndGetStatistics(requestCredential.getIpAddress());
		requestSpecificThrottleStatistics.setThrottleStatistics(throttleStatistics);

		ThrottleResult throttleResult = new ThrottleResult();

		if (!throttleStatistics.isDecremented()) {
			throttleResult.setThrottle(true);
			throttleResult.setThrottleReason(ThrottleReason.HOURLY_IP_LIMIT_EXCEEDED);
			return throttleResult;
		}

		throttleResult.setThrottle(false);
		return throttleResult;
	}

}
