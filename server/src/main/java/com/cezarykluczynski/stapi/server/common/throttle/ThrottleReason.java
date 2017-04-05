package com.cezarykluczynski.stapi.server.common.throttle;

public enum ThrottleReason {

	HOURLY_API_KEY_LIMIT_EXCEEDED,
	HOURLY_IP_LIMIT_EXCEEDED,
	TOO_SHORT_INTERVAL_BETWEEN_REQUESTS

}
