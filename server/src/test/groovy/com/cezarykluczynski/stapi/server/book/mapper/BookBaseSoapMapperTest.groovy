package com.cezarykluczynski.stapi.server.book.mapper

import com.cezarykluczynski.stapi.client.v1.soap.BookBase
import com.cezarykluczynski.stapi.client.v1.soap.BookBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.FloatRange
import com.cezarykluczynski.stapi.client.v1.soap.IntegerRange
import com.cezarykluczynski.stapi.model.book.dto.BookRequestDTO
import com.cezarykluczynski.stapi.model.book.entity.Book
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class BookBaseSoapMapperTest extends AbstractBookMapperTest {

	private BookBaseSoapMapper bookBaseSoapMapper

	void setup() {
		bookBaseSoapMapper = Mappers.getMapper(BookBaseSoapMapper)
	}

	void "maps SOAP BookRequest to BookRequestDTO"() {
		given:
		BookBaseRequest bookBaseRequest = new BookBaseRequest(
				title: TITLE,
				publishedYear: new IntegerRange(
						from: PUBLISHED_YEAR_FROM,
						to: PUBLISHED_YEAR_TO
				),
				numberOfPages: new IntegerRange(
						from: NUMBER_OF_PAGES_FROM,
						to: NUMBER_OF_PAGES_TO
				),
				stardate: new FloatRange(
						from: STARDATE_FROM,
						to: STARDATE_TO,
				),
				year: new IntegerRange(
						from: YEAR_FROM,
						to: YEAR_TO,
				),
				novel: NOVEL,
				referenceBook: REFERENCE_BOOK,
				biographyBook: BIOGRAPHY_BOOK,
				rolePlayingBook: ROLE_PLAYING_BOOK,
				eBook: E_BOOK,
				anthology: ANTHOLOGY,
				novelization: NOVELIZATION,
				audiobook: AUDIOBOOK,
				audiobookAbridged: AUDIOBOOK_ABRIDGED,
				audiobookPublishedYear: new IntegerRange(
						from: AUDIOBOOK_PUBLISHED_YEAR_FROM,
						to: AUDIOBOOK_PUBLISHED_YEAR_TO
				),
				audiobookRunTime: new IntegerRange(
						from: AUDIOBOOK_RUN_TIME_FROM,
						to: AUDIOBOOK_RUN_TIME_TO
				))

		when:
		BookRequestDTO bookRequestDTO = bookBaseSoapMapper.mapBase bookBaseRequest

		then:
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

	void "maps DB entity to base SOAP entity"() {
		given:
		Book book = createBook()

		when:
		BookBase bookBase = bookBaseSoapMapper.mapBase(Lists.newArrayList(book))[0]

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
