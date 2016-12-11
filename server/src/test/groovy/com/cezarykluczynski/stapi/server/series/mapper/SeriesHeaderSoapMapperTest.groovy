package com.cezarykluczynski.stapi.server.series.mapper

import com.cezarykluczynski.stapi.client.v1.soap.SeriesHeader
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class SeriesHeaderSoapMapperTest extends AbstractSeriesMapperTest {

	private SeriesHeaderSoapMapper seriesHeaderSoapMapper

	def setup() {
		seriesHeaderSoapMapper = Mappers.getMapper(SeriesHeaderSoapMapper)
	}

	def "maps DB entity to SOAP header"() {
		given:
		Series series = new Series(
				title: TITLE,
				guid: GUID)

		when:
		SeriesHeader seriesHeader = seriesHeaderSoapMapper.map(Lists.newArrayList(series))[0]

		then:
		seriesHeader.title == TITLE
		seriesHeader.guid == GUID
	}

}
