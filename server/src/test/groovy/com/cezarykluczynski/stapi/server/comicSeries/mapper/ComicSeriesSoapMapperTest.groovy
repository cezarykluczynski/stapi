package com.cezarykluczynski.stapi.server.comicSeries.mapper

import com.cezarykluczynski.stapi.client.v1.soap.ComicSeries as SOAPComicSeries
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesRequest
import com.cezarykluczynski.stapi.client.v1.soap.FloatRange
import com.cezarykluczynski.stapi.client.v1.soap.IntegerRange
import com.cezarykluczynski.stapi.model.comicSeries.dto.ComicSeriesRequestDTO
import com.cezarykluczynski.stapi.model.comicSeries.entity.ComicSeries as DBComicSeries
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class ComicSeriesSoapMapperTest extends AbstractComicSeriesMapperTest {

	private ComicSeriesSoapMapper comicSeriesSoapMapper

	void setup() {
		comicSeriesSoapMapper = Mappers.getMapper(ComicSeriesSoapMapper)
	}

	void "maps SOAP ComicSeriesRequest to ComicSeriesRequestDTO"() {
		given:
		ComicSeriesRequest comicSeriesRequest = new ComicSeriesRequest(
				guid: GUID,
				title: TITLE,
				publishedYear: new IntegerRange(
						from: PUBLISHED_YEAR_FROM,
						to: PUBLISHED_YEAR_TO
				),
				numberOfIssues: new IntegerRange(
						from: NUMBER_OF_ISSUES_FROM,
						to: NUMBER_OF_ISSUES_TO
				),
				stardate: new FloatRange(
						from: STARDATE_FROM,
						to: STARDATE_TO,
				),
				year: new IntegerRange(
						from: YEAR_FROM,
						to: YEAR_TO,
				),
				miniseries: MINISERIES,
				photonovelSeries: PHOTONOVEL_SERIES)

		when:
		ComicSeriesRequestDTO comicSeriesRequestDTO = comicSeriesSoapMapper.map comicSeriesRequest

		then:
		comicSeriesRequestDTO.guid == GUID
		comicSeriesRequestDTO.title == TITLE
		comicSeriesRequestDTO.publishedYearFrom == PUBLISHED_YEAR_FROM
		comicSeriesRequestDTO.publishedYearTo == PUBLISHED_YEAR_TO
		comicSeriesRequestDTO.numberOfIssuesFrom == NUMBER_OF_ISSUES_FROM
		comicSeriesRequestDTO.numberOfIssuesTo == NUMBER_OF_ISSUES_TO
		comicSeriesRequestDTO.stardateFrom == STARDATE_FROM
		comicSeriesRequestDTO.stardateTo == STARDATE_TO
		comicSeriesRequestDTO.yearFrom == YEAR_FROM
		comicSeriesRequestDTO.yearTo == YEAR_TO
		comicSeriesRequestDTO.miniseries == MINISERIES
		comicSeriesRequestDTO.photonovelSeries == PHOTONOVEL_SERIES
	}

	void "maps DB entity to SOAP entity"() {
		given:
		DBComicSeries dBComicSeries = createComicSeries()

		when:
		SOAPComicSeries soapComicSeries = comicSeriesSoapMapper.map(Lists.newArrayList(dBComicSeries))[0]

		then:
		soapComicSeries.guid == GUID
		soapComicSeries.title == TITLE
		soapComicSeries.publishedYearFrom == PUBLISHED_YEAR_FROM
		soapComicSeries.publishedMonthFrom == PUBLISHED_MONTH_FROM
		soapComicSeries.publishedDayFrom == PUBLISHED_DAY_FROM
		soapComicSeries.publishedYearTo == PUBLISHED_YEAR_TO
		soapComicSeries.publishedMonthTo == PUBLISHED_MONTH_TO
		soapComicSeries.publishedDayTo == PUBLISHED_DAY_TO
		soapComicSeries.numberOfIssues == NUMBER_OF_ISSUES
		soapComicSeries.stardateFrom == STARDATE_FROM
		soapComicSeries.stardateTo == STARDATE_TO
		soapComicSeries.yearFrom.toInteger() == YEAR_FROM
		soapComicSeries.yearTo.toInteger() == YEAR_TO
		soapComicSeries.miniseries == MINISERIES
		soapComicSeries.photonovelSeries == PHOTONOVEL_SERIES
		soapComicSeries.parentSeriesHeaders.size() == dBComicSeries.parentSeries.size()
		soapComicSeries.childSeriesHeaders.size() == dBComicSeries.childSeries.size()
	}

}
