package com.cezarykluczynski.stapi.model.book_collection.repository;

import com.cezarykluczynski.stapi.model.book_collection.dto.BookCollectionRequestDTO;
import com.cezarykluczynski.stapi.model.book_collection.entity.BookCollection;
import com.cezarykluczynski.stapi.model.book_collection.entity.BookCollection_;
import com.cezarykluczynski.stapi.model.book_collection.query.BookCollectionInitialQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.common.repository.AbstractRepositoryImpl;
import com.google.common.collect.Sets;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookCollectionRepositoryImpl extends AbstractRepositoryImpl<BookCollection> implements BookCollectionRepositoryCustom {

	private final BookCollectionInitialQueryBuilderFactory bookCollectionInitialQueryBuilderFactory;

	public BookCollectionRepositoryImpl(BookCollectionInitialQueryBuilderFactory bookCollectionInitialQueryBuilderFactory) {
		this.bookCollectionInitialQueryBuilderFactory = bookCollectionInitialQueryBuilderFactory;
	}

	@Override
	public Page<BookCollection> findMatching(BookCollectionRequestDTO criteria, Pageable pageable) {
		QueryBuilder<BookCollection> bookCollectionQueryBuilder = createInitialBookCollectionQueryBuilder(criteria, pageable);
		String uid = criteria.getUid();
		boolean doFetch = uid != null;

		Page<BookCollection> bookCollectionCollectionPage;

		if (doFetch) {
			bookCollectionQueryBuilder.fetch(BookCollection_.authors);
			bookCollectionQueryBuilder.fetch(BookCollection_.artists);
			bookCollectionQueryBuilder.fetch(BookCollection_.editors);
			bookCollectionCollectionPage = bookCollectionQueryBuilder.findPage();

			List<BookCollection> bookCollectionList = bookCollectionCollectionPage.getContent();

			if (bookCollectionList.size() == 0) {
				return bookCollectionCollectionPage;
			}

			BookCollection bookCollection = bookCollectionList.get(0);

			QueryBuilder<BookCollection> bookCollectionComicsSeriesPublishersComicsQueryBuilder
					= createInitialBookCollectionQueryBuilder(criteria, pageable);

			bookCollectionComicsSeriesPublishersComicsQueryBuilder.fetch(BookCollection_.bookSeries);
			bookCollectionComicsSeriesPublishersComicsQueryBuilder.fetch(BookCollection_.publishers);
			bookCollectionComicsSeriesPublishersComicsQueryBuilder.fetch(BookCollection_.books);

			List<BookCollection> bookCollectionComicSeriesPublishersComicsList = bookCollectionComicsSeriesPublishersComicsQueryBuilder.findAll();

			if (bookCollectionComicSeriesPublishersComicsList.size() == 1) {
				BookCollection comicSeriesPublishersBookCollection = bookCollectionComicSeriesPublishersComicsList.get(0);
				bookCollection.setBookSeries(comicSeriesPublishersBookCollection.getBookSeries());
				bookCollection.setPublishers(comicSeriesPublishersBookCollection.getPublishers());
				bookCollection.setBooks(comicSeriesPublishersBookCollection.getBooks());
			}

			QueryBuilder<BookCollection> comicsCharactersReferencesQueryBuilder = createInitialBookCollectionQueryBuilder(criteria, pageable);
			comicsCharactersReferencesQueryBuilder.fetch(BookCollection_.characters);
			comicsCharactersReferencesQueryBuilder.fetch(BookCollection_.references);

			List<BookCollection> charactersReferencesBookCollectionList = comicsCharactersReferencesQueryBuilder.findAll();

			if (charactersReferencesBookCollectionList.size() == 1) {
				BookCollection charactersReferencesBookCollection = charactersReferencesBookCollectionList.get(0);
				bookCollection.setCharacters(charactersReferencesBookCollection.getCharacters());
				bookCollection.setReferences(charactersReferencesBookCollection.getReferences());
			}
		} else {
			bookCollectionCollectionPage = bookCollectionQueryBuilder.findPage();
		}

		clearProxies(bookCollectionCollectionPage, !doFetch);
		return bookCollectionCollectionPage;
	}

	@Override
	protected void clearProxies(Page<BookCollection> page, boolean doClearProxies) {
		if (!doClearProxies) {
			return;
		}

		page.getContent().forEach(bookCollection -> {
			bookCollection.setBookSeries(Sets.newHashSet());
			bookCollection.setAuthors(Sets.newHashSet());
			bookCollection.setArtists(Sets.newHashSet());
			bookCollection.setEditors(Sets.newHashSet());
			bookCollection.setPublishers(Sets.newHashSet());
			bookCollection.setCharacters(Sets.newHashSet());
			bookCollection.setReferences(Sets.newHashSet());
			bookCollection.setBooks(Sets.newHashSet());
		});
	}

	private QueryBuilder<BookCollection> createInitialBookCollectionQueryBuilder(BookCollectionRequestDTO criteria, Pageable pageable) {
		return bookCollectionInitialQueryBuilderFactory.createInitialQueryBuilder(criteria, pageable);
	}

}
