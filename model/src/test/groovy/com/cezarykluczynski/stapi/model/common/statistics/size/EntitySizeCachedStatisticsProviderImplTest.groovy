package com.cezarykluczynski.stapi.model.common.statistics.size

import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity
import spock.lang.Specification

class EntitySizeCachedStatisticsProviderImplTest extends Specification {

	private static final Long RELATIONS_COUNT = 15

	private EntitySizeCountingService entitySizeCountingServiceMock

	private EntitySizeCachedStatisticsProviderImpl entitySizeCachedStatisticsProviderImpl

	void setup() {
		entitySizeCountingServiceMock = Mock()
		entitySizeCachedStatisticsProviderImpl = new EntitySizeCachedStatisticsProviderImpl(entitySizeCountingServiceMock)
	}

	void "caches result of first call for entities count, and returns it every time"() {
		given:
		Map<Class<? extends PageAwareEntity>, Long> firstCountMap = Mock()

		when:
		Map<Class<? extends PageAwareEntity>, Long> firstCallResult = entitySizeCachedStatisticsProviderImpl.provideEntitiesCount()

		then:
		1 * entitySizeCountingServiceMock.countEntities() >> firstCountMap
		0 * _
		firstCallResult == firstCountMap

		when:
		Map<Class<? extends PageAwareEntity>, Long> secondCallResult = entitySizeCachedStatisticsProviderImpl.provideEntitiesCount()

		then:
		0 * _
		secondCallResult == firstCountMap
	}

	void "caches result of first call for relations count, and returns it every time"() {
		when:
		Long firstCallResult = entitySizeCachedStatisticsProviderImpl.provideRelationsCount()

		then:
		1 * entitySizeCountingServiceMock.countRelations() >> RELATIONS_COUNT
		0 * _
		firstCallResult == RELATIONS_COUNT

		when:
		Long secondCallResult = entitySizeCachedStatisticsProviderImpl.provideRelationsCount()

		then:
		0 * _
		secondCallResult == RELATIONS_COUNT
	}

}
