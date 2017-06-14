package com.cezarykluczynski.stapi.server.book_series.mapper

import com.cezarykluczynski.stapi.model.book.entity.Book
import com.cezarykluczynski.stapi.model.book_series.entity.BookSeries
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.util.AbstractBookSeriesTest

abstract class AbstractBookSeriesMapperTest extends AbstractBookSeriesTest {

	protected BookSeries createBookSeries() {
		new BookSeries(
				uid: UID,
				title: TITLE,
				publishedYearFrom: PUBLISHED_YEAR_FROM,
				publishedMonthFrom: PUBLISHED_MONTH_FROM,
				publishedYearTo: PUBLISHED_YEAR_TO,
				publishedMonthTo: PUBLISHED_MONTH_TO,
				numberOfBooks: NUMBER_OF_BOOKS,
				yearFrom: YEAR_FROM,
				yearTo: YEAR_TO,
				miniseries: MINISERIES,
				eBookSeries: E_BOOK_SERIES,
				parentSeries: createSetOfRandomNumberOfMocks(BookSeries),
				childSeries: createSetOfRandomNumberOfMocks(BookSeries),
				publishers: createSetOfRandomNumberOfMocks(Company),
				books: createSetOfRandomNumberOfMocks(Book))
	}

}
