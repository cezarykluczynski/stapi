package com.cezarykluczynski.stapi.server.comicSeries.mapper

import com.cezarykluczynski.stapi.model.comicSeries.entity.ComicSeries
import com.cezarykluczynski.stapi.model.comics.entity.Comics
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.util.AbstractComicSeriesTest
import com.google.common.collect.Sets

abstract class AbstractComicSeriesMapperTest extends AbstractComicSeriesTest {

	protected ComicSeries createComicSeries() {
		new ComicSeries(
				guid: GUID,
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
				parentSeries: Sets.newHashSet(Mock(ComicSeries)),
				childSeries: Sets.newHashSet(Mock(ComicSeries)),
				publishers: Sets.newHashSet(Mock(Company)),
				comics: Sets.newHashSet(Mock(Comics)))
	}

}
