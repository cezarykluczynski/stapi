package com.cezarykluczynski.stapi.server.book.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.BookBase
import com.cezarykluczynski.stapi.model.book.dto.BookRequestDTO
import com.cezarykluczynski.stapi.model.book.entity.Book
import com.cezarykluczynski.stapi.server.book.dto.BookRestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class BookBaseRestMapperTest extends AbstractBookMapperTest {

	private BookBaseRestMapper bookBaseRestMapper

	void setup() {
		bookBaseRestMapper = Mappers.getMapper(BookBaseRestMapper)
	}

	void "maps BookRestBeanParams to BookRequestDTO"() {
		given:
		BookRestBeanParams bookRestBeanParams = new BookRestBeanParams(
				uid: UID,
				title: TITLE,
				publishedYearFrom: PUBLISHED_YEAR_FROM,
				publishedYearTo: PUBLISHED_YEAR_TO,
				numberOfPagesFrom: NUMBER_OF_PAGES_FROM,
				numberOfPagesTo: NUMBER_OF_PAGES_TO,
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
				audiobookPublishedYearFrom: AUDIOBOOK_PUBLISHED_YEAR_FROM,
				audiobookPublishedYearTo: AUDIOBOOK_PUBLISHED_YEAR_TO,
				audiobookRunTimeFrom: AUDIOBOOK_RUN_TIME_FROM,
				audiobookRunTimeTo: AUDIOBOOK_RUN_TIME_TO)

		when:
		BookRequestDTO bookRequestDTO = bookBaseRestMapper.mapBase bookRestBeanParams

		then:
		bookRequestDTO.uid == UID
		bookRequestDTO.title == TITLE
		bookRequestDTO.stardateFrom == STARDATE_FROM
		bookRequestDTO.stardateTo == STARDATE_TO
		bookRequestDTO.numberOfPagesFrom == NUMBER_OF_PAGES_FROM
		bookRequestDTO.numberOfPagesTo == NUMBER_OF_PAGES_TO
		bookRequestDTO.yearFrom == YEAR_FROM
		bookRequestDTO.yearTo == YEAR_TO
		bookRequestDTO.novel == NOVEL
		bookRequestDTO.referenceBook == REFERENCE_BOOK
		bookRequestDTO.biographyBook == BIOGRAPHY_BOOK
		bookRequestDTO.rolePlayingBook == ROLE_PLAYING_BOOK
		bookRequestDTO.EBook == E_BOOK
		bookRequestDTO.anthology == ANTHOLOGY
		bookRequestDTO.novelization == NOVELIZATION
		bookRequestDTO.audiobook == AUDIOBOOK
		bookRequestDTO.audiobookAbridged == AUDIOBOOK_ABRIDGED
		bookRequestDTO.audiobookPublishedYearFrom == AUDIOBOOK_PUBLISHED_YEAR_FROM
		bookRequestDTO.audiobookPublishedYearTo == AUDIOBOOK_PUBLISHED_YEAR_TO
		bookRequestDTO.audiobookRunTimeFrom == AUDIOBOOK_RUN_TIME_FROM
		bookRequestDTO.audiobookRunTimeTo == AUDIOBOOK_RUN_TIME_TO
	}

	void "maps DB entity to base REST entity"() {
		given:
		Book book = createBook()

		when:
		BookBase bookBase = bookBaseRestMapper.mapBase(Lists.newArrayList(book))[0]

		then:
		bookBase.uid == UID
		bookBase.title == TITLE
		bookBase.publishedYear == PUBLISHED_YEAR
		bookBase.publishedMonth == PUBLISHED_MONTH
		bookBase.publishedDay == PUBLISHED_DAY
		bookBase.numberOfPages == NUMBER_OF_PAGES
		bookBase.stardateFrom == STARDATE_FROM
		bookBase.stardateTo == STARDATE_TO
		bookBase.yearFrom == YEAR_FROM
		bookBase.yearTo == YEAR_TO
		bookBase.novel == NOVEL
		bookBase.referenceBook == REFERENCE_BOOK
		bookBase.biographyBook == BIOGRAPHY_BOOK
		bookBase.rolePlayingBook == ROLE_PLAYING_BOOK
		bookBase.EBook == E_BOOK
		bookBase.anthology == ANTHOLOGY
		bookBase.novelization == NOVELIZATION
		bookBase.audiobook == AUDIOBOOK
		bookBase.audiobookAbridged == AUDIOBOOK_ABRIDGED
		bookBase.audiobookPublishedYear == AUDIOBOOK_PUBLISHED_YEAR
		bookBase.audiobookPublishedMonth == AUDIOBOOK_PUBLISHED_MONTH
		bookBase.audiobookPublishedDay == AUDIOBOOK_PUBLISHED_DAY
		bookBase.audiobookRunTime == AUDIOBOOK_RUN_TIME
		bookBase.productionNumber == PRODUCTION_NUMBER
	}

}
