package com.cezarykluczynski.stapi.server

import com.cezarykluczynski.stapi.server.common.throttle.RequestSpecificThrottleStatistics
import com.cezarykluczynski.stapi.util.constant.SpringProfile
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
class TestConfiguration {

	@Bean
	@Profile(SpringProfile.API_THROTTLE_NOT)
	RequestSpecificThrottleStatistics requestSpecificThrottleStatistics() {
		new RequestSpecificThrottleStatistics()
	}

}
