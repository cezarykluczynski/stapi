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

import javax.inject.Inject;
import java.util.List;

@Repository
public class BookRepositoryImpl extends AbstractRepositoryImpl<Book> implements BookRepositoryCustom {

	private BookInitialQueryBuilderFactory bookInitialQueryBuilderFactory;

	@Inject
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

			QueryBuilder<Book> bookBookSeriesPublishersQueryBuilder = createInitialBookQueryBuilder(criteria, pageable);

			bookBookSeriesPublishersQueryBuilder.fetch(Book_.bookSeries);
			bookBookSeriesPublishersQueryBuilder.fetch(Book_.publishers);
			bookBookSeriesPublishersQueryBuilder.fetch(Book_.audiobookPublishers);

			List<Book> bookSeriesPublishersBookList = bookBookSeriesPublishersQueryBuilder.findAll();

			if (bookSeriesPublishersBookList.size() == 1) {
				Book comicSeriesPublishersComicCollectionsBook = bookSeriesPublishersBookList.get(0);
				book.setBookSeries(comicSeriesPublishersComicCollectionsBook.getBookSeries());
				book.setPublishers(comicSeriesPublishersComicCollectionsBook.getPublishers());
				book.setAudiobookPublishers(comicSeriesPublishersComicCollectionsBook.getAudiobookPublishers());
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

		page.getContent().forEach(episode -> {
			episode.setAuthors(Sets.newHashSet());
			episode.setArtists(Sets.newHashSet());
			episode.setEditors(Sets.newHashSet());
			episode.setAudiobookNarrators(Sets.newHashSet());
			episode.setBookSeries(Sets.newHashSet());
			episode.setPublishers(Sets.newHashSet());
			episode.setAudiobookPublishers(Sets.newHashSet());
			episode.setCharacters(Sets.newHashSet());
			episode.setReferences(Sets.newHashSet());
			episode.setAudiobookReferences(Sets.newHashSet());
		});
	}

	private QueryBuilder<Book> createInitialBookQueryBuilder(BookRequestDTO criteria, Pageable pageable) {
		return bookInitialQueryBuilderFactory.createInitialQueryBuilder(criteria, pageable);
	}

}
