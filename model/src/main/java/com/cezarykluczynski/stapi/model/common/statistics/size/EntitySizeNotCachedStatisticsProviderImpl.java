package com.cezarykluczynski.stapi.model.common.statistics.size;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@ConditionalOnProperty("etl.enabled")
public class EntitySizeNotCachedStatisticsProviderImpl implements EntitySizeStatisticsProvider {

	private final EntitySizeCountingService entitySizeCountingService;

	public EntitySizeNotCachedStatisticsProviderImpl(EntitySizeCountingService entitySizeCountingService) {
		this.entitySizeCountingService = entitySizeCountingService;
	}

	@Override
	public Map<Class, Long> provide() {
		return entitySizeCountingService.count();
	}

}
