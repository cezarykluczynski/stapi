package com.cezarykluczynski.stapi.model.common.statistics.size

import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity
import spock.lang.Specification

class EntitySizeNotCachedStatisticsProviderImplTest extends Specification {

	private static final Long RELATIONS_COUNT = 15

	private EntitySizeCountingService entitySizeCountingServiceMock

	private EntitySizeNotCachedStatisticsProviderImpl entitySizeNotCachedStatisticsProviderImpl

	void setup() {
		entitySizeCountingServiceMock = Mock()
		entitySizeNotCachedStatisticsProviderImpl = new EntitySizeNotCachedStatisticsProviderImpl(entitySizeCountingServiceMock)
	}

	void "provides data for entities count every time"() {
		given:
		Map<Class<? extends PageAwareEntity>, Long> firstCountMap = Mock()
		Map<Class<? extends PageAwareEntity>, Long> secondCountMap = Mock()

		when:
		Map<Class<? extends PageAwareEntity>, Long> firstCallResult = entitySizeNotCachedStatisticsProviderImpl.provideEntitiesCount()

		then:
		1 * entitySizeCountingServiceMock.countEntities() >> firstCountMap
		0 * _
		firstCallResult == firstCountMap

		when:
		Map<Class<? extends PageAwareEntity>, Long> secondCallResult = entitySizeNotCachedStatisticsProviderImpl.provideEntitiesCount()

		then:
		1 * entitySizeCountingServiceMock.countEntities() >> secondCountMap
		0 * _
		secondCallResult == secondCountMap
	}

	void "provides data for relations count every time"() {
		when:
		Long firstCallResult = entitySizeNotCachedStatisticsProviderImpl.provideRelationsCount()

		then:
		1 * entitySizeCountingServiceMock.countRelations() >> RELATIONS_COUNT
		0 * _
		firstCallResult == RELATIONS_COUNT

		when:
		Long secondCallResult = entitySizeNotCachedStatisticsProviderImpl.provideRelationsCount()

		then:
		1 * entitySizeCountingServiceMock.countRelations() >> RELATIONS_COUNT
		0 * _
		secondCallResult == RELATIONS_COUNT
	}

}
