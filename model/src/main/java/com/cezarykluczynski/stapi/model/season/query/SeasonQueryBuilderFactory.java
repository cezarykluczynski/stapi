package com.cezarykluczynski.stapi.model.season.query;

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy;
import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.season.entity.Season;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class SeasonQueryBuilderFactory extends AbstractQueryBuilderFactory<Season> {

	@Inject
	public SeasonQueryBuilderFactory(JpaContext jpaContext, CachingStrategy cachingStrategy) {
		super(jpaContext, cachingStrategy, Season.class);
	}

}
