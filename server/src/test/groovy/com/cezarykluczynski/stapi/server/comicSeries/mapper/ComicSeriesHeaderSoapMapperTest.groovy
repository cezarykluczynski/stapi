package com.cezarykluczynski.stapi.server.comicSeries.mapper

import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesHeader
import com.cezarykluczynski.stapi.model.comicSeries.entity.ComicSeries
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
				guid: GUID,
				title: TITLE)

		when:
		ComicSeriesHeader comicSeriesHeader = comicSeriesHeaderSoapMapper.map(Lists.newArrayList(comicSeries))[0]

		then:
		comicSeriesHeader.guid == GUID
		comicSeriesHeader.title == TITLE
	}

}
