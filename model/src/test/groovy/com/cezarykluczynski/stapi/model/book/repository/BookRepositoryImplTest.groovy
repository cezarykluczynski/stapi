package com.cezarykluczynski.stapi.model.book.repository

import com.cezarykluczynski.stapi.model.book.dto.BookRequestDTO
import com.cezarykluczynski.stapi.model.book.entity.Book
import com.cezarykluczynski.stapi.model.book.entity.Book_
import com.cezarykluczynski.stapi.model.book.query.BookInitialQueryBuilderFactory
import com.cezarykluczynski.stapi.model.book_collection.entity.BookCollection
import com.cezarykluczynski.stapi.model.book_series.entity.BookSeries
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.model.reference.entity.Reference
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import spock.lang.Specification

class BookRepositoryImplTest extends Specification {

	private static final String UID = 'ABCD0123456789'

	private BookInitialQueryBuilderFactory bookInitialQueryBuilderFactory

	private BookRepositoryImpl bookRepositoryImpl

	private QueryBuilder<Book> bookQueryBuilder

	private QueryBuilder<Book> bookBookSeriesBookCollectionsPublishersQueryBuilder

	private QueryBuilder<Book> bookCharactersReferencesQueryBuilder

	private Pageable pageable

	private BookRequestDTO bookRequestDTO

	private Book book

	private Book bookBookSeriesPublishersBook

	private Book charactersReferencesBook

	private Page page

	private Page performersPage

	private Page charactersPage

	private Set<BookSeries> bookSeriesSet

	private Set<Company> publishersSet

	private Set<Character> charactersSet

	private Set<Reference> referencesSet

	private Set<Reference> audiobookReferencesSet

	private Set<Company> audiobookPublishersSet

	private Set<BookCollection> bookCollectionsSet

	void setup() {
		bookInitialQueryBuilderFactory = Mock()
		bookRepositoryImpl = new BookRepositoryImpl(bookInitialQueryBuilderFactory)
		bookQueryBuilder = Mock()
		bookBookSeriesBookCollectionsPublishersQueryBuilder = Mock()
		bookCharactersReferencesQueryBuilder = Mock()
		pageable = Mock()
		bookRequestDTO = Mock()
		page = Mock()
		performersPage = Mock()
		charactersPage = Mock()
		book = Mock()
		bookBookSeriesPublishersBook = Mock()
		charactersReferencesBook = Mock()
		bookSeriesSet = Mock()
		publishersSet = Mock()
		charactersSet = Mock()
		referencesSet = Mock()
		audiobookReferencesSet = Mock()
		audiobookPublishersSet = Mock()
		bookCollectionsSet = Mock()
	}

