package com.cezarykluczynski.stapi.etl.book.creation.processor

import com.cezarykluczynski.stapi.etl.template.book.dto.BookTemplate
import com.cezarykluczynski.stapi.model.book.entity.Book
import com.cezarykluczynski.stapi.model.book_series.entity.BookSeries
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.reference.entity.Reference
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.util.AbstractBookTest
import com.google.common.collect.Sets

class BookTemplateProcessorTest extends AbstractBookTest {

	private UidGenerator uidGeneratorMock

	private BookTemplateProcessor bookTemplateProcessor

	private final Page page = Mock()

	void setup() {
		uidGeneratorMock = Mock()
		bookTemplateProcessor = new BookTemplateProcessor(uidGeneratorMock)
	}

	void "converts BookTemplate to Book"() {
		given:
		BookSeries bookSeries1 = Mock()
		BookSeries bookSeries2 = Mock()
		Staff author1 = Mock()
		Staff author2 = Mock()
		Staff artist1 = Mock()
		Staff artist2 = Mock()
		Staff editor1 = Mock()
		Staff editor2 = Mock()
		Staff audiobookNarrator1 = Mock()
		Staff audiobookNarrator2 = Mock()
		Company publisher1 = Mock()
		Company publisher2 = Mock()
		Company audiobookPublisher1 = Mock()
		Company audiobookPublisher2 = Mock()
		Character character1 = Mock()
		Character character2 = Mock()
		Reference reference1 = Mock()
		Reference reference2 = Mock()
		Reference audiobookReference1 = Mock()
		Reference audiobookReference2 = Mock()

		BookTemplate bookTemplate = new BookTemplate(
				page: page,
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
				bookSeries: Sets.newHashSet(bookSeries1, bookSeries2),
				authors: Sets.newHashSet(author1, author2),
				artists: Sets.newHashSet(artist1, artist2),
				editors: Sets.newHashSet(editor1, editor2),
				audiobookNarrators: Sets.newHashSet(audiobookNarrator1, audiobookNarrator2),
				publishers: Sets.newHashSet(publisher1, publisher2),
				audiobookPublishers: Sets.newHashSet(audiobookPublisher1, audiobookPublisher2),
				characters: Sets.newHashSet(character1, character2),
				references: Sets.newHashSet(reference1, reference2),
				audiobookReferences: Sets.newHashSet(audiobookReference1, audiobookReference2))

		when:
		Book book = bookTemplateProcessor.process(bookTemplate)

		then:
		1 * uidGeneratorMock.generateFromPage(page, Book) >> UID
		0 * _
		book.uid == UID
		book.page == page
		book.title == TITLE
		book.publishedYear == PUBLISHED_YEAR
		book.publishedMonth == PUBLISHED_MONTH
		book.publishedDay == PUBLISHED_DAY
		book.numberOfPages == NUMBER_OF_PAGES
		book.stardateFrom == STARDATE_FROM
		book.stardateTo == STARDATE_TO
		book.yearFrom == YEAR_FROM
		book.yearTo == YEAR_TO
		book.novel == NOVEL
		book.referenceBook == REFERENCE_BOOK
		book.biographyBook == BIOGRAPHY_BOOK
		book.rolePlayingBook == ROLE_PLAYING_BOOK
		book.EBook == E_BOOK
		book.anthology == ANTHOLOGY
		book.novelization == NOVELIZATION
		book.audiobook == AUDIOBOOK
		book.audiobookPublishedYear == AUDIOBOOK_PUBLISHED_YEAR
		book.audiobookPublishedMonth == AUDIOBOOK_PUBLISHED_MONTH
		book.audiobookPublishedDay == AUDIOBOOK_PUBLISHED_DAY
		book.audiobookRunTime == AUDIOBOOK_RUN_TIME
		book.productionNumber == PRODUCTION_NUMBER
		book.bookSeries.contains bookSeries1
		book.bookSeries.contains bookSeries2
		book.authors.contains author1
		book.authors.contains author2
		book.artists.contains artist1
		book.artists.contains artist1
		book.editors.contains editor1
		book.editors.contains editor2
		book.audiobookNarrators.contains audiobookNarrator1
		book.audiobookNarrators.contains audiobookNarrator2
		book.publishers.contains publisher1
		book.publishers.contains publisher2
		book.audiobookPublishers.contains audiobookPublisher1
		book.audiobookPublishers.contains audiobookPublisher2
		book.characters.contains character1
		book.characters.contains character2
		book.references.contains reference1
		book.references.contains reference2
		book.audiobookReferences.contains audiobookReference1
		book.audiobookReferences.contains audiobookReference2
	}

}
