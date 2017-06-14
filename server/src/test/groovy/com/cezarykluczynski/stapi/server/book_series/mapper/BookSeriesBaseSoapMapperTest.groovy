package com.cezarykluczynski.stapi.server.book_series.mapper

import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesBase
import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.IntegerRange
import com.cezarykluczynski.stapi.model.book_series.dto.BookSeriesRequestDTO
import com.cezarykluczynski.stapi.model.book_series.entity.BookSeries as BookSeries
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class BookSeriesBaseSoapMapperTest extends AbstractBookSeriesMapperTest {

	private BookSeriesBaseSoapMapper bookSeriesBaseSoapMapper

	void setup() {
		bookSeriesBaseSoapMapper = Mappers.getMapper(BookSeriesBaseSoapMapper)
	}

	void "maps SOAP BookSeriesRequest to BookSeriesRequestDTO"() {
		given:
		BookSeriesBaseRequest bookSeriesBaseRequest = new BookSeriesBaseRequest(
				title: TITLE,
				publishedYear: new IntegerRange(
						from: PUBLISHED_YEAR_FROM,
						to: PUBLISHED_YEAR_TO
				),
				numberOfBooks: new IntegerRange(
						from: NUMBER_OF_BOOKS_FROM,
						to: NUMBER_OF_BOOKS_TO
				),
				year: new IntegerRange(
						from: YEAR_FROM,
						to: YEAR_TO,
				),
				miniseries: MINISERIES,
				eBookSeries: E_BOOK_SERIES)

		when:
		BookSeriesRequestDTO bookSeriesRequestDTO = bookSeriesBaseSoapMapper.mapBase bookSeriesBaseRequest

		then:
		bookSeriesRequestDTO.title == TITLE
		bookSeriesRequestDTO.publishedYearFrom == PUBLISHED_YEAR_FROM
		bookSeriesRequestDTO.publishedYearTo == PUBLISHED_YEAR_TO
		bookSeriesRequestDTO.numberOfBooksFrom == NUMBER_OF_BOOKS_FROM
		bookSeriesRequestDTO.numberOfBooksTo == NUMBER_OF_BOOKS_TO
		bookSeriesRequestDTO.yearFrom == YEAR_FROM
		bookSeriesRequestDTO.yearTo == YEAR_TO
		bookSeriesRequestDTO.miniseries == MINISERIES
		bookSeriesRequestDTO.EBookSeries == E_BOOK_SERIES
	}

	void "maps DB entity to base SOAP entity"() {
		given:
		BookSeries bookSeries = createBookSeries()

		when:
		BookSeriesBase bookSeriesBase = bookSeriesBaseSoapMapper.mapBase(Lists.newArrayList(bookSeries))[0]

		then:
		bookSeriesBase.uid == UID
		bookSeriesBase.title == TITLE
		bookSeriesBase.publishedYearFrom == PUBLISHED_YEAR_FROM
		bookSeriesBase.publishedMonthFrom == PUBLISHED_MONTH_FROM
		bookSeriesBase.publishedYearTo == PUBLISHED_YEAR_TO
		bookSeriesBase.publishedMonthTo == PUBLISHED_MONTH_TO
		bookSeriesBase.numberOfBooks == NUMBER_OF_BOOKS
		bookSeriesBase.yearFrom.toInteger() == YEAR_FROM
		bookSeriesBase.yearTo.toInteger() == YEAR_TO
		bookSeriesBase.miniseries == MINISERIES
		bookSeriesBase.EBookSeries == E_BOOK_SERIES
	}

}
