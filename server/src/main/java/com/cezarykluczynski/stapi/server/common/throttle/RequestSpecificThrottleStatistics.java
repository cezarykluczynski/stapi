package com.cezarykluczynski.stapi.server.common.throttle;

import com.cezarykluczynski.stapi.model.throttle.dto.ThrottleStatistics;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RequestSpecificThrottleStatistics {

	private ThrottleStatistics throttleStatistics;

	public ThrottleStatistics getThrottleStatistics() {
		return throttleStatistics;
	}

	void setThrottleStatistics(ThrottleStatistics throttleStatistics) {
		this.throttleStatistics = throttleStatistics;
	}

}
