package com.cezarykluczynski.stapi.server.common.throttle;

import com.google.common.collect.Maps;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.message.Message;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.xml.namespace.QName;
import java.util.Map;

@Service
public class ThrottleFacade {

	private static final String PATH_INFO = "org.apache.cxf.message.Message.PATH_INFO";
	private static final Map<ThrottleReason, String> ERROR_MESSAGES = Maps.newHashMap();

	static {
		ERROR_MESSAGES.put(ThrottleReason.HOURLY_API_KEY_LIMIT_EXCEEDED, "Hourly API limit exceeded");
		ERROR_MESSAGES.put(ThrottleReason.HOURLY_IP_LIMIT_EXCEEDED, "IP limit exceeded");
		ERROR_MESSAGES.put(ThrottleReason.TOO_SHORT_INTERVAL_BETWEEN_REQUESTS, "Too short interval between requests");
	}

	private final ThrottleValidator throttleValidator;

	@Inject
	public ThrottleFacade(ThrottleValidator throttleValidator) {
		this.throttleValidator = throttleValidator;
	}

	public void validate(Message message) {
		ThrottleResult throttleResult = throttleValidator.validate(message);

		if (throttleResult.getThrottle()) {
			pickExceptionAndThrow(message, throttleResult.getThrottleReason());
		}
	}

	private void pickExceptionAndThrow(Message message, ThrottleReason throttleReason) {
		if (isSoapEndpoint(message)) {
			throwSoapException(throttleReason);
		} else if (isRestEndpoint(message)) {
			throwRestException(throttleReason);
		}
	}

	private boolean isRestEndpoint(Message message) {
		return ((String) message.get(PATH_INFO)).contains("/rest/");
	}

	private boolean isSoapEndpoint(Message message) {
		return ((String) message.get(PATH_INFO)).contains("/soap/");
	}

	private void throwRestException(ThrottleReason throttleReason) {
		throw new RestException(mapToMessage(throttleReason), throttleReason);
	}

	private void throwSoapException(ThrottleReason throttleReason) {
		throw new SoapFault(mapToMessage(throttleReason), QName.valueOf(throttleReason.name()));
	}

	private String mapToMessage(ThrottleReason throttleReason) {
		return ERROR_MESSAGES.get(throttleReason);
	}

}
