package com.cezarykluczynski.stapi.etl.template.book.processor.collection

import com.cezarykluczynski.stapi.etl.template.book.dto.BookCollectionTemplate
import com.cezarykluczynski.stapi.etl.template.book.dto.BookTemplate
import com.cezarykluczynski.stapi.model.book_series.entity.BookSeries
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.reference.entity.Reference
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.util.AbstractBookCollectionTest
import com.google.common.collect.Sets

class BookTemplateToBookCollectionTemplateProcessorTest extends AbstractBookCollectionTest {

	private final Page page = Mock()

	private BookTemplateToBookCollectionTemplateProcessor bookTemplateToBookCollectionTemplateProcessor

	void setup() {
		bookTemplateToBookCollectionTemplateProcessor = new BookTemplateToBookCollectionTemplateProcessor()
	}

	void "converts BooksTemplate to BookCollectionTemplate"() {
		given:
		BookSeries bookSeries1 = Mock()
		BookSeries bookSeries2 = Mock()
		Staff writer1 = Mock()
		Staff writer2 = Mock()
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

		BookTemplate comicsTemplate = new BookTemplate(
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
				authors: Sets.newHashSet(writer1, writer2),
				artists: Sets.newHashSet(artist1, artist2),
				editors: Sets.newHashSet(editor1, editor2),
				publishers: Sets.newHashSet(publisher1, publisher2),
				characters: Sets.newHashSet(character1, character2),
				references: Sets.newHashSet(reference1, reference2))

		when:
		BookCollectionTemplate comicCollectionTemplate = bookTemplateToBookCollectionTemplateProcessor.process(comicsTemplate)

		then:
		comicCollectionTemplate.page == page
		comicCollectionTemplate.title == TITLE
		comicCollectionTemplate.publishedYear == PUBLISHED_YEAR
		comicCollectionTemplate.publishedMonth == PUBLISHED_MONTH
		comicCollectionTemplate.publishedDay == PUBLISHED_DAY
		comicCollectionTemplate.numberOfPages == NUMBER_OF_PAGES
		comicCollectionTemplate.stardateFrom == STARDATE_FROM
		comicCollectionTemplate.stardateTo == STARDATE_TO
		comicCollectionTemplate.yearFrom == YEAR_FROM
		comicCollectionTemplate.yearTo == YEAR_TO
		comicCollectionTemplate.bookSeries.contains bookSeries1
		comicCollectionTemplate.bookSeries.contains bookSeries2
		comicCollectionTemplate.authors.contains writer1
		comicCollectionTemplate.authors.contains writer2
		comicCollectionTemplate.artists.contains artist1
		comicCollectionTemplate.artists.contains artist1
		comicCollectionTemplate.editors.contains editor1
		comicCollectionTemplate.editors.contains editor2
		comicCollectionTemplate.publishers.contains publisher1
		comicCollectionTemplate.publishers.contains publisher2
		comicCollectionTemplate.characters.contains character1
		comicCollectionTemplate.characters.contains character2
		comicCollectionTemplate.references.contains reference1
		comicCollectionTemplate.references.contains reference2
	}

}
