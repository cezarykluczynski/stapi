package com.cezarykluczynski.stapi.server.book_collection.mapper

import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionBase
import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.FloatRange
import com.cezarykluczynski.stapi.client.v1.soap.IntegerRange
import com.cezarykluczynski.stapi.model.book_collection.dto.BookCollectionRequestDTO
import com.cezarykluczynski.stapi.model.book_collection.entity.BookCollection
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class BookCollectionBaseSoapMapperTest extends AbstractBookCollectionMapperTest {

	private BookCollectionBaseSoapMapper bookCollectionBaseSoapMapper

	void setup() {
		bookCollectionBaseSoapMapper = Mappers.getMapper(BookCollectionBaseSoapMapper)
	}

	void "maps SOAP BookCollectionBaseRequest to BookCollectionRequestDTO"() {
		given:
		BookCollectionBaseRequest bookCollectionRequest = new BookCollectionBaseRequest(
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
				))

		when:
		BookCollectionRequestDTO bookCollectionRequestDTO = bookCollectionBaseSoapMapper.mapBase bookCollectionRequest

		then:
		bookCollectionRequestDTO.title == TITLE
		bookCollectionRequestDTO.stardateFrom == STARDATE_FROM
		bookCollectionRequestDTO.stardateTo == STARDATE_TO
		bookCollectionRequestDTO.numberOfPagesFrom == NUMBER_OF_PAGES_FROM
		bookCollectionRequestDTO.numberOfPagesTo == NUMBER_OF_PAGES_TO
		bookCollectionRequestDTO.yearFrom == YEAR_FROM
		bookCollectionRequestDTO.yearTo == YEAR_TO
	}

	void "maps DB entity to base SOAP entity"() {
		given:
		BookCollection bookCollection = createBookCollection()

		when:
		BookCollectionBase bookCollectionBase = bookCollectionBaseSoapMapper.mapBase(Lists.newArrayList(bookCollection))[0]

		then:
		bookCollectionBase.uid == UID
		bookCollectionBase.title == TITLE
		bookCollectionBase.publishedYear == PUBLISHED_YEAR
		bookCollectionBase.publishedMonth == PUBLISHED_MONTH
		bookCollectionBase.publishedDay == PUBLISHED_DAY
		bookCollectionBase.numberOfPages == NUMBER_OF_PAGES
		bookCollectionBase.stardateFrom == STARDATE_FROM
		bookCollectionBase.stardateTo == STARDATE_TO
		bookCollectionBase.yearFrom.toInteger() == YEAR_FROM
		bookCollectionBase.yearTo.toInteger() == YEAR_TO
	}

}
