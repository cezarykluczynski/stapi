package com.cezarykluczynski.stapi.model.common.statistics.size

import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity
import spock.lang.Specification

class EntitySizeCachedStatisticsProviderImplTest extends Specification {

	private EntitySizeCountingService entitySizeCountingServiceMock

	private EntitySizeCachedStatisticsProviderImpl entitySizeCachedStatisticsProviderImpl

	void setup() {
		entitySizeCountingServiceMock = Mock()
		entitySizeCachedStatisticsProviderImpl = new EntitySizeCachedStatisticsProviderImpl(entitySizeCountingServiceMock)
	}

	void "caches result of first EntitySizeCountingService call, and returns it every time"() {
		given:
		Map<Class<? extends PageAwareEntity>, Long> firstCountMap = Mock()

		when:
		Map<Class<? extends PageAwareEntity>, Long> firstCallResult = entitySizeCachedStatisticsProviderImpl.provide()

		then:
		1 * entitySizeCountingServiceMock.count() >> firstCountMap
		0 * _
		firstCallResult == firstCountMap

		when:
		Map<Class<? extends PageAwareEntity>, Long> secondCallResult = entitySizeCachedStatisticsProviderImpl.provide()

		then:
		0 * _
		secondCallResult == firstCountMap
	}

}
