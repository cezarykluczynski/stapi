package com.cezarykluczynski.stapi.server.magazine_series.mapper

import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesFull
import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesFullRequest
import com.cezarykluczynski.stapi.model.magazine_series.dto.MagazineSeriesRequestDTO
import com.cezarykluczynski.stapi.model.magazine_series.entity.MagazineSeries
import org.mapstruct.factory.Mappers

class MagazineSeriesFullSoapMapperTest extends AbstractMagazineSeriesMapperTest {

	private MagazineSeriesFullSoapMapper magazineSeriesFullSoapMapper

	void setup() {
		magazineSeriesFullSoapMapper = Mappers.getMapper(MagazineSeriesFullSoapMapper)
	}

	void "maps SOAP MagazineSeriesFullRequest to MagazineSeriesBaseRequestDTO"() {
		given:
		MagazineSeriesFullRequest magazineSeriesFullRequest = new MagazineSeriesFullRequest(uid: UID)

		when:
		MagazineSeriesRequestDTO magazineSeriesRequestDTO = magazineSeriesFullSoapMapper.mapFull magazineSeriesFullRequest

		then:
		magazineSeriesRequestDTO.uid == UID
	}

	void "maps DB entity to base SOAP entity"() {
		given:
		MagazineSeries magazineSeries = createMagazineSeries()

		when:
		MagazineSeriesFull magazineSeriesFull = magazineSeriesFullSoapMapper.mapFull(magazineSeries)

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
