package com.cezarykluczynski.stapi.model.comicSeries.query;

import com.cezarykluczynski.stapi.model.comicSeries.entity.ComicSeries;
import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ComicSeriesQueryBuilderFactory extends AbstractQueryBuilderFactory<ComicSeries> {

	@Inject
	public ComicSeriesQueryBuilderFactory(JpaContext jpaContext) {
		super(jpaContext, ComicSeries.class);
	}

}
