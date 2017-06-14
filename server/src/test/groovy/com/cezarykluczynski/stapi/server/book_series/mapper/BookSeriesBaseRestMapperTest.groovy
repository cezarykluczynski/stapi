package com.cezarykluczynski.stapi.server.book_series.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.BookSeriesBase as BookSeriesBase
import com.cezarykluczynski.stapi.model.book_series.dto.BookSeriesRequestDTO
import com.cezarykluczynski.stapi.model.book_series.entity.BookSeries as BookSeries
import com.cezarykluczynski.stapi.server.book_series.dto.BookSeriesRestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class BookSeriesBaseRestMapperTest extends AbstractBookSeriesMapperTest {

	private BookSeriesBaseRestMapper bookSeriesBaseRestMapper

	void setup() {
		bookSeriesBaseRestMapper = Mappers.getMapper(BookSeriesBaseRestMapper)
	}

	void "maps BookSeriesRestBeanParams to BookSeriesRequestDTO"() {
		given:
		BookSeriesRestBeanParams bookSeriesRestBeanParams = new BookSeriesRestBeanParams(
				uid: UID,
				title: TITLE,
				publishedYearFrom: PUBLISHED_YEAR_FROM,
				publishedYearTo: PUBLISHED_YEAR_TO,
				numberOfBooksFrom: NUMBER_OF_BOOKS_FROM,
				numberOfBooksTo: NUMBER_OF_BOOKS_TO,
				yearFrom: YEAR_FROM,
				yearTo: YEAR_TO,
				miniseries: MINISERIES,
				eBookSeries: E_BOOK_SERIES)

		when:
		BookSeriesRequestDTO bookSeriesRequestDTO = bookSeriesBaseRestMapper.mapBase bookSeriesRestBeanParams

		then:
		bookSeriesRequestDTO.uid == UID
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

	void "maps DB entity to base REST entity"() {
		given:
		BookSeries bookSeries = createBookSeries()

		when:
		BookSeriesBase restBookSeries = bookSeriesBaseRestMapper.mapBase(Lists.newArrayList(bookSeries))[0]

		then:
		restBookSeries.uid == UID
		restBookSeries.title == TITLE
		restBookSeries.publishedYearFrom == PUBLISHED_YEAR_FROM
		restBookSeries.publishedMonthFrom == PUBLISHED_MONTH_FROM
		restBookSeries.publishedYearTo == PUBLISHED_YEAR_TO
		restBookSeries.publishedMonthTo == PUBLISHED_MONTH_TO
		restBookSeries.numberOfBooks == NUMBER_OF_BOOKS
		restBookSeries.yearFrom == YEAR_FROM
		restBookSeries.yearTo == YEAR_TO
		restBookSeries.miniseries == MINISERIES
		restBookSeries.EBookSeries == E_BOOK_SERIES
	}

}
