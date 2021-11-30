package com.cezarykluczynski.stapi.server.book.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.BookFull
import com.cezarykluczynski.stapi.client.v1.rest.model.BookV2Full
import com.cezarykluczynski.stapi.model.book.entity.Book
import org.mapstruct.factory.Mappers

class BookFullRestMapperTest extends AbstractBookMapperTest {

	private BookFullRestMapper bookFullRestMapper

	void setup() {
		bookFullRestMapper = Mappers.getMapper(BookFullRestMapper)
	}

	void "maps DB entity to full REST entity"() {
		given:
		Book book = createBook()

		when:
		BookFull bookFull = bookFullRestMapper.mapFull(book)

		then:
		bookFull.uid == UID
		bookFull.title == TITLE
		bookFull.publishedYear == PUBLISHED_YEAR
		bookFull.publishedMonth == PUBLISHED_MONTH
		bookFull.publishedDay == PUBLISHED_DAY
		bookFull.numberOfPages == NUMBER_OF_PAGES
		bookFull.stardateFrom == STARDATE_FROM
		bookFull.stardateTo == STARDATE_TO
		bookFull.yearFrom == YEAR_FROM
		bookFull.yearTo == YEAR_TO
		bookFull.novel == NOVEL
		bookFull.referenceBook == REFERENCE_BOOK
		bookFull.biographyBook == BIOGRAPHY_BOOK
		bookFull.rolePlayingBook == ROLE_PLAYING_BOOK
		bookFull.EBook == E_BOOK
		bookFull.anthology == ANTHOLOGY
		bookFull.novelization == NOVELIZATION
		bookFull.audiobook == AUDIOBOOK
		bookFull.audiobookAbridged == AUDIOBOOK_ABRIDGED
		bookFull.audiobookPublishedYear == AUDIOBOOK_PUBLISHED_YEAR
		bookFull.audiobookPublishedMonth == AUDIOBOOK_PUBLISHED_MONTH
		bookFull.audiobookPublishedDay == AUDIOBOOK_PUBLISHED_DAY
		bookFull.audiobookRunTime == AUDIOBOOK_RUN_TIME
		bookFull.productionNumber == PRODUCTION_NUMBER
		bookFull.bookSeries.size() == book.bookSeries.size()
		bookFull.authors.size() == book.authors.size()
		bookFull.artists.size() == book.artists.size()
		bookFull.editors.size() == book.editors.size()
		bookFull.audiobookNarrators.size() == book.audiobookNarrators.size()
		bookFull.publishers.size() == book.publishers.size()
		bookFull.audiobookPublishers.size() == book.audiobookPublishers.size()
		bookFull.characters.size() == book.characters.size()
		bookFull.references.size() == book.references.size()
		bookFull.audiobookReferences.size() == book.audiobookReferences.size()
		bookFull.bookCollections.size() == book.bookCollections.size()
	}

	void "maps DB entity to full REST V2 entity"() {
		given:
		Book book = createBook()

		when:
		BookV2Full bookV2Full = bookFullRestMapper.mapV2Full(book)

		then:
		bookV2Full.uid == UID
		bookV2Full.title == TITLE
		bookV2Full.publishedYear == PUBLISHED_YEAR
		bookV2Full.publishedMonth == PUBLISHED_MONTH
		bookV2Full.publishedDay == PUBLISHED_DAY
		bookV2Full.numberOfPages == NUMBER_OF_PAGES
		bookV2Full.stardateFrom == STARDATE_FROM
		bookV2Full.stardateTo == STARDATE_TO
		bookV2Full.yearFrom == YEAR_FROM
		bookV2Full.yearTo == YEAR_TO
		bookV2Full.novel == NOVEL
		bookV2Full.referenceBook == REFERENCE_BOOK
		bookV2Full.biographyBook == BIOGRAPHY_BOOK
		bookV2Full.rolePlayingBook == ROLE_PLAYING_BOOK
		bookV2Full.EBook == E_BOOK
		bookV2Full.anthology == ANTHOLOGY
		bookV2Full.novelization == NOVELIZATION
		bookV2Full.unauthorizedPublication == UNAUTHORIZED_PUBLICATION
		bookV2Full.audiobook == AUDIOBOOK
		bookV2Full.audiobookAbridged == AUDIOBOOK_ABRIDGED
		bookV2Full.audiobookPublishedYear == AUDIOBOOK_PUBLISHED_YEAR
		bookV2Full.audiobookPublishedMonth == AUDIOBOOK_PUBLISHED_MONTH
		bookV2Full.audiobookPublishedDay == AUDIOBOOK_PUBLISHED_DAY
		bookV2Full.audiobookRunTime == AUDIOBOOK_RUN_TIME
		bookV2Full.productionNumber == PRODUCTION_NUMBER
		bookV2Full.bookSeries.size() == book.bookSeries.size()
		bookV2Full.authors.size() == book.authors.size()
		bookV2Full.artists.size() == book.artists.size()
		bookV2Full.editors.size() == book.editors.size()
		bookV2Full.audiobookNarrators.size() == book.audiobookNarrators.size()
		bookV2Full.publishers.size() == book.publishers.size()
		bookV2Full.audiobookPublishers.size() == book.audiobookPublishers.size()
		bookV2Full.characters.size() == book.characters.size()
		bookV2Full.references.size() == book.references.size()
		bookV2Full.audiobookReferences.size() == book.audiobookReferences.size()
		bookV2Full.bookCollections.size() == book.bookCollections.size()
	}

}
