package com.cezarykluczynski.stapi.model.comic_series.query;

import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries;
import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy;
import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

@Service
public class ComicSeriesQueryBuilderFactory extends AbstractQueryBuilderFactory<ComicSeries> {

	public ComicSeriesQueryBuilderFactory(JpaContext jpaContext, CachingStrategy cachingStrategy) {
		super(jpaContext, cachingStrategy, ComicSeries.class);
	}

}
