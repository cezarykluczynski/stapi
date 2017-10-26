package com.cezarykluczynski.stapi.model.occupation.query;

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy;
import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.occupation.entity.Occupation;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

@Service
public class OccupationQueryBuilderFactory extends AbstractQueryBuilderFactory<Occupation> {

	public OccupationQueryBuilderFactory(JpaContext jpaContext, CachingStrategy cachingStrategy) {
		super(jpaContext, cachingStrategy, Occupation.class);
	}

}
