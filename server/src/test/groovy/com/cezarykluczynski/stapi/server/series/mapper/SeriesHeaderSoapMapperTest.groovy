package com.cezarykluczynski.stapi.server.series.mapper

import com.cezarykluczynski.stapi.client.v1.soap.SeriesHeader
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class SeriesHeaderSoapMapperTest extends AbstractSeriesMapperTest {

	private SeriesHeaderSoapMapper seriesHeaderSoapMapper

	void setup() {
		seriesHeaderSoapMapper = Mappers.getMapper(SeriesHeaderSoapMapper)
	}

	void "maps DB entity to SOAP header"() {
		given:
		Series series = new Series(
				title: TITLE,
				uid: UID)

		when:
		SeriesHeader seriesHeader = seriesHeaderSoapMapper.map(Lists.newArrayList(series))[0]

		then:
		seriesHeader.title == TITLE
		seriesHeader.uid == UID
	}

}
