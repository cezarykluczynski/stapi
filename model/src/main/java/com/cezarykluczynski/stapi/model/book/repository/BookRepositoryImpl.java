package com.cezarykluczynski.stapi.model.book.repository;

import com.cezarykluczynski.stapi.model.book.dto.BookRequestDTO;
import com.cezarykluczynski.stapi.model.book.entity.Book;
import com.cezarykluczynski.stapi.model.book.entity.Book_;
import com.cezarykluczynski.stapi.model.book.query.BookInitialQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.common.repository.AbstractRepositoryImpl;
import com.google.common.collect.Sets;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookRepositoryImpl extends AbstractRepositoryImpl<Book> implements BookRepositoryCustom {

	private final BookInitialQueryBuilderFactory bookInitialQueryBuilderFactory;

	public BookRepositoryImpl(BookInitialQueryBuilderFactory bookInitialQueryBuilderFactory) {
		this.bookInitialQueryBuilderFactory = bookInitialQueryBuilderFactory;
	}

	@Override
	public Page<Book> findMatching(BookRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Book> bookQueryBuilder = createInitialBookQueryBuilder(criteria, pageable);
		String uid = criteria.getUid();
		boolean doFetch = uid != null;

		Page<Book> bookPage;

		if (doFetch) {
			bookQueryBuilder.fetch(Book_.authors);
			bookQueryBuilder.fetch(Book_.artists);
			bookQueryBuilder.fetch(Book_.editors);
			bookQueryBuilder.fetch(Book_.audiobookNarrators);
			bookPage = bookQueryBuilder.findPage();

			List<Book> bookList = bookPage.getContent();

			if (bookList.size() == 0) {
				return bookPage;
			}

			Book book = bookList.get(0);

			QueryBuilder<Book> bookBookSeriesBookCollectionsPublishersQueryBuilder = createInitialBookQueryBuilder(criteria, pageable);

			bookBookSeriesBookCollectionsPublishersQueryBuilder.fetch(Book_.bookSeries);
			bookBookSeriesBookCollectionsPublishersQueryBuilder.fetch(Book_.publishers);
			bookBookSeriesBookCollectionsPublishersQueryBuilder.fetch(Book_.audiobookPublishers);
			bookBookSeriesBookCollectionsPublishersQueryBuilder.fetch(Book_.bookCollections);

			List<Book> bookSeriesPublishersBookList = bookBookSeriesBookCollectionsPublishersQueryBuilder.findAll();

			if (bookSeriesPublishersBookList.size() == 1) {
				Book comicSeriesPublishersComicCollectionsBook = bookSeriesPublishersBookList.get(0);
				book.setBookSeries(comicSeriesPublishersComicCollectionsBook.getBookSeries());
				book.setPublishers(comicSeriesPublishersComicCollectionsBook.getPublishers());
				book.setAudiobookPublishers(comicSeriesPublishersComicCollectionsBook.getAudiobookPublishers());
				book.setBookCollections(comicSeriesPublishersComicCollectionsBook.getBookCollections());
			}

			QueryBuilder<Book> bookCharactersReferencesQueryBuilder = createInitialBookQueryBuilder(criteria, pageable);
			bookCharactersReferencesQueryBuilder.fetch(Book_.characters);
			bookCharactersReferencesQueryBuilder.fetch(Book_.references);
			bookCharactersReferencesQueryBuilder.fetch(Book_.audiobookReferences);

			List<Book> charactersReferencesBookList = bookCharactersReferencesQueryBuilder.findAll();

			if (charactersReferencesBookList.size() == 1) {
				Book charactersReferencesBook = charactersReferencesBookList.get(0);
				book.setCharacters(charactersReferencesBook.getCharacters());
				book.setReferences(charactersReferencesBook.getReferences());
				book.setAudiobookReferences(charactersReferencesBook.getAudiobookReferences());
			}
		} else {
			bookPage = bookQueryBuilder.findPage();
		}

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
			book.setCharacters(Sets.newHashSet());
			book.setReferences(Sets.newHashSet());
			book.setAudiobookReferences(Sets.newHashSet());
			book.setBookCollections(Sets.newHashSet());
		});
	}

	private QueryBuilder<Book> createInitialBookQueryBuilder(BookRequestDTO criteria, Pageable pageable) {
		return bookInitialQueryBuilderFactory.createInitialQueryBuilder(criteria, pageable);
	}

}
