package com.cezarykluczynski.stapi.model.magazine_series.query;

import com.cezarykluczynski.stapi.model.magazine_series.entity.MagazineSeries;
import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy;
import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class MagazineSeriesQueryBuilderFactory extends AbstractQueryBuilderFactory<MagazineSeries> {

	@Inject
	public MagazineSeriesQueryBuilderFactory(JpaContext jpaContext, CachingStrategy cachingStrategy) {
		super(jpaContext, cachingStrategy, MagazineSeries.class);
	}

}
