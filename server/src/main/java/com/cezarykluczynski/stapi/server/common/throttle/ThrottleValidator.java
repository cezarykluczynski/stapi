package com.cezarykluczynski.stapi.server.common.throttle;

import com.cezarykluczynski.stapi.model.throttle.dto.ThrottleStatistics;
import com.cezarykluczynski.stapi.model.throttle.repository.ThrottleRepository;
import com.cezarykluczynski.stapi.server.common.throttle.credential.RequestCredential;
import com.cezarykluczynski.stapi.server.common.throttle.credential.RequestCredentialProvider;
import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import org.apache.cxf.message.Message;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(SpringProfile.API_THROTTLE)
public class ThrottleValidator {

	private final RequestCredentialProvider requestCredentialProvider;

	private final FrequentRequestsValidator frequentRequestsValidator;

	private final ThrottleQualifyingService throttleQualifyingService;

	private final ThrottleRepository throttleRepository;

	private final RequestSpecificThrottleStatistics requestSpecificThrottleStatistics;

	public ThrottleValidator(RequestCredentialProvider requestCredentialProvider, FrequentRequestsValidator frequentRequestsValidator,
			ThrottleQualifyingService throttleQualifyingService, ThrottleRepository throttleRepository,
			RequestSpecificThrottleStatistics requestSpecificThrottleStatistics) {
		this.requestCredentialProvider = requestCredentialProvider;
		this.frequentRequestsValidator = frequentRequestsValidator;
		this.throttleQualifyingService = throttleQualifyingService;
		this.throttleRepository = throttleRepository;
		this.requestSpecificThrottleStatistics = requestSpecificThrottleStatistics;
	}

	ThrottleResult validate(Message message) {
		RequestCredential requestCredential = requestCredentialProvider.provideRequestCredential(message);
		ThrottleResult tooMuchThrottleResult = frequentRequestsValidator.validate(requestCredential);

		if (Boolean.TRUE.equals(tooMuchThrottleResult.getThrottle())) {
			return tooMuchThrottleResult;
		} else if (throttleQualifyingService.isQualifiedForThrottle()) {
			return validateByIp(requestCredential);
		} else {
			return ThrottleResult.NOT_THROTTLED;
		}

	}

	private ThrottleResult validateByIp(RequestCredential requestCredential) {
		ThrottleStatistics throttleStatistics = throttleRepository.decrementByIpAndGetStatistics(requestCredential.getIpAddress());
		requestSpecificThrottleStatistics.setThrottleStatistics(throttleStatistics);
		return throttleStatistics.isDecremented() ? ThrottleResult.NOT_THROTTLED : ThrottleResult.HOURLY_IP_LIMIT_EXCEEDED;
	}

}
