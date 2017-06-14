package com.cezarykluczynski.stapi.model.book_collection.query

import com.cezarykluczynski.stapi.model.book_collection.dto.BookCollectionRequestDTO
import com.cezarykluczynski.stapi.model.book_collection.entity.BookCollection
import com.cezarykluczynski.stapi.model.book_collection.entity.BookCollection_
import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.util.AbstractBookCollectionTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class BookCollectionInitialQueryBuilderFactoryTest extends AbstractBookCollectionTest {

	private static final RequestSortDTO SORT = new RequestSortDTO()

	private BookCollectionQueryBuilderFactory bookCollectionQueryBuilderMock

	private BookCollectionInitialQueryBuilderFactory bookCollectionInitialQueryBuilderFactory

	private QueryBuilder<BookCollection> bookCollectionQueryBuilder

	private Pageable pageable

	private BookCollectionRequestDTO bookCollectionRequestDTO

	private BookCollection bookCollection

	private Page page

	void setup() {
		bookCollectionQueryBuilderMock = Mock()
		bookCollectionInitialQueryBuilderFactory = new BookCollectionInitialQueryBuilderFactory(bookCollectionQueryBuilderMock)
		bookCollectionQueryBuilder = Mock()
		pageable = Mock()
		bookCollectionRequestDTO = Mock()
		page = Mock()
		bookCollection = Mock()
	}

	void "initial query builder is built, then returned"() {
		when:
		QueryBuilder<BookCollection> bookCollectionQueryBuilderOutput = bookCollectionInitialQueryBuilderFactory
				.createInitialQueryBuilder(bookCollectionRequestDTO, pageable)

		then:
		1 * bookCollectionQueryBuilderMock.createQueryBuilder(pageable) >> bookCollectionQueryBuilder

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

		then: 'query builder is returned'
		bookCollectionQueryBuilderOutput == bookCollectionQueryBuilder

		then: 'no other interactions are expected'
		0 * _
	}

}
