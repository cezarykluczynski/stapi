package com.cezarykluczynski.stapi.server.magazine_series.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineSeriesBase as MagazineSeriesBase
import com.cezarykluczynski.stapi.model.magazine_series.dto.MagazineSeriesRequestDTO
import com.cezarykluczynski.stapi.model.magazine_series.entity.MagazineSeries as MagazineSeries
import com.cezarykluczynski.stapi.server.magazine_series.dto.MagazineSeriesRestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class MagazineSeriesBaseRestMapperTest extends AbstractMagazineSeriesMapperTest {

	private MagazineSeriesBaseRestMapper magazineSeriesBaseRestMapper

	void setup() {
		magazineSeriesBaseRestMapper = Mappers.getMapper(MagazineSeriesBaseRestMapper)
	}

	void "maps MagazineSeriesRestBeanParams to MagazineSeriesRequestDTO"() {
		given:
		MagazineSeriesRestBeanParams magazineSeriesRestBeanParams = new MagazineSeriesRestBeanParams(
				uid: UID,
				title: TITLE,
				publishedYearFrom: PUBLISHED_YEAR_FROM,
				publishedYearTo: PUBLISHED_YEAR_TO,
				numberOfIssuesFrom: NUMBER_OF_ISSUES_FROM,
				numberOfIssuesTo: NUMBER_OF_ISSUES_TO)

		when:
		MagazineSeriesRequestDTO magazineSeriesRequestDTO = magazineSeriesBaseRestMapper.mapBase magazineSeriesRestBeanParams

		then:
		magazineSeriesRequestDTO.uid == UID
		magazineSeriesRequestDTO.title == TITLE
		magazineSeriesRequestDTO.publishedYearFrom == PUBLISHED_YEAR_FROM
		magazineSeriesRequestDTO.publishedYearTo == PUBLISHED_YEAR_TO
		magazineSeriesRequestDTO.numberOfIssuesFrom == NUMBER_OF_ISSUES_FROM
		magazineSeriesRequestDTO.numberOfIssuesTo == NUMBER_OF_ISSUES_TO
	}

	void "maps DB entity to base REST entity"() {
		given:
		MagazineSeries magazineSeries = createMagazineSeries()

		when:
		MagazineSeriesBase restMagazineSeries = magazineSeriesBaseRestMapper.mapBase(Lists.newArrayList(magazineSeries))[0]

		then:
		restMagazineSeries.uid == UID
		restMagazineSeries.title == TITLE
		restMagazineSeries.publishedYearFrom == PUBLISHED_YEAR_FROM
		restMagazineSeries.publishedMonthFrom == PUBLISHED_MONTH_FROM
		restMagazineSeries.publishedYearTo == PUBLISHED_YEAR_TO
		restMagazineSeries.publishedMonthTo == PUBLISHED_MONTH_TO
		restMagazineSeries.numberOfIssues == NUMBER_OF_ISSUES
	}

}
