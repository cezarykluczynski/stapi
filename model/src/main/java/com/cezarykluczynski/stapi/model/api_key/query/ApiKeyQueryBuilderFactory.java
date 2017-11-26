package com.cezarykluczynski.stapi.model.api_key.query;

import com.cezarykluczynski.stapi.model.api_key.entity.ApiKey;
import com.cezarykluczynski.stapi.model.common.cache.NoCacheCachingStrategy;
import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

@Service
public class ApiKeyQueryBuilderFactory extends AbstractQueryBuilderFactory<ApiKey> {

	public ApiKeyQueryBuilderFactory(JpaContext jpaContext) {
		super(jpaContext, new NoCacheCachingStrategy(), ApiKey.class);
	}

}
