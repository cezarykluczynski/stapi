package com.cezarykluczynski.stapi.server.book_collection.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.BookCollectionBase
import com.cezarykluczynski.stapi.model.book_collection.dto.BookCollectionRequestDTO
import com.cezarykluczynski.stapi.model.book_collection.entity.BookCollection
import com.cezarykluczynski.stapi.server.book_collection.dto.BookCollectionRestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class BookCollectionBaseRestMapperTest extends AbstractBookCollectionMapperTest {

	private BookCollectionBaseRestMapper bookCollectionRestMapper

	void setup() {
		bookCollectionRestMapper = Mappers.getMapper(BookCollectionBaseRestMapper)
	}

	void "maps BookCollectionRestBeanParams to BookCollectionRequestDTO"() {
		given:
		BookCollectionRestBeanParams bookCollectionRestBeanParams = new BookCollectionRestBeanParams(
				uid: UID,
				title: TITLE,
				publishedYearFrom: PUBLISHED_YEAR_FROM,
				publishedYearTo: PUBLISHED_YEAR_TO,
				numberOfPagesFrom: NUMBER_OF_PAGES_FROM,
				numberOfPagesTo: NUMBER_OF_PAGES_TO,
				stardateFrom: STARDATE_FROM,
				stardateTo: STARDATE_TO,
				yearFrom: YEAR_FROM,
				yearTo: YEAR_TO)

		when:
		BookCollectionRequestDTO bookCollectionRequestDTO = bookCollectionRestMapper.mapBase bookCollectionRestBeanParams

		then:
		bookCollectionRequestDTO.uid == UID
		bookCollectionRequestDTO.title == TITLE
		bookCollectionRequestDTO.stardateFrom == STARDATE_FROM
		bookCollectionRequestDTO.stardateTo == STARDATE_TO
		bookCollectionRequestDTO.numberOfPagesFrom == NUMBER_OF_PAGES_FROM
		bookCollectionRequestDTO.numberOfPagesTo == NUMBER_OF_PAGES_TO
		bookCollectionRequestDTO.yearFrom == YEAR_FROM
		bookCollectionRequestDTO.yearTo == YEAR_TO
	}

	void "maps DB entity to base REST entity"() {
		given:
		BookCollection bookCollection = createBookCollection()

		when:
		BookCollectionBase bookCollectionBase = bookCollectionRestMapper.mapBase(Lists.newArrayList(bookCollection))[0]

		then:
		bookCollectionBase.uid == UID
		bookCollectionBase.title == TITLE
		bookCollectionBase.publishedYear == PUBLISHED_YEAR
		bookCollectionBase.publishedMonth == PUBLISHED_MONTH
		bookCollectionBase.publishedDay == PUBLISHED_DAY
		bookCollectionBase.numberOfPages == NUMBER_OF_PAGES
		bookCollectionBase.stardateFrom == STARDATE_FROM
		bookCollectionBase.stardateTo == STARDATE_TO
		bookCollectionBase.yearFrom == YEAR_FROM
		bookCollectionBase.yearTo == YEAR_TO
	}

}
