package com.cezarykluczynski.stapi.server.common.throttle;

public class ThrottleResult {

	static final ThrottleResult NOT_THROTTLED = new ThrottleResult();
	static final ThrottleResult HOURLY_IP_LIMIT_EXCEEDED = throttled(ThrottleReason.HOURLY_IP_LIMIT_EXCEEDED);
	static final ThrottleResult HOURLY_API_KEY_LIMIT_EXCEEDED = throttled(ThrottleReason.HOURLY_API_KEY_LIMIT_EXCEEDED);
	static final ThrottleResult TOO_SHORT_INTERVAL_BETWEEN_REQUESTS = throttled(ThrottleReason.TOO_SHORT_INTERVAL_BETWEEN_REQUESTS);

	private boolean throttle;

	private ThrottleReason throttleReason;

	private static ThrottleResult throttled(ThrottleReason throttleReason) {
		ThrottleResult throttleResult = new ThrottleResult();
		throttleResult.setThrottle(true);
		throttleResult.setThrottleReason(throttleReason);
		return throttleResult;
	}

	public Boolean getThrottle() {
		return throttle;
	}

	public void setThrottle(Boolean throttle) {
		this.throttle = throttle;
	}

	public ThrottleReason getThrottleReason() {
		return throttleReason;
	}

	public void setThrottleReason(ThrottleReason throttleReason) {
		this.throttleReason = throttleReason;
	}
}
