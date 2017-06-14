package com.cezarykluczynski.stapi.server.book_collection.mapper

import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionFull
import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionFullRequest
import com.cezarykluczynski.stapi.model.book_collection.dto.BookCollectionRequestDTO
import com.cezarykluczynski.stapi.model.book_collection.entity.BookCollection
import org.mapstruct.factory.Mappers

class BookCollectionFullSoapMapperTest extends AbstractBookCollectionMapperTest {

	private BookCollectionFullSoapMapper bookCollectionFullSoapMapper

	void setup() {
		bookCollectionFullSoapMapper = Mappers.getMapper(BookCollectionFullSoapMapper)
	}

	void "maps SOAP BookCollectionFullRequest to BookCollectionBaseRequestDTO"() {
		given:
		BookCollectionFullRequest bookCollectionFullRequest = new BookCollectionFullRequest(uid: UID)

		when:
		BookCollectionRequestDTO bookCollectionRequestDTO = bookCollectionFullSoapMapper.mapFull bookCollectionFullRequest

		then:
		bookCollectionRequestDTO.uid == UID
	}

	void "maps DB entity to full SOAP entity"() {
		given:
		BookCollection bookCollection = createBookCollection()

		when:
		BookCollectionFull bookCollectionFull = bookCollectionFullSoapMapper.mapFull(bookCollection)

		then:
		bookCollectionFull.uid == UID
		bookCollectionFull.title == TITLE
		bookCollectionFull.publishedYear == PUBLISHED_YEAR
		bookCollectionFull.publishedMonth == PUBLISHED_MONTH
		bookCollectionFull.publishedDay == PUBLISHED_DAY
		bookCollectionFull.numberOfPages == NUMBER_OF_PAGES
		bookCollectionFull.stardateFrom == STARDATE_FROM
		bookCollectionFull.stardateTo == STARDATE_TO
		bookCollectionFull.yearFrom.toInteger() == YEAR_FROM
		bookCollectionFull.yearTo.toInteger() == YEAR_TO
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
