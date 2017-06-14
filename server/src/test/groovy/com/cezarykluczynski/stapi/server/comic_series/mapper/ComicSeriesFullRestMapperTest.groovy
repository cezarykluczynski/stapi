package com.cezarykluczynski.stapi.server.comic_series.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicSeriesFull
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries
import org.mapstruct.factory.Mappers

class ComicSeriesFullRestMapperTest extends AbstractComicSeriesMapperTest {

	private ComicSeriesFullRestMapper comicSeriesFullRestMapper

	void setup() {
		comicSeriesFullRestMapper = Mappers.getMapper(ComicSeriesFullRestMapper)
	}

	void "maps DB entity to full REST entity"() {
		given:
		ComicSeries comicSeries = createComicSeries()

		when:
		ComicSeriesFull comicSeriesFull = comicSeriesFullRestMapper.mapFull(comicSeries)

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
		comicSeriesFull.yearFrom == YEAR_FROM
		comicSeriesFull.yearTo == YEAR_TO
		comicSeriesFull.miniseries == MINISERIES
		comicSeriesFull.photonovelSeries == PHOTONOVEL_SERIES
		comicSeriesFull.parentSeries.size() == comicSeries.parentSeries.size()
		comicSeriesFull.childSeries.size() == comicSeries.childSeries.size()
		comicSeriesFull.publishers.size() == comicSeries.publishers.size()
		comicSeriesFull.comics.size() == comicSeries.comics.size()
	}

}
