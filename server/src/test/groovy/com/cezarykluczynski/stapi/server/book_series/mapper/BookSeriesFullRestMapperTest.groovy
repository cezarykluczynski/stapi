package com.cezarykluczynski.stapi.server.book_series.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.BookSeriesFull
import com.cezarykluczynski.stapi.model.book_series.entity.BookSeries
import org.mapstruct.factory.Mappers

class BookSeriesFullRestMapperTest extends AbstractBookSeriesMapperTest {

	private BookSeriesFullRestMapper bookSeriesFullRestMapper

	void setup() {
		bookSeriesFullRestMapper = Mappers.getMapper(BookSeriesFullRestMapper)
	}

	void "maps DB entity to full REST entity"() {
		given:
		BookSeries bookSeries = createBookSeries()

		when:
		BookSeriesFull bookSeriesFull = bookSeriesFullRestMapper.mapFull(bookSeries)

		then:
		bookSeriesFull.uid == UID
		bookSeriesFull.title == TITLE
		bookSeriesFull.publishedYearFrom == PUBLISHED_YEAR_FROM
		bookSeriesFull.publishedMonthFrom == PUBLISHED_MONTH_FROM
		bookSeriesFull.publishedYearTo == PUBLISHED_YEAR_TO
		bookSeriesFull.publishedMonthTo == PUBLISHED_MONTH_TO
		bookSeriesFull.numberOfBooks == NUMBER_OF_BOOKS
		bookSeriesFull.yearFrom == YEAR_FROM
		bookSeriesFull.yearTo == YEAR_TO
		bookSeriesFull.miniseries == MINISERIES
		bookSeriesFull.EBookSeries == E_BOOK_SERIES
		bookSeriesFull.parentSeries.size() == bookSeries.parentSeries.size()
		bookSeriesFull.childSeries.size() == bookSeries.childSeries.size()
		bookSeriesFull.publishers.size() == bookSeries.publishers.size()
		bookSeriesFull.books.size() == bookSeries.books.size()
	}

}
