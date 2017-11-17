package com.cezarykluczynski.stapi.model.throttle.repository;

import com.cezarykluczynski.stapi.model.throttle.dto.ThrottleStatistics;

public interface ThrottleRepositoryCustom {

	ThrottleStatistics decrementByIpAndGetStatistics(String ipAddress);

	ThrottleStatistics decrementByApiKeyAndGetStatistics(String apiKey, String ipAddress);

	void regenerateIPAddressesRemainingHits();

	void regenerateApiKeysRemainingHits();

	void deleteExpiredIPLimits();

}
