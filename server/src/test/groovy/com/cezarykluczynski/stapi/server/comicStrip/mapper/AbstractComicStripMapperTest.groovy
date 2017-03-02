package com.cezarykluczynski.stapi.server.comicStrip.mapper

import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.comicSeries.entity.ComicSeries
import com.cezarykluczynski.stapi.model.comicStrip.entity.ComicStrip
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.util.AbstractComicStripTest
import com.google.common.collect.Sets

abstract class AbstractComicStripMapperTest extends AbstractComicStripTest {

	protected ComicStrip createComicStrip() {
		new ComicStrip(
				guid: GUID,
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
				comicSeries: Sets.newHashSet(Mock(ComicSeries)),
				writers: Sets.newHashSet(Mock(Staff)),
				artists: Sets.newHashSet(Mock(Staff)),
				characters: Sets.newHashSet(Mock(Character)))
	}

}