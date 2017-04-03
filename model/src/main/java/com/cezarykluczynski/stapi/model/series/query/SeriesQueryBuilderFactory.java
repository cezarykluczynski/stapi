package com.cezarykluczynski.stapi.model.series.query;

import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.common.query.CachingStrategy;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class SeriesQueryBuilderFactory extends AbstractQueryBuilderFactory<Series> {

	@Inject
	public SeriesQueryBuilderFactory(JpaContext jpaContext, CachingStrategy cachingStrategy) {
		super(jpaContext, cachingStrategy, Series.class);
	}

}
