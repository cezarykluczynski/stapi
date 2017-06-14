package com.cezarykluczynski.stapi.server.book.mapper

import com.cezarykluczynski.stapi.model.book.entity.Book
import com.cezarykluczynski.stapi.model.book_series.entity.BookSeries
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.model.reference.entity.Reference
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.util.AbstractBookTest

abstract class AbstractBookMapperTest extends AbstractBookTest {

	protected Book createBook() {
		new Book(
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
				novel: NOVEL,
				referenceBook: REFERENCE_BOOK,
				biographyBook: BIOGRAPHY_BOOK,
				rolePlayingBook: ROLE_PLAYING_BOOK,
				eBook: E_BOOK,
				anthology: ANTHOLOGY,
				novelization: NOVELIZATION,
				audiobook: AUDIOBOOK,
				audiobookAbridged: AUDIOBOOK_ABRIDGED,
				audiobookPublishedYear: AUDIOBOOK_PUBLISHED_YEAR,
				audiobookPublishedMonth: AUDIOBOOK_PUBLISHED_MONTH,
				audiobookPublishedDay: AUDIOBOOK_PUBLISHED_DAY,
				audiobookRunTime: AUDIOBOOK_RUN_TIME,
				productionNumber: PRODUCTION_NUMBER,
				bookSeries: createSetOfRandomNumberOfMocks(BookSeries),
				authors: createSetOfRandomNumberOfMocks(Staff),
				artists: createSetOfRandomNumberOfMocks(Staff),
				editors: createSetOfRandomNumberOfMocks(Staff),
				audiobookNarrators: createSetOfRandomNumberOfMocks(Staff),
				publishers: createSetOfRandomNumberOfMocks(Company),
				audiobookPublishers: createSetOfRandomNumberOfMocks(Company),
				characters: createSetOfRandomNumberOfMocks(Character),
				references: createSetOfRandomNumberOfMocks(Reference),
				audiobookReferences: createSetOfRandomNumberOfMocks(Reference))
	}

}
