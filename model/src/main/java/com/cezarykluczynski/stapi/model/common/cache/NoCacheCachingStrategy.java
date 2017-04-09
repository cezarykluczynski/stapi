package com.cezarykluczynski.stapi.model.common.cache;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;

public class NoCacheCachingStrategy implements CachingStrategy {

	@Override
	public boolean isCacheable(QueryBuilder queryBuilder) {
		return false;
	}

}
