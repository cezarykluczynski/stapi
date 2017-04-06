package com.cezarykluczynski.stapi.server.common.throttle;

class RestException extends RuntimeException {

	private final ThrottleReason throttleReason;

	RestException(String message, ThrottleReason throttleReason) {
		super(message);
		this.throttleReason = throttleReason;
	}

	ThrottleReason getThrottleReason() {
		return throttleReason;
	}
}
