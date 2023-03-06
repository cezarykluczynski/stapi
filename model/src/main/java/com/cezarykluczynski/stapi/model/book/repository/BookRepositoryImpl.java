package com.cezarykluczynski.stapi.model.book.repository;

import com.cezarykluczynski.stapi.model.book.dto.BookRequestDTO;
import com.cezarykluczynski.stapi.model.book.entity.Book;
import com.cezarykluczynski.stapi.model.book.entity.Book_;
import com.cezarykluczynski.stapi.model.book.query.BookQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.common.repository.AbstractRepositoryImpl;
import com.google.common.collect.Sets;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepositoryImpl extends AbstractRepositoryImpl<Book> implements BookRepositoryCustom {

	private final BookQueryBuilderFactory bookQueryBuilderFactory;

	public BookRepositoryImpl(BookQueryBuilderFactory bookQueryBuilderFactory) {
		this.bookQueryBuilderFactory = bookQueryBuilderFactory;
	}

	@Override
	public Page<Book> findMatching(BookRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Book> bookQueryBuilder = bookQueryBuilderFactory.createQueryBuilder(pageable);
		String uid = criteria.getUid();
		boolean doFetch = uid != null;

		bookQueryBuilder.equal(Book_.uid, uid);
		bookQueryBuilder.like(Book_.title, criteria.getTitle());
		bookQueryBuilder.between(Book_.publishedYear, criteria.getPublishedYearFrom(), criteria.getPublishedYearTo());
		bookQueryBuilder.between(Book_.numberOfPages, criteria.getNumberOfPagesFrom(), criteria.getNumberOfPagesTo());
		bookQueryBuilder.between(Book_.yearFrom, criteria.getYearFrom(), null);
		bookQueryBuilder.between(Book_.yearTo, null, criteria.getYearTo());
		bookQueryBuilder.between(Book_.audiobookPublishedYear, criteria.getAudiobookPublishedYearFrom(), criteria.getAudiobookPublishedYearTo());
		bookQueryBuilder.between(Book_.audiobookRunTime, criteria.getAudiobookRunTimeFrom(), criteria.getAudiobookRunTimeTo());
		bookQueryBuilder.between(Book_.stardateFrom, criteria.getStardateFrom(), null);
		bookQueryBuilder.between(Book_.stardateTo, null, criteria.getStardateTo());
		bookQueryBuilder.equal(Book_.novel, criteria.getNovel());
		bookQueryBuilder.equal(Book_.referenceBook, criteria.getReferenceBook());
		bookQueryBuilder.equal(Book_.biographyBook, criteria.getBiographyBook());
		bookQueryBuilder.equal(Book_.rolePlayingBook, criteria.getRolePlayingBook());
		bookQueryBuilder.equal(Book_.eBook, criteria.getEBook());
		bookQueryBuilder.equal(Book_.anthology, criteria.getAnthology());
		bookQueryBuilder.equal(Book_.unauthorizedPublication, criteria.getUnauthorizedPublication());
		bookQueryBuilder.equal(Book_.novelization, criteria.getNovelization());
		bookQueryBuilder.equal(Book_.audiobook, criteria.getAudiobook());
		bookQueryBuilder.equal(Book_.audiobookAbridged, criteria.getAudiobookAbridged());
		bookQueryBuilder.setSort(criteria.getSort());
		bookQueryBuilder.fetch(Book_.authors, doFetch);
		bookQueryBuilder.fetch(Book_.artists, doFetch);
		bookQueryBuilder.divideQueries();
		bookQueryBuilder.fetch(Book_.editors, doFetch);
		bookQueryBuilder.fetch(Book_.audiobookNarrators, doFetch);
		bookQueryBuilder.divideQueries();
		bookQueryBuilder.fetch(Book_.bookSeries, doFetch);
		bookQueryBuilder.fetch(Book_.publishers, doFetch);
		bookQueryBuilder.divideQueries();
		bookQueryBuilder.fetch(Book_.audiobookPublishers, doFetch);
		bookQueryBuilder.fetch(Book_.bookCollections, doFetch);
		bookQueryBuilder.divideQueries();
		bookQueryBuilder.fetch(Book_.characters, doFetch);
		bookQueryBuilder.divideQueries();
		bookQueryBuilder.fetch(Book_.references, doFetch);
		bookQueryBuilder.fetch(Book_.audiobookReferences, doFetch);

		Page<Book> bookPage = bookQueryBuilder.findPage();
		clearProxies(bookPage, !doFetch);
		return bookPage;
	}

	@Override
	protected void clearProxies(Page<Book> page, boolean doClearProxies) {
		if (!doClearProxies) {
			return;
		}

		page.getContent().forEach(book -> {
			book.setAuthors(Sets.newHashSet());
			book.setArtists(Sets.newHashSet());
			book.setEditors(Sets.newHashSet());
			book.setAudiobookNarrators(Sets.newHashSet());
			book.setBookSeries(Sets.newHashSet());
			book.setPublishers(Sets.newHashSet());
			book.setAudiobookPublishers(Sets.newHashSet());
			book.setBookCollections(Sets.newHashSet());
			book.setCharacters(Sets.newHashSet());
			book.setReferences(Sets.newHashSet());
			book.setAudiobookReferences(Sets.newHashSet());
		});
	}

}
