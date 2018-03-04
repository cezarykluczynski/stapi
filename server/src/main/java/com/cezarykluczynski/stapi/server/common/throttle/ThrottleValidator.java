package com.cezarykluczynski.stapi.server.common.throttle;

import com.cezarykluczynski.stapi.model.configuration.ThrottleProperties;
import com.cezarykluczynski.stapi.model.throttle.dto.ThrottleStatistics;
import com.cezarykluczynski.stapi.model.throttle.repository.ThrottleRepository;
import com.cezarykluczynski.stapi.server.common.throttle.credential.RequestCredential;
import com.cezarykluczynski.stapi.server.common.throttle.credential.RequestCredentialProvider;
import com.cezarykluczynski.stapi.server.common.throttle.credential.RequestCredentialType;
import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;
import org.apache.cxf.message.Message;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(SpringProfile.API_THROTTLE)
public class ThrottleValidator {

	private final ThrottleProperties throttleProperties;

	private final RequestCredentialProvider requestCredentialProvider;

	private final FrequentRequestsValidator frequentRequestsValidator;

	private final ThrottleQualifyingService throttleQualifyingService;

	private final ThrottleRepository throttleRepository;

	private final RequestSpecificThrottleStatistics requestSpecificThrottleStatistics;

	public ThrottleValidator(ThrottleProperties throttleProperties, RequestCredentialProvider requestCredentialProvider,
			FrequentRequestsValidator frequentRequestsValidator, ThrottleQualifyingService throttleQualifyingService,
			ThrottleRepository throttleRepository, RequestSpecificThrottleStatistics requestSpecificThrottleStatistics) {
		this.throttleProperties = throttleProperties;
		this.requestCredentialProvider = requestCredentialProvider;
		this.frequentRequestsValidator = frequentRequestsValidator;
		this.throttleQualifyingService = throttleQualifyingService;
		this.throttleRepository = throttleRepository;
		this.requestSpecificThrottleStatistics = requestSpecificThrottleStatistics;
	}

	ThrottleResult validate(Message message) {
		boolean validateFrequentRequests = throttleProperties.isValidateFrequentRequests();
		boolean throttleIpAddresses = throttleProperties.isThrottleIpAddresses();
		boolean throttleApiKey = throttleProperties.isThrottleApiKey();

		if (!validateFrequentRequests && !throttleIpAddresses && !throttleApiKey) {
			return ThrottleResult.NOT_THROTTLED;
		}

		RequestCredential requestCredential = requestCredentialProvider.provideRequestCredential(message);
		ThrottleResult tooMuchThrottleResult = frequentRequestsValidator.validate(requestCredential);

		if (validateFrequentRequests && Boolean.TRUE.equals(tooMuchThrottleResult.getThrottle())) {
			return tooMuchThrottleResult;
		} else if ((throttleIpAddresses || throttleApiKey) && throttleQualifyingService.isQualifiedForThrottle()) {
			RequestCredentialType requestCredentialType = requestCredential.getRequestCredentialType();
			if (RequestCredentialType.IP_ADDRESS.equals(requestCredentialType)) {
				if (throttleIpAddresses) {
					return validateByIp(requestCredential);
				}
			} else if (RequestCredentialType.API_KEY.equals(requestCredentialType)) {
				if (throttleApiKey) {
					return validateByApiKey(requestCredential);
				}
			} else {
				throw new StapiRuntimeException(String.format("Validation not implemented for RequestCredentialType %s", requestCredentialType));
			}
		}

		return ThrottleResult.NOT_THROTTLED;
	}

	private ThrottleResult validateByIp(RequestCredential requestCredential) {
		ThrottleStatistics throttleStatistics = throttleRepository.decrementByIpAndGetStatistics(requestCredential.getIpAddress());
		requestSpecificThrottleStatistics.setThrottleStatistics(throttleStatistics);
		return throttleStatistics.isDecremented() ? ThrottleResult.NOT_THROTTLED : ThrottleResult.HOURLY_IP_LIMIT_EXCEEDED;
	}

	private ThrottleResult validateByApiKey(RequestCredential requestCredential) {
		ThrottleStatistics throttleStatistics = throttleRepository
				.decrementByApiKeyAndGetStatistics(requestCredential.getApiKey(), requestCredential.getIpAddress());
		requestSpecificThrottleStatistics.setThrottleStatistics(throttleStatistics);
		return throttleStatistics.isDecremented() ? ThrottleResult.NOT_THROTTLED : ThrottleResult.HOURLY_API_KEY_LIMIT_EXCEEDED;
	}

}
