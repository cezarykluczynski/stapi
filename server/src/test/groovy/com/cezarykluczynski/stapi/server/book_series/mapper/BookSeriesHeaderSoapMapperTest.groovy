package com.cezarykluczynski.stapi.server.book_series.mapper

import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesHeader
import com.cezarykluczynski.stapi.model.book_series.entity.BookSeries
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class BookSeriesHeaderSoapMapperTest extends AbstractBookSeriesMapperTest {

	private BookSeriesHeaderSoapMapper bookSeriesHeaderSoapMapper

	void setup() {
		bookSeriesHeaderSoapMapper = Mappers.getMapper(BookSeriesHeaderSoapMapper)
	}

	void "maps DB entity to SOAP header"() {
		given:
		BookSeries bookSeries = new BookSeries(
				uid: UID,
				title: TITLE)

		when:
		BookSeriesHeader bookSeriesHeader = bookSeriesHeaderSoapMapper.map(Lists.newArrayList(bookSeries))[0]

		then:
		bookSeriesHeader.uid == UID
		bookSeriesHeader.title == TITLE
	}

}
