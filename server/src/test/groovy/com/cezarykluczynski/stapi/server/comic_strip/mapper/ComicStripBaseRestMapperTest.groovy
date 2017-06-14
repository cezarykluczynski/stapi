package com.cezarykluczynski.stapi.server.comic_strip.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicStripBase
import com.cezarykluczynski.stapi.model.comic_strip.dto.ComicStripRequestDTO
import com.cezarykluczynski.stapi.model.comic_strip.entity.ComicStrip
import com.cezarykluczynski.stapi.server.comic_strip.dto.ComicStripRestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class ComicStripBaseRestMapperTest extends AbstractComicStripMapperTest {

	private ComicStripBaseRestMapper comicStripRestMapper

	void setup() {
		comicStripRestMapper = Mappers.getMapper(ComicStripBaseRestMapper)
	}

	void "maps ComicStripRestBeanParams to ComicStripRequestDTO"() {
		given:
		ComicStripRestBeanParams comicStripRestBeanParams = new ComicStripRestBeanParams(
				uid: UID,
				title: TITLE,
				publishedYearFrom: PUBLISHED_YEAR_FROM,
				publishedYearTo: PUBLISHED_YEAR_TO,
				numberOfPagesFrom: NUMBER_OF_PAGES_FROM,
				numberOfPagesTo: NUMBER_OF_PAGES_TO,
				yearFrom: YEAR_FROM,
				yearTo: YEAR_TO)

		when:
		ComicStripRequestDTO comicStripRequestDTO = comicStripRestMapper.mapBase comicStripRestBeanParams

		then:
		comicStripRequestDTO.uid == UID
		comicStripRequestDTO.title == TITLE
		comicStripRequestDTO.numberOfPagesFrom == NUMBER_OF_PAGES_FROM
		comicStripRequestDTO.numberOfPagesTo == NUMBER_OF_PAGES_TO
		comicStripRequestDTO.yearFrom == YEAR_FROM
		comicStripRequestDTO.yearTo == YEAR_TO
	}

	void "maps DB entity to base REST entity"() {
		given:
		ComicStrip comicStrip = createComicStrip()

		when:
		ComicStripBase comicStripBase = comicStripRestMapper.mapBase(Lists.newArrayList(comicStrip))[0]

		then:
		comicStripBase.uid == UID
		comicStripBase.title == TITLE
		comicStripBase.periodical == PERIODICAL
		comicStripBase.publishedYearFrom == PUBLISHED_YEAR_FROM
		comicStripBase.publishedMonthFrom == PUBLISHED_MONTH_FROM
		comicStripBase.publishedDayFrom == PUBLISHED_DAY_FROM
		comicStripBase.publishedYearTo == PUBLISHED_YEAR_TO
		comicStripBase.publishedMonthTo == PUBLISHED_MONTH_TO
		comicStripBase.publishedDayTo == PUBLISHED_DAY_TO
		comicStripBase.numberOfPages == NUMBER_OF_PAGES
		comicStripBase.yearFrom == YEAR_FROM
		comicStripBase.yearTo == YEAR_TO
	}

}
