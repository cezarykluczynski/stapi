package com.cezarykluczynski.stapi.server.comic_series.mapper

import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesHeader
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class ComicSeriesHeaderSoapMapperTest extends AbstractComicSeriesMapperTest {

	private ComicSeriesHeaderSoapMapper comicSeriesHeaderSoapMapper

	void setup() {
		comicSeriesHeaderSoapMapper = Mappers.getMapper(ComicSeriesHeaderSoapMapper)
	}

	void "maps DB entity to SOAP header"() {
		given:
		ComicSeries comicSeries = new ComicSeries(
				uid: UID,
				title: TITLE)

		when:
		ComicSeriesHeader comicSeriesHeader = comicSeriesHeaderSoapMapper.map(Lists.newArrayList(comicSeries))[0]

		then:
		comicSeriesHeader.uid == UID
		comicSeriesHeader.title == TITLE
	}

}
