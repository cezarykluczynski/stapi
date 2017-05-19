package com.cezarykluczynski.stapi.model.book.query

import com.cezarykluczynski.stapi.model.book.dto.BookRequestDTO
import com.cezarykluczynski.stapi.model.book.entity.Book
import com.cezarykluczynski.stapi.model.book.entity.Book_
import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.util.AbstractBookTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class BookInitialQueryBuilderFactoryTest extends AbstractBookTest {

	private static final RequestSortDTO SORT = new RequestSortDTO()

	private BookQueryBuilderFactory bookQueryBuilderMock

	private BookInitialQueryBuilderFactory bookInitialQueryBuilderFactory

	private QueryBuilder<Book> bookQueryBuilder

	private Pageable pageable

	private BookRequestDTO bookRequestDTO

	private Book book

	private Page page

	void setup() {
		bookQueryBuilderMock = Mock()
		bookInitialQueryBuilderFactory = new BookInitialQueryBuilderFactory(bookQueryBuilderMock)
		bookQueryBuilder = Mock()
		pageable = Mock()
		bookRequestDTO = Mock()
		page = Mock()
		book = Mock()
	}

	void "initial query builder is built, then returned"() {
		when:
		QueryBuilder<Book> bookQueryBuilderOutput = bookInitialQueryBuilderFactory.createInitialQueryBuilder(bookRequestDTO, pageable)

		then:
		1 * bookQueryBuilderMock.createQueryBuilder(pageable) >> bookQueryBuilder

		then: 'uid criteria is set'
		1 * bookRequestDTO.uid >> UID
		1 * bookQueryBuilder.equal(Book_.uid, UID)

		then: 'string criteria are set'
		1 * bookRequestDTO.title >> TITLE
		1 * bookQueryBuilder.like(Book_.title, TITLE)

		then: 'integer criteria are set'
		1 * bookRequestDTO.publishedYearFrom >> PUBLISHED_YEAR_FROM
		1 * bookRequestDTO.publishedYearTo >> PUBLISHED_YEAR_TO
		1 * bookQueryBuilder.between(Book_.publishedYear, PUBLISHED_YEAR_FROM, PUBLISHED_YEAR_TO)
		1 * bookRequestDTO.numberOfPagesFrom >> NUMBER_OF_PAGES_FROM
		1 * bookRequestDTO.numberOfPagesTo >> NUMBER_OF_PAGES_TO
		1 * bookQueryBuilder.between(Book_.numberOfPages, NUMBER_OF_PAGES_FROM, NUMBER_OF_PAGES_TO)

		1 * bookRequestDTO.yearFrom >> YEAR_FROM
		1 * bookQueryBuilder.between(Book_.yearFrom, YEAR_FROM, null)
		1 * bookRequestDTO.yearTo >> YEAR_TO
		1 * bookQueryBuilder.between(Book_.yearTo, null, YEAR_TO)

		1 * bookRequestDTO.audiobookPublishedYearFrom >> AUDIOBOOK_PUBLISHED_YEAR_FROM
		1 * bookRequestDTO.audiobookPublishedYearTo >> AUDIOBOOK_PUBLISHED_YEAR_TO
		1 * bookQueryBuilder.between(Book_.audiobookPublishedYear, AUDIOBOOK_PUBLISHED_YEAR_FROM, AUDIOBOOK_PUBLISHED_YEAR_TO)
		1 * bookRequestDTO.audiobookRunTimeFrom >> AUDIOBOOK_RUN_TIME_FROM
		1 * bookRequestDTO.audiobookRunTimeTo >> AUDIOBOOK_RUN_TIME_TO
		1 * bookQueryBuilder.between(Book_.audiobookRunTime, AUDIOBOOK_RUN_TIME_FROM, AUDIOBOOK_RUN_TIME_TO)

		then: 'float criteria are set'
		1 * bookRequestDTO.stardateFrom >> STARDATE_FROM
		1 * bookQueryBuilder.between(Book_.stardateFrom, STARDATE_FROM, null)
		1 * bookRequestDTO.stardateTo >> STARDATE_TO
		1 * bookQueryBuilder.between(Book_.stardateTo, null, STARDATE_TO)

		then: 'boolean criteria are set'
		1 * bookRequestDTO.novel >> NOVEL
		1 * bookQueryBuilder.equal(Book_.novel, NOVEL)
		1 * bookRequestDTO.referenceBook >> REFERENCE_BOOK
		1 * bookQueryBuilder.equal(Book_.referenceBook, REFERENCE_BOOK)

		1 * bookRequestDTO.biographyBook >> BIOGRAPHY_BOOK
		1 * bookQueryBuilder.equal(Book_.biographyBook, BIOGRAPHY_BOOK)
		1 * bookRequestDTO.rolePlayingBook >> ROLE_PLAYING_BOOK
		1 * bookQueryBuilder.equal(Book_.rolePlayingBook, ROLE_PLAYING_BOOK)
		1 * bookRequestDTO.EBook >> E_BOOK
		1 * bookQueryBuilder.equal(Book_.eBook, E_BOOK)
		1 * bookRequestDTO.anthology >> ANTHOLOGY
		1 * bookQueryBuilder.equal(Book_.anthology, ANTHOLOGY)
		1 * bookRequestDTO.novelization >> NOVELIZATION
		1 * bookQueryBuilder.equal(Book_.novelization, NOVELIZATION)
		1 * bookRequestDTO.audiobook >> AUDIOBOOK
		1 * bookQueryBuilder.equal(Book_.audiobook, AUDIOBOOK)
		1 * bookRequestDTO.audiobookAbridged >> AUDIOBOOK_ABRIDGED
		1 * bookQueryBuilder.equal(Book_.audiobookAbridged, AUDIOBOOK_ABRIDGED)

		then: 'sort is set'
		1 * bookRequestDTO.sort >> SORT
		1 * bookQueryBuilder.setSort(SORT)

		then: 'query builder is returned'
		bookQueryBuilderOutput == bookQueryBuilder

		then: 'no other interactions are expected'
		0 * _
	}

}
