package com.cezarykluczynski.stapi.server.comic_series.mapper

import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries
import com.cezarykluczynski.stapi.model.comics.entity.Comics
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.util.AbstractComicSeriesTest

abstract class AbstractComicSeriesMapperTest extends AbstractComicSeriesTest {

	protected ComicSeries createComicSeries() {
		new ComicSeries(
				uid: UID,
				title: TITLE,
				publishedYearFrom: PUBLISHED_YEAR_FROM,
				publishedMonthFrom: PUBLISHED_MONTH_FROM,
				publishedDayFrom: PUBLISHED_DAY_FROM,
				publishedYearTo: PUBLISHED_YEAR_TO,
				publishedMonthTo: PUBLISHED_MONTH_TO,
				publishedDayTo: PUBLISHED_DAY_TO,
				numberOfIssues: NUMBER_OF_ISSUES,
				stardateFrom: STARDATE_FROM,
				stardateTo: STARDATE_TO,
				yearFrom: YEAR_FROM,
				yearTo: YEAR_TO,
				miniseries: MINISERIES,
				photonovelSeries: PHOTONOVEL_SERIES,
				parentSeries: createSetOfRandomNumberOfMocks(ComicSeries),
				childSeries: createSetOfRandomNumberOfMocks(ComicSeries),
				publishers: createSetOfRandomNumberOfMocks(Company),
				comics: createSetOfRandomNumberOfMocks(Comics))
	}

}
