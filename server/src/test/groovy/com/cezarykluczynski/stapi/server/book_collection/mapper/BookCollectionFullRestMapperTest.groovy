package com.cezarykluczynski.stapi.server.book_collection.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.BookCollectionFull
import com.cezarykluczynski.stapi.model.book_collection.entity.BookCollection
import org.mapstruct.factory.Mappers

class BookCollectionFullRestMapperTest extends AbstractBookCollectionMapperTest {

	private BookCollectionFullRestMapper bookCollectionFullRestMapper

	void setup() {
		bookCollectionFullRestMapper = Mappers.getMapper(BookCollectionFullRestMapper)
	}

	void "maps DB entity to base REST entity"() {
		given:
		BookCollection bookCollection = createBookCollection()

		when:
		BookCollectionFull bookCollectionFull = bookCollectionFullRestMapper.mapFull(bookCollection)

		then:
		bookCollectionFull.uid == UID
		bookCollectionFull.title == TITLE
		bookCollectionFull.publishedYear == PUBLISHED_YEAR
		bookCollectionFull.publishedMonth == PUBLISHED_MONTH
		bookCollectionFull.publishedDay == PUBLISHED_DAY
		bookCollectionFull.numberOfPages == NUMBER_OF_PAGES
		bookCollectionFull.stardateFrom == STARDATE_FROM
		bookCollectionFull.stardateTo == STARDATE_TO
		bookCollectionFull.yearFrom == YEAR_FROM
		bookCollectionFull.yearTo == YEAR_TO
		bookCollectionFull.bookSeries.size() == bookCollection.bookSeries.size()
		bookCollectionFull.authors.size() == bookCollection.authors.size()
		bookCollectionFull.artists.size() == bookCollection.artists.size()
		bookCollectionFull.editors.size() == bookCollection.editors.size()
		bookCollectionFull.publishers.size() == bookCollection.publishers.size()
		bookCollectionFull.characters.size() == bookCollection.characters.size()
		bookCollectionFull.references.size() == bookCollection.references.size()
		bookCollectionFull.books.size() == bookCollection.books.size()
	}

}
