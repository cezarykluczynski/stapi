package com.cezarykluczynski.stapi.server.magazine_series.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineSeriesFull
import com.cezarykluczynski.stapi.model.magazine_series.entity.MagazineSeries
import org.mapstruct.factory.Mappers

class MagazineSeriesFullRestMapperTest extends AbstractMagazineSeriesMapperTest {

	private MagazineSeriesFullRestMapper magazineSeriesFullRestMapper

	void setup() {
		magazineSeriesFullRestMapper = Mappers.getMapper(MagazineSeriesFullRestMapper)
	}

	void "maps DB entity to full REST entity"() {
		given:
		MagazineSeries magazineSeries = createMagazineSeries()

		when:
		MagazineSeriesFull magazineSeriesFull = magazineSeriesFullRestMapper.mapFull(magazineSeries)

		then:
		magazineSeriesFull.uid == UID
		magazineSeriesFull.title == TITLE
		magazineSeriesFull.publishedYearFrom == PUBLISHED_YEAR_FROM
		magazineSeriesFull.publishedMonthFrom == PUBLISHED_MONTH_FROM
		magazineSeriesFull.publishedYearTo == PUBLISHED_YEAR_TO
		magazineSeriesFull.publishedMonthTo == PUBLISHED_MONTH_TO
		magazineSeriesFull.numberOfIssues == NUMBER_OF_ISSUES
		magazineSeriesFull.publishers.size() == magazineSeries.publishers.size()
		magazineSeriesFull.editors.size() == magazineSeries.editors.size()
		magazineSeriesFull.magazines.size() == magazineSeries.magazines.size()
	}

}
