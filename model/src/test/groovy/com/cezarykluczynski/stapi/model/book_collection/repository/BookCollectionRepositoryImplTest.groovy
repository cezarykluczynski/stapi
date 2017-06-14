package com.cezarykluczynski.stapi.model.book_collection.repository

import com.cezarykluczynski.stapi.model.book.entity.Book
import com.cezarykluczynski.stapi.model.book_collection.dto.BookCollectionRequestDTO
import com.cezarykluczynski.stapi.model.book_collection.entity.BookCollection
import com.cezarykluczynski.stapi.model.book_collection.entity.BookCollection_
import com.cezarykluczynski.stapi.model.book_collection.query.BookCollectionInitialQueryBuilderFactory
import com.cezarykluczynski.stapi.model.book_series.entity.BookSeries
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.model.reference.entity.Reference
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import spock.lang.Specification

class BookCollectionRepositoryImplTest extends Specification {

	private static final String UID = 'ABCD0123456789'

	private BookCollectionInitialQueryBuilderFactory bookCollectionInitialQueryBuilderFactory

	private BookCollectionRepositoryImpl bookCollectionRepositoryImpl

	private QueryBuilder<BookCollection> bookCollectionQueryBuilder

	private QueryBuilder<BookCollection> bookCollectionComicSeriesPublishersComicsQueryBuilder

	private QueryBuilder<BookCollection> bookCollectionCharactersReferencesQueryBuilder

	private Pageable pageable

	private BookCollectionRequestDTO bookCollectionRequestDTO

	private BookCollection bookCollection

	private BookCollection comicSeriesPerformersBookCollection

	private BookCollection charactersReferencesBookCollection

	private BookCollection staffBookCollection

	private Page page

	private Page performersPage

	private Page charactersPage

	private Set<BookSeries> bookSeriesSet

	private Set<Company> publishersSet

	private Set<Book> booksSet

	private Set<Character> charactersSet

	private Set<Reference> referencesSet

	private Set<Staff> authorsSet

	private Set<Staff> artistsSet

	private Set<Staff> editorsSet

	void setup() {
		bookCollectionInitialQueryBuilderFactory = Mock()
		bookCollectionRepositoryImpl = new BookCollectionRepositoryImpl(bookCollectionInitialQueryBuilderFactory)
		bookCollectionQueryBuilder = Mock()
		bookCollectionComicSeriesPublishersComicsQueryBuilder = Mock()
		bookCollectionCharactersReferencesQueryBuilder = Mock()
		pageable = Mock()
		bookCollectionRequestDTO = Mock()
		page = Mock()
		performersPage = Mock()
		charactersPage = Mock()
		bookCollection = Mock()
		comicSeriesPerformersBookCollection = Mock()
		charactersReferencesBookCollection = Mock()
		staffBookCollection = Mock()
		bookSeriesSet = Mock()
		publishersSet = Mock()
		booksSet = Mock()
		charactersSet = Mock()
		referencesSet = Mock()
		authorsSet = Mock()
		artistsSet = Mock()
		editorsSet = Mock()
	}

