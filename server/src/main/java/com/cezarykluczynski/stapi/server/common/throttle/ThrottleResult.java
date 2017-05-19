package com.cezarykluczynski.stapi.server.common.throttle;

public class ThrottleResult {

	static final ThrottleResult NOT_THROTTLED = new ThrottleResult();

	private boolean throttle;

	private ThrottleReason throttleReason;

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
