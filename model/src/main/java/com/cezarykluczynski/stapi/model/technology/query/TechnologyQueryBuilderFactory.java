package com.cezarykluczynski.stapi.model.technology.query;

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy;
import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.technology.entity.Technology;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class TechnologyQueryBuilderFactory extends AbstractQueryBuilderFactory<Technology> {

	@Inject
	public TechnologyQueryBuilderFactory(JpaContext jpaContext, CachingStrategy cachingStrategy) {
		super(jpaContext, cachingStrategy, Technology.class);
	}

}
