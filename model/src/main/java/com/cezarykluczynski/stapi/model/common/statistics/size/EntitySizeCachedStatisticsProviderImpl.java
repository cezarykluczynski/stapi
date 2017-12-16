package com.cezarykluczynski.stapi.model.common.statistics.size;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@ConditionalOnProperty(name = "etl.enabled", havingValue = "false")
public class EntitySizeCachedStatisticsProviderImpl implements EntitySizeStatisticsProvider {

	private Map<Class, Long> statistics;

	private final EntitySizeCountingService entitySizeCountingService;

	public EntitySizeCachedStatisticsProviderImpl(EntitySizeCountingService entitySizeCountingService) {
		this.entitySizeCountingService = entitySizeCountingService;
	}

	@Override
	public synchronized Map<Class, Long> provide() {
		if (statistics == null) {
			statistics = entitySizeCountingService.count();
		}

		return statistics;
	}

}
