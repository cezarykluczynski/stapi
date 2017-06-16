package com.cezarykluczynski.stapi.server.magazine_series.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineSeriesHeader
import com.cezarykluczynski.stapi.model.magazine_series.entity.MagazineSeries
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class MagazineSeriesHeaderRestMapperTest extends AbstractMagazineSeriesMapperTest {

	private MagazineSeriesHeaderRestMapper magazineSeriesHeaderRestMapper

	void setup() {
		magazineSeriesHeaderRestMapper = Mappers.getMapper(MagazineSeriesHeaderRestMapper)
	}

	void "maps DB entity to REST header"() {
		given:
		MagazineSeries magazineSeries = new MagazineSeries(
				uid: UID,
				title: TITLE)

		when:
		MagazineSeriesHeader magazineSeriesHeader = magazineSeriesHeaderRestMapper.map(Lists.newArrayList(magazineSeries))[0]

		then:
		magazineSeriesHeader.uid == UID
		magazineSeriesHeader.title == TITLE
	}

}
