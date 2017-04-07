package com.cezarykluczynski.stapi.server.common.throttle;

import com.cezarykluczynski.stapi.model.throttle.repository.ThrottleRepository;
import com.cezarykluczynski.stapi.server.common.throttle.credential.RequestCredential;
import com.cezarykluczynski.stapi.server.common.throttle.credential.RequestCredentialProvider;
import org.apache.cxf.message.Message;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ThrottleValidator {

	private final ThrottleRepository throttleRepository;

	private final RequestCredentialProvider requestCredentialProvider;

	@Inject
	public ThrottleValidator(ThrottleRepository throttleRepository, RequestCredentialProvider requestCredentialProvider) {
		this.throttleRepository = throttleRepository;
		this.requestCredentialProvider = requestCredentialProvider;
	}

	ThrottleResult validate(Message message) {
		RequestCredential requestCredential = requestCredentialProvider.provideRequestCredential();
		return validateByIp(requestCredential);
	}

	private ThrottleResult validateByIp(RequestCredential requestCredential) {
		boolean decrementResult = throttleRepository.decrementByIpAndGetResult(requestCredential.getIpAddress());

		ThrottleResult throttleResult = new ThrottleResult();

		if (!decrementResult) {
			throttleResult.setThrottle(true);
			throttleResult.setThrottleReason(ThrottleReason.HOURLY_IP_LIMIT_EXCEEDED);
			return throttleResult;
		}

		throttleResult.setThrottle(false);
		return throttleResult;
	}

}
