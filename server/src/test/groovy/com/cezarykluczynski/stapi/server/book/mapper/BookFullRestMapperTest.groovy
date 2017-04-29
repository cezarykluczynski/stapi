package com.cezarykluczynski.stapi.server.book.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.BookFull
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

}
