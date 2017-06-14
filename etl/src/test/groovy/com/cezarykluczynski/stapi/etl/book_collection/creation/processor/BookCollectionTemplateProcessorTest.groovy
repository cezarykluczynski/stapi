package com.cezarykluczynski.stapi.etl.book_collection.creation.processor

import com.cezarykluczynski.stapi.etl.template.book.dto.BookCollectionTemplate
import com.cezarykluczynski.stapi.model.book.entity.Book
import com.cezarykluczynski.stapi.model.book_collection.entity.BookCollection
import com.cezarykluczynski.stapi.model.book_series.entity.BookSeries
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.reference.entity.Reference
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.util.AbstractBookCollectionTest
import com.google.common.collect.Sets

class BookCollectionTemplateProcessorTest extends AbstractBookCollectionTest {

	private UidGenerator uidGeneratorMock

	private BookCollectionTemplateProcessor bookCollectionTemplateProcessor

	private final Page page = Mock()

	void setup() {
		uidGeneratorMock = Mock()
		bookCollectionTemplateProcessor = new BookCollectionTemplateProcessor(uidGeneratorMock)
	}

	void "converts BookCollectionTemplate to BookCollection"() {
		given:
		BookSeries bookSeries1 = Mock()
		BookSeries bookSeries2 = Mock()
		Staff author1 = Mock()
		Staff author2 = Mock()
		Staff artist1 = Mock()
		Staff artist2 = Mock()
		Staff editor1 = Mock()
		Staff editor2 = Mock()
		Company publisher1 = Mock()
		Company publisher2 = Mock()
		Character character1 = Mock()
		Character character2 = Mock()
		Reference reference1 = Mock()
		Reference reference2 = Mock()
		Book book1 = Mock()
		Book book2 = Mock()

		BookCollectionTemplate bookCollectionTemplate = new BookCollectionTemplate(
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
				bookSeries: Sets.newHashSet(bookSeries1, bookSeries2),
				authors: Sets.newHashSet(author1, author2),
				artists: Sets.newHashSet(artist1, artist2),
				editors: Sets.newHashSet(editor1, editor2),
				publishers: Sets.newHashSet(publisher1, publisher2),
				characters: Sets.newHashSet(character1, character2),
				references: Sets.newHashSet(reference1, reference2),
				books: Sets.newHashSet(book1, book2))

		when:
		BookCollection bookCollection = bookCollectionTemplateProcessor.process(bookCollectionTemplate)

		then:
		1 * uidGeneratorMock.generateFromPage(page, BookCollection) >> UID
		0 * _
		bookCollection.uid == UID
		bookCollection.page == page
		bookCollection.title == TITLE
		bookCollection.publishedYear == PUBLISHED_YEAR
		bookCollection.publishedMonth == PUBLISHED_MONTH
		bookCollection.publishedDay == PUBLISHED_DAY
		bookCollection.numberOfPages == NUMBER_OF_PAGES
		bookCollection.yearFrom == YEAR_FROM
		bookCollection.yearTo == YEAR_TO
		bookCollection.bookSeries.contains bookSeries1
		bookCollection.bookSeries.contains bookSeries2
		bookCollection.authors.contains author1
		bookCollection.authors.contains author2
		bookCollection.artists.contains artist1
		bookCollection.artists.contains artist1
		bookCollection.editors.contains editor1
		bookCollection.editors.contains editor2
		bookCollection.publishers.contains publisher1
		bookCollection.publishers.contains publisher2
		bookCollection.characters.contains character1
		bookCollection.characters.contains character2
		bookCollection.references.contains reference1
		bookCollection.references.contains reference2
		bookCollection.books.contains book1
		bookCollection.books.contains book2
	}

}
