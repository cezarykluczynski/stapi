package com.cezarykluczynski.stapi.server.comic_series.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicSeriesBase as ComicSeriesBase
import com.cezarykluczynski.stapi.model.comic_series.dto.ComicSeriesRequestDTO
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries as ComicSeries
import com.cezarykluczynski.stapi.server.comic_series.dto.ComicSeriesRestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class ComicSeriesBaseRestMapperTest extends AbstractComicSeriesMapperTest {

	private ComicSeriesBaseRestMapper comicSeriesBaseRestMapper

	void setup() {
		comicSeriesBaseRestMapper = Mappers.getMapper(ComicSeriesBaseRestMapper)
	}

	void "maps ComicSeriesRestBeanParams to ComicSeriesRequestDTO"() {
		given:
		ComicSeriesRestBeanParams comicSeriesRestBeanParams = new ComicSeriesRestBeanParams(
				uid: UID,
				title: TITLE,
				publishedYearFrom: PUBLISHED_YEAR_FROM,
				publishedYearTo: PUBLISHED_YEAR_TO,
				numberOfIssuesFrom: NUMBER_OF_ISSUES_FROM,
				numberOfIssuesTo: NUMBER_OF_ISSUES_TO,
				stardateFrom: STARDATE_FROM,
				stardateTo: STARDATE_TO,
				yearFrom: YEAR_FROM,
				yearTo: YEAR_TO,
				miniseries: MINISERIES,
				photonovelSeries: PHOTONOVEL_SERIES)

		when:
		ComicSeriesRequestDTO comicSeriesRequestDTO = comicSeriesBaseRestMapper.mapBase comicSeriesRestBeanParams

		then:
		comicSeriesRequestDTO.uid == UID
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

	void "maps DB entity to base REST entity"() {
		given:
		ComicSeries comicSeries = createComicSeries()

		when:
		ComicSeriesBase restComicSeries = comicSeriesBaseRestMapper.mapBase(Lists.newArrayList(comicSeries))[0]

		then:
		restComicSeries.uid == UID
		restComicSeries.title == TITLE
		restComicSeries.publishedYearFrom == PUBLISHED_YEAR_FROM
		restComicSeries.publishedMonthFrom == PUBLISHED_MONTH_FROM
		restComicSeries.publishedDayFrom == PUBLISHED_DAY_FROM
		restComicSeries.publishedYearTo == PUBLISHED_YEAR_TO
		restComicSeries.publishedMonthTo == PUBLISHED_MONTH_TO
		restComicSeries.publishedDayTo == PUBLISHED_DAY_TO
		restComicSeries.numberOfIssues == NUMBER_OF_ISSUES
		restComicSeries.stardateFrom == STARDATE_FROM
		restComicSeries.stardateTo == STARDATE_TO
		restComicSeries.yearFrom == YEAR_FROM
		restComicSeries.yearTo == YEAR_TO
		restComicSeries.miniseries == MINISERIES
		restComicSeries.photonovelSeries == PHOTONOVEL_SERIES
	}

}
