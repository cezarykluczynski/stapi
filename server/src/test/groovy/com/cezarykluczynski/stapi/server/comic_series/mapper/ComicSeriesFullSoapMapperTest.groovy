package com.cezarykluczynski.stapi.server.comic_series.mapper

import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesFull
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesFullRequest
import com.cezarykluczynski.stapi.model.comic_series.dto.ComicSeriesRequestDTO
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries
import org.mapstruct.factory.Mappers

class ComicSeriesFullSoapMapperTest extends AbstractComicSeriesMapperTest {

	private ComicSeriesFullSoapMapper comicSeriesFullSoapMapper

	void setup() {
		comicSeriesFullSoapMapper = Mappers.getMapper(ComicSeriesFullSoapMapper)
	}

	void "maps SOAP ComicSeriesFullRequest to ComicSeriesBaseRequestDTO"() {
		given:
		ComicSeriesFullRequest comicSeriesFullRequest = new ComicSeriesFullRequest(uid: UID)

		when:
		ComicSeriesRequestDTO comicSeriesRequestDTO = comicSeriesFullSoapMapper.mapFull comicSeriesFullRequest

		then:
		comicSeriesRequestDTO.uid == UID
	}

	void "maps DB entity to base SOAP entity"() {
		given:
		ComicSeries comicSeries = createComicSeries()

		when:
		ComicSeriesFull comicSeriesFull = comicSeriesFullSoapMapper.mapFull(comicSeries)

		then:
		comicSeriesFull.uid == UID
		comicSeriesFull.title == TITLE
		comicSeriesFull.publishedYearFrom == PUBLISHED_YEAR_FROM
		comicSeriesFull.publishedMonthFrom == PUBLISHED_MONTH_FROM
		comicSeriesFull.publishedDayFrom == PUBLISHED_DAY_FROM
		comicSeriesFull.publishedYearTo == PUBLISHED_YEAR_TO
		comicSeriesFull.publishedMonthTo == PUBLISHED_MONTH_TO
		comicSeriesFull.publishedDayTo == PUBLISHED_DAY_TO
		comicSeriesFull.numberOfIssues == NUMBER_OF_ISSUES
		comicSeriesFull.stardateFrom == STARDATE_FROM
		comicSeriesFull.stardateTo == STARDATE_TO
		comicSeriesFull.yearFrom.toInteger() == YEAR_FROM
		comicSeriesFull.yearTo.toInteger() == YEAR_TO
		comicSeriesFull.miniseries == MINISERIES
		comicSeriesFull.photonovelSeries == PHOTONOVEL_SERIES
		comicSeriesFull.parentSeries.size() == comicSeries.parentSeries.size()
		comicSeriesFull.childSeries.size() == comicSeries.childSeries.size()
		comicSeriesFull.publishers.size() == comicSeries.publishers.size()
		comicSeriesFull.comics.size() == comicSeries.comics.size()
	}

}
