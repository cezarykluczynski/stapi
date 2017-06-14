package com.cezarykluczynski.stapi.server.comic_strip.mapper

import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries
import com.cezarykluczynski.stapi.model.comic_strip.entity.ComicStrip
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.util.AbstractComicStripTest

abstract class AbstractComicStripMapperTest extends AbstractComicStripTest {

	protected ComicStrip createComicStrip() {
		new ComicStrip(
				uid: UID,
				title: TITLE,
				periodical: PERIODICAL,
				publishedYearFrom: PUBLISHED_YEAR_FROM,
				publishedMonthFrom: PUBLISHED_MONTH_FROM,
				publishedDayFrom: PUBLISHED_DAY_FROM,
				publishedYearTo: PUBLISHED_YEAR_TO,
				publishedMonthTo: PUBLISHED_MONTH_TO,
				publishedDayTo: PUBLISHED_DAY_TO,
				numberOfPages: NUMBER_OF_PAGES,
				yearFrom: YEAR_FROM,
				yearTo: YEAR_TO,
				comicSeries: createSetOfRandomNumberOfMocks(ComicSeries),
				writers: createSetOfRandomNumberOfMocks(Staff),
				artists: createSetOfRandomNumberOfMocks(Staff),
				characters: createSetOfRandomNumberOfMocks(Character))
	}

}
