package com.cezarykluczynski.stapi.server.comic_series.mapper

import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesBase
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.FloatRange
import com.cezarykluczynski.stapi.client.v1.soap.IntegerRange
import com.cezarykluczynski.stapi.model.comic_series.dto.ComicSeriesRequestDTO
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries as ComicSeries
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class ComicSeriesBaseSoapMapperTest extends AbstractComicSeriesMapperTest {

	private ComicSeriesBaseSoapMapper comicSeriesBaseSoapMapper

	void setup() {
		comicSeriesBaseSoapMapper = Mappers.getMapper(ComicSeriesBaseSoapMapper)
	}

	void "maps SOAP ComicSeriesRequest to ComicSeriesRequestDTO"() {
		given:
		ComicSeriesBaseRequest comicSeriesBaseRequest = new ComicSeriesBaseRequest(
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
		ComicSeriesRequestDTO comicSeriesRequestDTO = comicSeriesBaseSoapMapper.mapBase comicSeriesBaseRequest

		then:
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

	void "maps DB entity to base SOAP entity"() {
		given:
		ComicSeries comicSeries = createComicSeries()

		when:
		ComicSeriesBase comicSeriesBase = comicSeriesBaseSoapMapper.mapBase(Lists.newArrayList(comicSeries))[0]

		then:
		comicSeriesBase.uid == UID
		comicSeriesBase.title == TITLE
		comicSeriesBase.publishedYearFrom == PUBLISHED_YEAR_FROM
		comicSeriesBase.publishedMonthFrom == PUBLISHED_MONTH_FROM
		comicSeriesBase.publishedDayFrom == PUBLISHED_DAY_FROM
		comicSeriesBase.publishedYearTo == PUBLISHED_YEAR_TO
		comicSeriesBase.publishedMonthTo == PUBLISHED_MONTH_TO
		comicSeriesBase.publishedDayTo == PUBLISHED_DAY_TO
		comicSeriesBase.numberOfIssues == NUMBER_OF_ISSUES
		comicSeriesBase.stardateFrom == STARDATE_FROM
		comicSeriesBase.stardateTo == STARDATE_TO
		comicSeriesBase.yearFrom.toInteger() == YEAR_FROM
		comicSeriesBase.yearTo.toInteger() == YEAR_TO
		comicSeriesBase.miniseries == MINISERIES
		comicSeriesBase.photonovelSeries == PHOTONOVEL_SERIES
	}

}
