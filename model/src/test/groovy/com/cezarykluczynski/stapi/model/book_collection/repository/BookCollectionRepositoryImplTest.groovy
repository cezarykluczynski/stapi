package com.cezarykluczynski.stapi.model.book_collection.repository

import com.cezarykluczynski.stapi.model.book_collection.dto.BookCollectionRequestDTO
import com.cezarykluczynski.stapi.model.book_collection.entity.BookCollection
import com.cezarykluczynski.stapi.model.book_collection.entity.BookCollection_
import com.cezarykluczynski.stapi.model.book_collection.query.BookCollectionQueryBuilderFactory
import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.util.AbstractBookCollectionTest
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class BookCollectionRepositoryImplTest extends AbstractBookCollectionTest {

	private static final RequestSortDTO SORT = new RequestSortDTO()

	private BookCollectionQueryBuilderFactory bookCollectionQueryBuilderFactory

	private BookCollectionRepositoryImpl bookCollectionRepositoryImpl

	private QueryBuilder<BookCollection> bookCollectionQueryBuilder

	private Pageable pageable

	private BookCollectionRequestDTO bookCollectionRequestDTO

	private BookCollection bookCollection

	private Page page

	void setup() {
		bookCollectionQueryBuilderFactory = Mock()
		bookCollectionRepositoryImpl = new BookCollectionRepositoryImpl(bookCollectionQueryBuilderFactory)
		bookCollectionQueryBuilder = Mock()
		pageable = Mock()
		bookCollectionRequestDTO = Mock()
		page = Mock()
		bookCollection = Mock()
	}

	void "query is built and performed"() {
		when:
		Page pageOutput = bookCollectionRepositoryImpl.findMatching(bookCollectionRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * bookCollectionQueryBuilderFactory.createQueryBuilder(pageable) >> bookCollectionQueryBuilder

		then: 'uid criteria is set'
		1 * bookCollectionRequestDTO.uid >> UID
		1 * bookCollectionQueryBuilder.equal(BookCollection_.uid, UID)

		then: 'string criteria are set'
		1 * bookCollectionRequestDTO.title >> TITLE
		1 * bookCollectionQueryBuilder.like(BookCollection_.title, TITLE)

		then: 'integer criteria are set'
		1 * bookCollectionRequestDTO.publishedYearFrom >> PUBLISHED_YEAR_FROM
		1 * bookCollectionRequestDTO.publishedYearTo >> PUBLISHED_YEAR_TO
		1 * bookCollectionQueryBuilder.between(BookCollection_.publishedYear, PUBLISHED_YEAR_FROM, PUBLISHED_YEAR_TO)
		1 * bookCollectionRequestDTO.numberOfPagesFrom >> NUMBER_OF_PAGES_FROM
		1 * bookCollectionRequestDTO.numberOfPagesTo >> NUMBER_OF_PAGES_TO
		1 * bookCollectionQueryBuilder.between(BookCollection_.numberOfPages, NUMBER_OF_PAGES_FROM, NUMBER_OF_PAGES_TO)

		1 * bookCollectionRequestDTO.yearFrom >> YEAR_FROM
		1 * bookCollectionQueryBuilder.between(BookCollection_.yearFrom, YEAR_FROM, null)
		1 * bookCollectionRequestDTO.yearTo >> YEAR_TO
		1 * bookCollectionQueryBuilder.between(BookCollection_.yearTo, null, YEAR_TO)

		then: 'float criteria are set'
		1 * bookCollectionRequestDTO.stardateFrom >> STARDATE_FROM
		1 * bookCollectionQueryBuilder.between(BookCollection_.stardateFrom, STARDATE_FROM, null)
		1 * bookCollectionRequestDTO.stardateTo >> STARDATE_TO
		1 * bookCollectionQueryBuilder.between(BookCollection_.stardateTo, null, STARDATE_TO)

		then: 'sort is set'
		1 * bookCollectionRequestDTO.sort >> SORT
		1 * bookCollectionQueryBuilder.setSort(SORT)

		then: 'fetch is performed'
		1 * bookCollectionQueryBuilder.fetch(BookCollection_.authors, true)
		1 * bookCollectionQueryBuilder.fetch(BookCollection_.artists, true)
		1 * bookCollectionQueryBuilder.fetch(BookCollection_.editors, true)
		1 * bookCollectionQueryBuilder.divideQueries()
		1 * bookCollectionQueryBuilder.fetch(BookCollection_.bookSeries, true)
		1 * bookCollectionQueryBuilder.fetch(BookCollection_.publishers, true)
		1 * bookCollectionQueryBuilder.divideQueries()
		1 * bookCollectionQueryBuilder.fetch(BookCollection_.books, true)
		1 * bookCollectionQueryBuilder.divideQueries()
		1 * bookCollectionQueryBuilder.fetch(BookCollection_.characters, true)
		1 * bookCollectionQueryBuilder.divideQueries()
		1 * bookCollectionQueryBuilder.fetch(BookCollection_.references, true)

		then: 'page is retrieved'
		1 * bookCollectionQueryBuilder.findPage() >> page

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

	void "proxies are cleared when no related entities should be fetched"() {
		when:
		Page pageOutput = bookCollectionRepositoryImpl.findMatching(bookCollectionRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * bookCollectionQueryBuilderFactory.createQueryBuilder(pageable) >> bookCollectionQueryBuilder

		then: 'uid criteria is set to null'
		1 * bookCollectionRequestDTO.uid >> null

		then: 'page is searched for and returned'
		1 * bookCollectionQueryBuilder.findPage() >> page

		then: 'proxies are cleared'
		1 * page.content >> Lists.newArrayList(bookCollection)
		1 * bookCollection.setBookSeries(Sets.newHashSet())
		1 * bookCollection.setAuthors(Sets.newHashSet())
		1 * bookCollection.setArtists(Sets.newHashSet())
		1 * bookCollection.setEditors(Sets.newHashSet())
		1 * bookCollection.setPublishers(Sets.newHashSet())
		1 * bookCollection.setCharacters(Sets.newHashSet())
		1 * bookCollection.setReferences(Sets.newHashSet())
		1 * bookCollection.setBooks(Sets.newHashSet())
		pageOutput == page
	}

}
