package com.cezarykluczynski.stapi.model.book_series.query;

import com.cezarykluczynski.stapi.model.book_series.entity.BookSeries;
import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

@Service
public class BookSeriesQueryBuilderFactory extends AbstractQueryBuilderFactory<BookSeries> {

	public BookSeriesQueryBuilderFactory(JpaContext jpaContext) {
		super(jpaContext, BookSeries.class);
	}

}
