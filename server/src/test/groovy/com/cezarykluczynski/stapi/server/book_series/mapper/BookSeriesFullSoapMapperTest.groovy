package com.cezarykluczynski.stapi.server.book_series.mapper

import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesFull
import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesFullRequest
import com.cezarykluczynski.stapi.model.book_series.dto.BookSeriesRequestDTO
import com.cezarykluczynski.stapi.model.book_series.entity.BookSeries
import org.mapstruct.factory.Mappers

class BookSeriesFullSoapMapperTest extends AbstractBookSeriesMapperTest {

	private BookSeriesFullSoapMapper bookSeriesFullSoapMapper

	void setup() {
		bookSeriesFullSoapMapper = Mappers.getMapper(BookSeriesFullSoapMapper)
	}

	void "maps SOAP BookSeriesFullRequest to BookSeriesBaseRequestDTO"() {
		given:
		BookSeriesFullRequest bookSeriesFullRequest = new BookSeriesFullRequest(uid: UID)

		when:
		BookSeriesRequestDTO bookSeriesRequestDTO = bookSeriesFullSoapMapper.mapFull bookSeriesFullRequest

		then:
		bookSeriesRequestDTO.uid == UID
	}

	void "maps DB entity to base SOAP entity"() {
		given:
		BookSeries bookSeries = createBookSeries()

		when:
		BookSeriesFull bookSeriesFull = bookSeriesFullSoapMapper.mapFull(bookSeries)

		then:
		bookSeriesFull.uid == UID
		bookSeriesFull.title == TITLE
		bookSeriesFull.publishedYearFrom == PUBLISHED_YEAR_FROM
		bookSeriesFull.publishedMonthFrom == PUBLISHED_MONTH_FROM
		bookSeriesFull.publishedYearTo == PUBLISHED_YEAR_TO
		bookSeriesFull.publishedMonthTo == PUBLISHED_MONTH_TO
		bookSeriesFull.numberOfBooks == NUMBER_OF_BOOKS
		bookSeriesFull.yearFrom.toInteger() == YEAR_FROM
		bookSeriesFull.yearTo.toInteger() == YEAR_TO
		bookSeriesFull.miniseries == MINISERIES
		bookSeriesFull.EBookSeries == E_BOOK_SERIES
		bookSeriesFull.parentSeries.size() == bookSeries.parentSeries.size()
		bookSeriesFull.childSeries.size() == bookSeries.childSeries.size()
		bookSeriesFull.publishers.size() == bookSeries.publishers.size()
		bookSeriesFull.books.size() == bookSeries.books.size()
	}

}
