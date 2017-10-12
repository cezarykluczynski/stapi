package com.cezarykluczynski.stapi.model.conflict.query;

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy;
import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.conflict.entity.Conflict;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ConflictQueryBuilderFactory extends AbstractQueryBuilderFactory<Conflict> {

	@Inject
	public ConflictQueryBuilderFactory(JpaContext jpaContext, CachingStrategy cachingStrategy) {
		super(jpaContext, cachingStrategy, Conflict.class);
	}

}
