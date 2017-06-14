package com.cezarykluczynski.stapi.server.book_collection.mapper

import com.cezarykluczynski.stapi.model.book.entity.Book
import com.cezarykluczynski.stapi.model.book_collection.entity.BookCollection
import com.cezarykluczynski.stapi.model.book_series.entity.BookSeries
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.model.reference.entity.Reference
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.util.AbstractBookCollectionTest

abstract class AbstractBookCollectionMapperTest extends AbstractBookCollectionTest {

	protected BookCollection createBookCollection() {
		new BookCollection(
				uid: UID,
				title: TITLE,
				publishedYear: PUBLISHED_YEAR,
				publishedMonth: PUBLISHED_MONTH,
				publishedDay: PUBLISHED_DAY,
				numberOfPages: NUMBER_OF_PAGES,
				stardateFrom: STARDATE_FROM,
				stardateTo: STARDATE_TO,
				yearFrom: YEAR_FROM,
				yearTo: YEAR_TO,
				bookSeries: createSetOfRandomNumberOfMocks(BookSeries),
				authors: createSetOfRandomNumberOfMocks(Staff),
				artists: createSetOfRandomNumberOfMocks(Staff),
				editors: createSetOfRandomNumberOfMocks(Staff),
				characters: createSetOfRandomNumberOfMocks(Character),
				publishers: createSetOfRandomNumberOfMocks(Company),
				references: createSetOfRandomNumberOfMocks(Reference),
				books: createSetOfRandomNumberOfMocks(Book))
	}

}
