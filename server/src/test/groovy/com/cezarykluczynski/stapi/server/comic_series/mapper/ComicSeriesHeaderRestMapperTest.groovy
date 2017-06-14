package com.cezarykluczynski.stapi.server.comic_series.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicSeriesHeader
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries
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
				uid: UID,
				title: TITLE)

		when:
		ComicSeriesHeader comicSeriesHeader = comicSeriesHeaderRestMapper.map(Lists.newArrayList(comicSeries))[0]

		then:
		comicSeriesHeader.uid == UID
		comicSeriesHeader.title == TITLE
	}

}
