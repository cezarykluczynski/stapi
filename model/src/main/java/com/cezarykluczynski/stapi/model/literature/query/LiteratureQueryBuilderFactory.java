package com.cezarykluczynski.stapi.model.literature.query;

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy;
import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.literature.entity.Literature;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class LiteratureQueryBuilderFactory extends AbstractQueryBuilderFactory<Literature> {

	@Inject
	public LiteratureQueryBuilderFactory(JpaContext jpaContext, CachingStrategy cachingStrategy) {
		super(jpaContext, cachingStrategy, Literature.class);
	}

}
