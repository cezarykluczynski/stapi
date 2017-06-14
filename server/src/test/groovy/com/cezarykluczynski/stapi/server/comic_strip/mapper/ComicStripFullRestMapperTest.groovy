package com.cezarykluczynski.stapi.server.comic_strip.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicStripFull
import com.cezarykluczynski.stapi.model.comic_strip.entity.ComicStrip
import org.mapstruct.factory.Mappers

class ComicStripFullRestMapperTest extends AbstractComicStripMapperTest {

	private ComicStripFullRestMapper comicStripFullRestMapper

	void setup() {
		comicStripFullRestMapper = Mappers.getMapper(ComicStripFullRestMapper)
	}

	void "maps DB entity to full REST entity"() {
		given:
		ComicStrip comicStrip = createComicStrip()

		when:
		ComicStripFull comicStripFull = comicStripFullRestMapper.mapFull(comicStrip)

		then:
		comicStripFull.uid == UID
		comicStripFull.title == TITLE
		comicStripFull.periodical == PERIODICAL
		comicStripFull.publishedYearFrom == PUBLISHED_YEAR_FROM
		comicStripFull.publishedMonthFrom == PUBLISHED_MONTH_FROM
		comicStripFull.publishedDayFrom == PUBLISHED_DAY_FROM
		comicStripFull.publishedYearTo == PUBLISHED_YEAR_TO
		comicStripFull.publishedMonthTo == PUBLISHED_MONTH_TO
		comicStripFull.publishedDayTo == PUBLISHED_DAY_TO
		comicStripFull.numberOfPages == NUMBER_OF_PAGES
		comicStripFull.yearFrom == YEAR_FROM
		comicStripFull.yearTo == YEAR_TO
		comicStripFull.comicSeries.size() == comicStrip.comicSeries.size()
		comicStripFull.writers.size() == comicStrip.writers.size()
		comicStripFull.artists.size() == comicStrip.artists.size()
		comicStripFull.characters.size() == comicStrip.characters.size()
	}

}
