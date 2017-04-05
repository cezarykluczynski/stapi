package com.cezarykluczynski.stapi.server.common.throttle;

import org.apache.cxf.message.Message;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

// TODO: tests
@Service
public class ThrottleFacade {

	private static final String PATH_INFO = "org.apache.cxf.message.Message.PATH_INFO";

	private final ThrottleValidator throttleValidator;

	@Inject
	public ThrottleFacade(ThrottleValidator throttleValidator) {
		this.throttleValidator = throttleValidator;
	}

	public void validate(Message message) {
		ThrottleResult throttleResult = throttleValidator.isValid(message);

		if (!throttleResult.getThrottle()) {
			return;
		}

		if (isSoapEndpoint(message)) {
			throwSoapException(throttleResult.getThrottleReason());
		} else if (isRestEndpoint(message)) {
			throwRestException(throttleResult.getThrottleReason());
		}
	}

	private boolean isRestEndpoint(Message message) {
		return ((String) message.get(PATH_INFO)).contains("/rest/");
	}

	private boolean isSoapEndpoint(Message message) {
		return ((String) message.get(PATH_INFO)).contains("/soap/");
	}

	private void throwRestException(ThrottleReason throttleReason) {
		// TODO
	}

	private void throwSoapException(ThrottleReason throttleReason) {
		// TODO
	}

}
