package com.cezarykluczynski.stapi.server.series.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.SeriesHeader
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class SeriesHeaderRestMapperTest extends AbstractSeriesMapperTest {

	private SeriesHeaderRestMapper seriesHeaderRestMapper

	void setup() {
		seriesHeaderRestMapper = Mappers.getMapper(SeriesHeaderRestMapper)
	}

	void "maps DB entity to REST header"() {
		given:
		Series series = new Series(
				title: TITLE,
				uid: UID)

		when:
		SeriesHeader seriesHeader = seriesHeaderRestMapper.map(Lists.newArrayList(series))[0]

		then:
		seriesHeader.title == TITLE
		seriesHeader.uid == UID
	}

}
