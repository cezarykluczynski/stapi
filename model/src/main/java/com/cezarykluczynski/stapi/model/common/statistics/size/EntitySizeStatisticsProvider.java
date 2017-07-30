package com.cezarykluczynski.stapi.model.common.statistics.size;

import java.util.Map;

public interface EntitySizeStatisticsProvider {

	Map<Class, Long> provide();

}
