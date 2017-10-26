package com.cezarykluczynski.stapi.model.book_collection.query;

import com.cezarykluczynski.stapi.model.book_collection.dto.BookCollectionRequestDTO;
import com.cezarykluczynski.stapi.model.book_collection.entity.BookCollection;
import com.cezarykluczynski.stapi.model.book_collection.entity.BookCollection_;
import com.cezarykluczynski.stapi.model.common.query.InitialQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookCollectionInitialQueryBuilderFactory implements InitialQueryBuilderFactory<BookCollectionRequestDTO, BookCollection> {

	private final BookCollectionQueryBuilderFactory bookCollectionQueryBuilderFactory;

	public BookCollectionInitialQueryBuilderFactory(BookCollectionQueryBuilderFactory bookCollectionQueryBuilderFactory) {
		this.bookCollectionQueryBuilderFactory = bookCollectionQueryBuilderFactory;
	}

	@Override
	public QueryBuilder<BookCollection> createInitialQueryBuilder(BookCollectionRequestDTO criteria, Pageable pageable) {
		QueryBuilder<BookCollection> comicsQueryBuilder = bookCollectionQueryBuilderFactory.createQueryBuilder(pageable);

		comicsQueryBuilder.equal(BookCollection_.uid, criteria.getUid());
		comicsQueryBuilder.like(BookCollection_.title, criteria.getTitle());
		comicsQueryBuilder.between(BookCollection_.publishedYear, criteria.getPublishedYearFrom(), criteria.getPublishedYearTo());
		comicsQueryBuilder.between(BookCollection_.numberOfPages, criteria.getNumberOfPagesFrom(), criteria.getNumberOfPagesTo());
		comicsQueryBuilder.between(BookCollection_.yearFrom, criteria.getYearFrom(), null);
		comicsQueryBuilder.between(BookCollection_.yearTo, null, criteria.getYearTo());
		comicsQueryBuilder.between(BookCollection_.stardateFrom, criteria.getStardateFrom(), null);
		comicsQueryBuilder.between(BookCollection_.stardateTo, null, criteria.getStardateTo());
		comicsQueryBuilder.setSort(criteria.getSort());

		return comicsQueryBuilder;
	}
}
