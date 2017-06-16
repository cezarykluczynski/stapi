package com.cezarykluczynski.stapi.server.magazine_series.mapper

import com.cezarykluczynski.stapi.client.v1.soap.IntegerRange
import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesBase
import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesBaseRequest
import com.cezarykluczynski.stapi.model.magazine_series.dto.MagazineSeriesRequestDTO
import com.cezarykluczynski.stapi.model.magazine_series.entity.MagazineSeries as MagazineSeries
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class MagazineSeriesBaseSoapMapperTest extends AbstractMagazineSeriesMapperTest {

	private MagazineSeriesBaseSoapMapper magazineSeriesBaseSoapMapper

	void setup() {
		magazineSeriesBaseSoapMapper = Mappers.getMapper(MagazineSeriesBaseSoapMapper)
	}

	void "maps SOAP MagazineSeriesRequest to MagazineSeriesRequestDTO"() {
		given:
		MagazineSeriesBaseRequest magazineSeriesBaseRequest = new MagazineSeriesBaseRequest(
				title: TITLE,
				publishedYear: new IntegerRange(
						from: PUBLISHED_YEAR_FROM,
						to: PUBLISHED_YEAR_TO
				),
				numberOfIssues: new IntegerRange(
						from: NUMBER_OF_ISSUES_FROM,
						to: NUMBER_OF_ISSUES_TO
				))

		when:
		MagazineSeriesRequestDTO magazineSeriesRequestDTO = magazineSeriesBaseSoapMapper.mapBase magazineSeriesBaseRequest

		then:
		magazineSeriesRequestDTO.title == TITLE
		magazineSeriesRequestDTO.publishedYearFrom == PUBLISHED_YEAR_FROM
		magazineSeriesRequestDTO.publishedYearTo == PUBLISHED_YEAR_TO
		magazineSeriesRequestDTO.numberOfIssuesFrom == NUMBER_OF_ISSUES_FROM
		magazineSeriesRequestDTO.numberOfIssuesTo == NUMBER_OF_ISSUES_TO
	}

	void "maps DB entity to base SOAP entity"() {
		given:
		MagazineSeries magazineSeries = createMagazineSeries()

		when:
		MagazineSeriesBase magazineSeriesBase = magazineSeriesBaseSoapMapper.mapBase(Lists.newArrayList(magazineSeries))[0]

		then:
		magazineSeriesBase.uid == UID
		magazineSeriesBase.title == TITLE
		magazineSeriesBase.publishedYearFrom == PUBLISHED_YEAR_FROM
		magazineSeriesBase.publishedMonthFrom == PUBLISHED_MONTH_FROM
		magazineSeriesBase.publishedYearTo == PUBLISHED_YEAR_TO
		magazineSeriesBase.publishedMonthTo == PUBLISHED_MONTH_TO
		magazineSeriesBase.numberOfIssues == NUMBER_OF_ISSUES
	}

}