	void "query is built and performed"() {
		when:
		Page pageOutput = bookRepositoryImpl.findMatching(bookRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * bookInitialQueryBuilderFactory.createInitialQueryBuilder(bookRequestDTO, pageable) >> bookQueryBuilder

		then: 'uid is retrieved, and it is not null'
		1 * bookRequestDTO.uid >> UID

		then: 'staff fetch is performed'
		1 * bookQueryBuilder.fetch(Book_.authors)
		1 * bookQueryBuilder.fetch(Book_.artists)
		1 * bookQueryBuilder.fetch(Book_.editors)
		1 * bookQueryBuilder.fetch(Book_.audiobookNarrators)

		then: 'page is retrieved'
		1 * bookQueryBuilder.findPage() >> page
		1 * page.content >> Lists.newArrayList(book)

		then: 'another criteria builder is retrieved for book series and publishers'
		1 * bookInitialQueryBuilderFactory.createInitialQueryBuilder(bookRequestDTO, pageable) >>
				bookBookSeriesBookCollectionsPublishersQueryBuilder

		then: 'book series, and publishers fetch is performed'
		1 * bookBookSeriesBookCollectionsPublishersQueryBuilder.fetch(Book_.bookSeries)
		1 * bookBookSeriesBookCollectionsPublishersQueryBuilder.fetch(Book_.publishers)
		1 * bookBookSeriesBookCollectionsPublishersQueryBuilder.fetch(Book_.audiobookPublishers)
		1 * bookBookSeriesBookCollectionsPublishersQueryBuilder.fetch(Book_.bookCollections)

		then: 'book series and publishers list is retrieved'
		1 * bookBookSeriesBookCollectionsPublishersQueryBuilder.findAll() >> Lists.newArrayList(bookBookSeriesPublishersBook)

		then: 'book series and publishers are set to book'
		1 * bookBookSeriesPublishersBook.bookSeries >> bookSeriesSet
		1 * book.setBookSeries(bookSeriesSet)
		1 * bookBookSeriesPublishersBook.publishers >> publishersSet
		1 * book.setPublishers(publishersSet)
		1 * bookBookSeriesPublishersBook.audiobookPublishers >> audiobookPublishersSet
		1 * book.setAudiobookPublishers(audiobookPublishersSet)
		1 * bookBookSeriesPublishersBook.bookCollections >> bookCollectionsSet
		1 * book.setBookCollections(bookCollectionsSet)

		then: 'another criteria builder is retrieved for characters and references'
		1 * bookInitialQueryBuilderFactory.createInitialQueryBuilder(bookRequestDTO, pageable) >> bookCharactersReferencesQueryBuilder

		then: 'characters and references fetch is performed'
		1 * bookCharactersReferencesQueryBuilder.fetch(Book_.characters)
		1 * bookCharactersReferencesQueryBuilder.fetch(Book_.references)
		1 * bookCharactersReferencesQueryBuilder.fetch(Book_.audiobookReferences)

		then: 'characters and references list is retrieved'
		1 * bookCharactersReferencesQueryBuilder.findAll() >> Lists.newArrayList(charactersReferencesBook)

		then: 'characters and references are set to book'
		1 * charactersReferencesBook.characters >> charactersSet
		1 * book.setCharacters(charactersSet)
		1 * charactersReferencesBook.references >> referencesSet
		1 * book.setReferences(referencesSet)
		1 * charactersReferencesBook.audiobookReferences >> audiobookReferencesSet
		1 * book.setAudiobookReferences(audiobookReferencesSet)

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

	void "query is built and performed without results from additional queries"() {
		when:
		Page pageOutput = bookRepositoryImpl.findMatching(bookRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * bookInitialQueryBuilderFactory.createInitialQueryBuilder(bookRequestDTO, pageable) >> bookQueryBuilder

		then: 'uid is retrieved, and it is not null'
		1 * bookRequestDTO.uid >> UID

		then: 'staff fetch is performed'
		1 * bookQueryBuilder.fetch(Book_.authors)
		1 * bookQueryBuilder.fetch(Book_.artists)
		1 * bookQueryBuilder.fetch(Book_.editors)
		1 * bookQueryBuilder.fetch(Book_.audiobookNarrators)

		then: 'page is retrieved'
		1 * bookQueryBuilder.findPage() >> page
		1 * page.content >> Lists.newArrayList(book)

		then: 'another criteria builder is retrieved for book series and publishers'
		1 * bookInitialQueryBuilderFactory.createInitialQueryBuilder(bookRequestDTO, pageable) >>
				bookBookSeriesBookCollectionsPublishersQueryBuilder

		then: 'book series and publishers fetch is performed'
		1 * bookBookSeriesBookCollectionsPublishersQueryBuilder.fetch(Book_.bookSeries)
		1 * bookBookSeriesBookCollectionsPublishersQueryBuilder.fetch(Book_.publishers)
		1 * bookBookSeriesBookCollectionsPublishersQueryBuilder.fetch(Book_.audiobookPublishers)
		1 * bookBookSeriesBookCollectionsPublishersQueryBuilder.fetch(Book_.bookCollections)

		then: 'empty book series and publishers list is retrieved'
		1 * bookBookSeriesBookCollectionsPublishersQueryBuilder.findAll() >> Lists.newArrayList()

		then: 'another criteria builder is retrieved for characters and references'
		1 * bookInitialQueryBuilderFactory.createInitialQueryBuilder(bookRequestDTO, pageable) >> bookCharactersReferencesQueryBuilder

		then: 'characters and references fetch is performed'
		1 * bookCharactersReferencesQueryBuilder.fetch(Book_.characters)
		1 * bookCharactersReferencesQueryBuilder.fetch(Book_.references)
		1 * bookCharactersReferencesQueryBuilder.fetch(Book_.audiobookReferences)

		then: 'empty characters and references list is retrieved'
		1 * bookCharactersReferencesQueryBuilder.findAll() >> Lists.newArrayList()

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

	void "empty page is returned"() {
		when:
		Page pageOutput = bookRepositoryImpl.findMatching(bookRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * bookInitialQueryBuilderFactory.createInitialQueryBuilder(bookRequestDTO, pageable) >> bookQueryBuilder

		then: 'uid is retrieved, and it is not null'
		1 * bookRequestDTO.uid >> UID

		then: 'staff fetch is performed'
		1 * bookQueryBuilder.fetch(Book_.authors)
		1 * bookQueryBuilder.fetch(Book_.artists)
		1 * bookQueryBuilder.fetch(Book_.editors)
		1 * bookQueryBuilder.fetch(Book_.audiobookNarrators)

		then: 'page is retrieved'
		1 * bookQueryBuilder.findPage() >> page
		1 * page.content >> Lists.newArrayList()

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

	void "proxies are cleared when no related entities should be fetched"() {
		when:
		Page pageOutput = bookRepositoryImpl.findMatching(bookRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * bookInitialQueryBuilderFactory.createInitialQueryBuilder(bookRequestDTO, pageable) >> bookQueryBuilder

		then: 'uid criteria is set to null'
		1 * bookRequestDTO.uid >> null

		then: 'page is searched for and returned'
		1 * bookQueryBuilder.findPage() >> page

		then: 'proxies are cleared'
		1 * page.content >> Lists.newArrayList(book)
		1 * book.setAuthors(Sets.newHashSet())
		1 * book.setArtists(Sets.newHashSet())
		1 * book.setEditors(Sets.newHashSet())
		1 * book.setAudiobookNarrators(Sets.newHashSet())
		1 * book.setBookSeries(Sets.newHashSet())
		1 * book.setPublishers(Sets.newHashSet())
		1 * book.setAudiobookPublishers(Sets.newHashSet())
		1 * book.setCharacters(Sets.newHashSet())
		1 * book.setReferences(Sets.newHashSet())
		1 * book.setAudiobookReferences(Sets.newHashSet())
		1 * book.setBookCollections(Sets.newHashSet())
		pageOutput == page
	}

}
