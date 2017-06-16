package com.cezarykluczynski.stapi.server.magazine_series.mapper

import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesHeader
import com.cezarykluczynski.stapi.model.magazine_series.entity.MagazineSeries
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class MagazineSeriesHeaderSoapMapperTest extends AbstractMagazineSeriesMapperTest {

	private MagazineSeriesHeaderSoapMapper magazineSeriesHeaderSoapMapper

	void setup() {
		magazineSeriesHeaderSoapMapper = Mappers.getMapper(MagazineSeriesHeaderSoapMapper)
	}

	void "maps DB entity to SOAP header"() {
		given:
		MagazineSeries magazineSeries = new MagazineSeries(
				uid: UID,
				title: TITLE)

		when:
		MagazineSeriesHeader magazineSeriesHeader = magazineSeriesHeaderSoapMapper.map(Lists.newArrayList(magazineSeries))[0]

		then:
		magazineSeriesHeader.uid == UID
		magazineSeriesHeader.title == TITLE
	}

}
