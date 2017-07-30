package com.cezarykluczynski.stapi.model.common.statistics.size;

import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Map;

@Service
@Profile(SpringProfile.ETL)
public class EntitySizeNotCachedStatisticsProviderImpl implements EntitySizeStatisticsProvider {

	private final EntitySizeCountingService entitySizeCountingService;

	@Inject
	public EntitySizeNotCachedStatisticsProviderImpl(EntitySizeCountingService entitySizeCountingService) {
		this.entitySizeCountingService = entitySizeCountingService;
	}

	@Override
	public Map<Class, Long> provide() {
		return entitySizeCountingService.count();
	}

}
