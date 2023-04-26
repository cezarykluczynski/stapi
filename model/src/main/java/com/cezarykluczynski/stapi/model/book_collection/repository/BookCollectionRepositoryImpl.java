package com.cezarykluczynski.stapi.model.book_collection.repository;

import com.cezarykluczynski.stapi.model.book_collection.dto.BookCollectionRequestDTO;
import com.cezarykluczynski.stapi.model.book_collection.entity.BookCollection;
import com.cezarykluczynski.stapi.model.book_collection.entity.BookCollection_;
import com.cezarykluczynski.stapi.model.book_collection.query.BookCollectionQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class BookCollectionRepositoryImpl implements BookCollectionRepositoryCustom {

	private final BookCollectionQueryBuilderFactory bookCollectionQueryBuilderFactory;

	public BookCollectionRepositoryImpl(BookCollectionQueryBuilderFactory bookCollectionQueryBuilderFactory) {
		this.bookCollectionQueryBuilderFactory = bookCollectionQueryBuilderFactory;
	}

	@Override
	public Page<BookCollection> findMatching(BookCollectionRequestDTO criteria, Pageable pageable) {
		QueryBuilder<BookCollection> bookCollectionQueryBuilder = bookCollectionQueryBuilderFactory.createQueryBuilder(pageable);
		String uid = criteria.getUid();
		boolean doFetch = uid != null;

		bookCollectionQueryBuilder.equal(BookCollection_.uid, uid);
		bookCollectionQueryBuilder.like(BookCollection_.title, criteria.getTitle());
		bookCollectionQueryBuilder.between(BookCollection_.publishedYear, criteria.getPublishedYearFrom(), criteria.getPublishedYearTo());
		bookCollectionQueryBuilder.between(BookCollection_.numberOfPages, criteria.getNumberOfPagesFrom(), criteria.getNumberOfPagesTo());
		bookCollectionQueryBuilder.between(BookCollection_.yearFrom, criteria.getYearFrom(), null);
		bookCollectionQueryBuilder.between(BookCollection_.yearTo, null, criteria.getYearTo());
		bookCollectionQueryBuilder.between(BookCollection_.stardateFrom, criteria.getStardateFrom(), null);
		bookCollectionQueryBuilder.between(BookCollection_.stardateTo, null, criteria.getStardateTo());
		bookCollectionQueryBuilder.setSort(criteria.getSort());
		bookCollectionQueryBuilder.fetch(BookCollection_.authors, doFetch);
		bookCollectionQueryBuilder.fetch(BookCollection_.artists, doFetch);
		bookCollectionQueryBuilder.fetch(BookCollection_.editors, doFetch);
		bookCollectionQueryBuilder.divideQueries();
		bookCollectionQueryBuilder.fetch(BookCollection_.bookSeries, doFetch);
		bookCollectionQueryBuilder.fetch(BookCollection_.publishers, doFetch);
		bookCollectionQueryBuilder.divideQueries();
		bookCollectionQueryBuilder.fetch(BookCollection_.books, doFetch);
		bookCollectionQueryBuilder.divideQueries();
		bookCollectionQueryBuilder.fetch(BookCollection_.characters, doFetch);
		bookCollectionQueryBuilder.divideQueries();
		bookCollectionQueryBuilder.fetch(BookCollection_.references, doFetch);

		return bookCollectionQueryBuilder.findPage();
	}

}
