package com.cezarykluczynski.stapi.model.common.statistics.size;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@ConditionalOnProperty(name = "spring.batch.job.enabled", havingValue = "false")
public class EntitySizeCachedStatisticsProviderImpl implements EntitySizeStatisticsProvider {

	private Map<Class, Long> statistics;

	private Long relationsCount;

	private final EntitySizeCountingService entitySizeCountingService;

	public EntitySizeCachedStatisticsProviderImpl(EntitySizeCountingService entitySizeCountingService) {
		this.entitySizeCountingService = entitySizeCountingService;
	}

	@Override
	@SuppressFBWarnings("EI_EXPOSE_REP")
	public synchronized Map<Class, Long> provideEntitiesCount() {
		if (statistics == null) {
			statistics = entitySizeCountingService.countEntities();
		}

		return statistics;
	}

	@Override
	public synchronized Long provideRelationsCount() {
		if (relationsCount == null) {
			relationsCount = entitySizeCountingService.countRelations();
		}

		return relationsCount;
	}

}
