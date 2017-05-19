package com.cezarykluczynski.stapi.model.common.statistics.size

import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity
import spock.lang.Specification

class EntitySizeNotCachedStatisticsProviderImplTest extends Specification {

	private EntitySizeCountingService entitySizeCountingServiceMock

	private EntitySizeNotCachedStatisticsProviderImpl entitySizeNotCachedStatisticsProviderImpl

	void setup() {
		entitySizeCountingServiceMock = Mock()
		entitySizeNotCachedStatisticsProviderImpl = new EntitySizeNotCachedStatisticsProviderImpl(entitySizeCountingServiceMock)
	}

	void "provides data from EntitySizeCountingService every time"() {
		given:
		Map<Class<? extends PageAwareEntity>, Long> firstCountMap = Mock()
		Map<Class<? extends PageAwareEntity>, Long> secondCountMap = Mock()

		when:
		Map<Class<? extends PageAwareEntity>, Long> firstCallResult = entitySizeNotCachedStatisticsProviderImpl.provide()

		then:
		1 * entitySizeCountingServiceMock.count() >> firstCountMap
		0 * _
		firstCallResult == firstCountMap

		when:
		Map<Class<? extends PageAwareEntity>, Long> secondCallResult = entitySizeNotCachedStatisticsProviderImpl.provide()

		then:
		1 * entitySizeCountingServiceMock.count() >> secondCountMap
		0 * _
		secondCallResult == secondCountMap
	}

}
