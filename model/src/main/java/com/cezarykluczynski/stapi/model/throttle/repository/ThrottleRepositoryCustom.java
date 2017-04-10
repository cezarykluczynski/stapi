package com.cezarykluczynski.stapi.model.throttle.repository;

public interface ThrottleRepositoryCustom {

	boolean decrementByIpAndGetResult(String ipAddress);

	void regenerateIPAddressesRemainingHits();

	void deleteExpiredIPLimits();

}
