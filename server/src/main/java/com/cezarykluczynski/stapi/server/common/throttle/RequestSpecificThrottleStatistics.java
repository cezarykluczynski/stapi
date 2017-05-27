package com.cezarykluczynski.stapi.server.common.throttle;

import com.cezarykluczynski.stapi.model.throttle.dto.ThrottleStatistics;
import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Profile(SpringProfile.API_THROTTLE)
public class RequestSpecificThrottleStatistics {

	private ThrottleStatistics throttleStatistics;

	public ThrottleStatistics getThrottleStatistics() {
		return throttleStatistics;
	}

	void setThrottleStatistics(ThrottleStatistics throttleStatistics) {
		this.throttleStatistics = throttleStatistics;
	}

}
