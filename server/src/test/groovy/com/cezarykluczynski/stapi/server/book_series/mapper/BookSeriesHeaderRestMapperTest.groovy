package com.cezarykluczynski.stapi.server.book_series.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.BookSeriesHeader
import com.cezarykluczynski.stapi.model.book_series.entity.BookSeries
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class BookSeriesHeaderRestMapperTest extends AbstractBookSeriesMapperTest {

	private BookSeriesHeaderRestMapper bookSeriesHeaderRestMapper

	void setup() {
		bookSeriesHeaderRestMapper = Mappers.getMapper(BookSeriesHeaderRestMapper)
	}

	void "maps DB entity to REST header"() {
		given:
		BookSeries bookSeries = new BookSeries(
				uid: UID,
				title: TITLE)

		when:
		BookSeriesHeader bookSeriesHeader = bookSeriesHeaderRestMapper.map(Lists.newArrayList(bookSeries))[0]

		then:
		bookSeriesHeader.uid == UID
		bookSeriesHeader.title == TITLE
	}

}
