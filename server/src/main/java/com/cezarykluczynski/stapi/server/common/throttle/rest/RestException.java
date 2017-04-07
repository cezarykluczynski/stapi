package com.cezarykluczynski.stapi.server.common.throttle.rest;

import com.cezarykluczynski.stapi.server.common.throttle.ThrottleReason;

public class RestException extends RuntimeException {

	private final ThrottleReason throttleReason;

	public RestException(String message, ThrottleReason throttleReason) {
		super(message);
		this.throttleReason = throttleReason;
	}

	public ThrottleReason getThrottleReason() {
		return throttleReason;
	}
}
