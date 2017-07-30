package com.cezarykluczynski.stapi.model.common.statistics.size;

import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Map;

@Service
@Profile(SpringProfile.ETL_NOT)
public class EntitySizeCachedStatisticsProviderImpl implements EntitySizeStatisticsProvider {

	private Map<Class, Long> statistics;

	private final EntitySizeCountingService entitySizeCountingService;

	@Inject
	public EntitySizeCachedStatisticsProviderImpl(EntitySizeCountingService entitySizeCountingService) {
		this.entitySizeCountingService = entitySizeCountingService;
	}

	@Override
	public Map<Class, Long> provide() {
		if (statistics == null) {
			synchronized (this) {
				if (statistics == null) {
					statistics = entitySizeCountingService.count();
				}
			}
		}

		return statistics;
	}

}
