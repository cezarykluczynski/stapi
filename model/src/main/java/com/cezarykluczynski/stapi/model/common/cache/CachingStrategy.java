package com.cezarykluczynski.stapi.model.common.cache;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;

public interface CachingStrategy {

	boolean isCacheable(QueryBuilder queryBuilder);

}