	void "query is built and performed"() {
		when:
		Page pageOutput = bookCollectionRepositoryImpl.findMatching(bookCollectionRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * bookCollectionInitialQueryBuilderFactory.createInitialQueryBuilder(bookCollectionRequestDTO, pageable) >>
				bookCollectionQueryBuilder

		then: 'uid is retrieved, and it is not null'
		1 * bookCollectionRequestDTO.uid >> UID

		then: 'staff fetch is performed'
		1 * bookCollectionQueryBuilder.fetch(BookCollection_.authors)
		1 * bookCollectionQueryBuilder.fetch(BookCollection_.artists)
		1 * bookCollectionQueryBuilder.fetch(BookCollection_.editors)

		then: 'page is retrieved'
		1 * bookCollectionQueryBuilder.findPage() >> page
		1 * page.content >> Lists.newArrayList(bookCollection)

		then: 'another criteria builder is retrieved for comic series, publishers, and comics'
		1 * bookCollectionInitialQueryBuilderFactory.createInitialQueryBuilder(bookCollectionRequestDTO, pageable) >>
				bookCollectionComicSeriesPublishersComicsQueryBuilder

		then: 'comic series and publishers fetch is performed'
		1 * bookCollectionComicSeriesPublishersComicsQueryBuilder.fetch(BookCollection_.bookSeries)
		1 * bookCollectionComicSeriesPublishersComicsQueryBuilder.fetch(BookCollection_.publishers)
		1 * bookCollectionComicSeriesPublishersComicsQueryBuilder.fetch(BookCollection_.books)

		then: 'comic series and publishers list is retrieved'
		1 * bookCollectionComicSeriesPublishersComicsQueryBuilder.findAll() >> Lists.newArrayList(comicSeriesPerformersBookCollection)

		then: 'comic series and publishers are set to bookCollection'
		1 * comicSeriesPerformersBookCollection.bookSeries >> bookSeriesSet
		1 * bookCollection.setBookSeries(bookSeriesSet)
		1 * comicSeriesPerformersBookCollection.publishers >> publishersSet
		1 * bookCollection.setPublishers(publishersSet)
		1 * comicSeriesPerformersBookCollection.books >> booksSet
		1 * bookCollection.setBooks(booksSet)

		then: 'another criteria builder is retrieved for characters and references'
		1 * bookCollectionInitialQueryBuilderFactory.createInitialQueryBuilder(bookCollectionRequestDTO, pageable) >>
				bookCollectionCharactersReferencesQueryBuilder

		then: 'characters and references fetch is performed'
		1 * bookCollectionCharactersReferencesQueryBuilder.fetch(BookCollection_.characters)
		1 * bookCollectionCharactersReferencesQueryBuilder.fetch(BookCollection_.references)

		then: 'characters and references list is retrieved'
		1 * bookCollectionCharactersReferencesQueryBuilder.findAll() >> Lists.newArrayList(charactersReferencesBookCollection)

		then: 'characters and references are set to bookCollection'
		1 * charactersReferencesBookCollection.characters >> charactersSet
		1 * bookCollection.setCharacters(charactersSet)
		1 * charactersReferencesBookCollection.references >> referencesSet
		1 * bookCollection.setReferences(referencesSet)

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

	void "query is built and performed without results from additional queries"() {
		when:
		Page pageOutput = bookCollectionRepositoryImpl.findMatching(bookCollectionRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * bookCollectionInitialQueryBuilderFactory.createInitialQueryBuilder(bookCollectionRequestDTO, pageable) >>
				bookCollectionQueryBuilder

		then: 'uid is retrieved, and it is not null'
		1 * bookCollectionRequestDTO.uid >> UID

		then: 'staff fetch is performed'
		1 * bookCollectionQueryBuilder.fetch(BookCollection_.authors)
		1 * bookCollectionQueryBuilder.fetch(BookCollection_.artists)
		1 * bookCollectionQueryBuilder.fetch(BookCollection_.editors)

		then: 'page is retrieved'
		1 * bookCollectionQueryBuilder.findPage() >> page
		1 * page.content >> Lists.newArrayList(bookCollection)

		then: 'another criteria builder is retrieved for comic series and publishers'
		1 * bookCollectionInitialQueryBuilderFactory.createInitialQueryBuilder(bookCollectionRequestDTO, pageable) >>
				bookCollectionComicSeriesPublishersComicsQueryBuilder

		then: 'comic series and publishers fetch is performed'
		1 * bookCollectionComicSeriesPublishersComicsQueryBuilder.fetch(BookCollection_.bookSeries)
		1 * bookCollectionComicSeriesPublishersComicsQueryBuilder.fetch(BookCollection_.publishers)
		1 * bookCollectionComicSeriesPublishersComicsQueryBuilder.fetch(BookCollection_.books)

		then: 'empty comic series and publishers list is retrieved'
		1 * bookCollectionComicSeriesPublishersComicsQueryBuilder.findAll() >> Lists.newArrayList()

		then: 'another criteria builder is retrieved for characters and references'
		1 * bookCollectionInitialQueryBuilderFactory.createInitialQueryBuilder(bookCollectionRequestDTO, pageable) >>
				bookCollectionCharactersReferencesQueryBuilder

		then: 'characters and references fetch is performed'
		1 * bookCollectionCharactersReferencesQueryBuilder.fetch(BookCollection_.characters)
		1 * bookCollectionCharactersReferencesQueryBuilder.fetch(BookCollection_.references)

		then: 'empty characters and references list is retrieved'
		1 * bookCollectionCharactersReferencesQueryBuilder.findAll() >> Lists.newArrayList()

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

	void "empty page is returned"() {
		when:
		Page pageOutput = bookCollectionRepositoryImpl.findMatching(bookCollectionRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * bookCollectionInitialQueryBuilderFactory.createInitialQueryBuilder(bookCollectionRequestDTO, pageable) >>
				bookCollectionQueryBuilder

		then: 'uid is retrieved, and it is not null'
		1 * bookCollectionRequestDTO.uid >> UID

		then: 'staff fetch is performed'
		1 * bookCollectionQueryBuilder.fetch(BookCollection_.authors)
		1 * bookCollectionQueryBuilder.fetch(BookCollection_.artists)
		1 * bookCollectionQueryBuilder.fetch(BookCollection_.editors)

		then: 'page is retrieved'
		1 * bookCollectionQueryBuilder.findPage() >> page
		1 * page.content >> Lists.newArrayList()

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

	void "proxies are cleared when no related entities should be fetched"() {
		when:
		Page pageOutput = bookCollectionRepositoryImpl.findMatching(bookCollectionRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * bookCollectionInitialQueryBuilderFactory.createInitialQueryBuilder(bookCollectionRequestDTO, pageable) >> bookCollectionQueryBuilder

		then: 'uid criteria is set to null'
		1 * bookCollectionRequestDTO.uid >> null

		then: 'page is searched for and returned'
		1 * bookCollectionQueryBuilder.findPage() >> page

		then: 'proxies are cleared'
		1 * page.content >> Lists.newArrayList(bookCollection)
		bookCollection.setBookSeries(Sets.newHashSet())
		bookCollection.setAuthors(Sets.newHashSet())
		bookCollection.setArtists(Sets.newHashSet())
		bookCollection.setEditors(Sets.newHashSet())
		bookCollection.setPublishers(Sets.newHashSet())
		bookCollection.setCharacters(Sets.newHashSet())
		bookCollection.setReferences(Sets.newHashSet())
		bookCollection.setBooks(Sets.newHashSet())
		pageOutput == page
	}

}
