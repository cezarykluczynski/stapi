package com.cezarykluczynski.stapi.model.throttle.repository;

import com.cezarykluczynski.stapi.model.throttle.dto.ThrottleStatistics;

public interface ThrottleRepositoryCustom {

	ThrottleStatistics decrementByIpAndGetStatistics(String ipAddress);

	void regenerateIPAddressesRemainingHits();

	void deleteExpiredIPLimits();

}
