package com.cezarykluczynski.stapi.model.magazine_series.query;

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy;
import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.magazine_series.entity.MagazineSeries;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

@Service
public class MagazineSeriesQueryBuilderFactory extends AbstractQueryBuilderFactory<MagazineSeries> {

	public MagazineSeriesQueryBuilderFactory(JpaContext jpaContext, CachingStrategy cachingStrategy) {
		super(jpaContext, cachingStrategy, MagazineSeries.class);
	}

}
