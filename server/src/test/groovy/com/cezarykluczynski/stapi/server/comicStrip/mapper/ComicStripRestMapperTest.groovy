package com.cezarykluczynski.stapi.server.comicStrip.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicStrip as RESTComicStrip
import com.cezarykluczynski.stapi.model.comicStrip.dto.ComicStripRequestDTO
import com.cezarykluczynski.stapi.model.comicStrip.entity.ComicStrip as DBComicStrip
import com.cezarykluczynski.stapi.server.comicStrip.dto.ComicStripRestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class ComicStripRestMapperTest extends AbstractComicStripMapperTest {

	private ComicStripRestMapper comicStripRestMapper

	void setup() {
		comicStripRestMapper = Mappers.getMapper(ComicStripRestMapper)
	}

	void "maps ComicStripRestBeanParams to ComicStripRequestDTO"() {
		given:
		ComicStripRestBeanParams comicStripRestBeanParams = new ComicStripRestBeanParams(
				guid: GUID,
				title: TITLE,
				publishedYearFrom: PUBLISHED_YEAR_FROM,
				publishedYearTo: PUBLISHED_YEAR_TO,
				numberOfPagesFrom: NUMBER_OF_PAGES_FROM,
				numberOfPagesTo: NUMBER_OF_PAGES_TO,
				yearFrom: YEAR_FROM,
				yearTo: YEAR_TO)

		when:
		ComicStripRequestDTO comicStripRequestDTO = comicStripRestMapper.map comicStripRestBeanParams

		then:
		comicStripRequestDTO.guid == GUID
		comicStripRequestDTO.title == TITLE
		comicStripRequestDTO.numberOfPagesFrom == NUMBER_OF_PAGES_FROM
		comicStripRequestDTO.numberOfPagesTo == NUMBER_OF_PAGES_TO
		comicStripRequestDTO.yearFrom == YEAR_FROM
		comicStripRequestDTO.yearTo == YEAR_TO
	}

	void "maps DB entity to REST entity"() {
		given:
		DBComicStrip dBComicStrip = createComicStrip()

		when:
		RESTComicStrip restComicStrip = comicStripRestMapper.map(Lists.newArrayList(dBComicStrip))[0]

		then:
		restComicStrip.guid == GUID
		restComicStrip.title == TITLE
		restComicStrip.periodical == PERIODICAL
		restComicStrip.publishedYearFrom == PUBLISHED_YEAR_FROM
		restComicStrip.publishedMonthFrom == PUBLISHED_MONTH_FROM
		restComicStrip.publishedDayFrom == PUBLISHED_DAY_FROM
		restComicStrip.publishedYearTo == PUBLISHED_YEAR_TO
		restComicStrip.publishedMonthTo == PUBLISHED_MONTH_TO
		restComicStrip.publishedDayTo == PUBLISHED_DAY_TO
		restComicStrip.numberOfPages == NUMBER_OF_PAGES
		restComicStrip.yearFrom == YEAR_FROM
		restComicStrip.yearTo == YEAR_TO
		restComicStrip.comicSeriesHeaders.size() == dBComicStrip.comicSeries.size()
		restComicStrip.writerHeaders.size() == dBComicStrip.writers.size()
		restComicStrip.artistHeaders.size() == dBComicStrip.artists.size()
		restComicStrip.characterHeaders.size() == dBComicStrip.characters.size()
	}

}
