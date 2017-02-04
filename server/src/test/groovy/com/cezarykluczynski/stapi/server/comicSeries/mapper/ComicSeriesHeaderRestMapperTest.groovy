package com.cezarykluczynski.stapi.server.comicSeries.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicSeriesHeader
import com.cezarykluczynski.stapi.model.comicSeries.entity.ComicSeries
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class ComicSeriesHeaderRestMapperTest extends AbstractComicSeriesMapperTest {

	private ComicSeriesHeaderRestMapper comicSeriesHeaderRestMapper

	void setup() {
		comicSeriesHeaderRestMapper = Mappers.getMapper(ComicSeriesHeaderRestMapper)
	}

	void "maps DB entity to REST header"() {
		given:
		ComicSeries comicSeries = new ComicSeries(
				guid: GUID,
				title: TITLE)

		when:
		ComicSeriesHeader comicSeriesHeader = comicSeriesHeaderRestMapper.map(Lists.newArrayList(comicSeries))[0]

		then:
		comicSeriesHeader.guid == GUID
		comicSeriesHeader.title == TITLE
	}

}
