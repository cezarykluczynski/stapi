package com.cezarykluczynski.stapi.model.common.statistics.size;

import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity;

import java.util.Map;

public interface EntitySizeStatisticsProvider {

	Map<Class<? extends PageAwareEntity>, Long> provide();

}
