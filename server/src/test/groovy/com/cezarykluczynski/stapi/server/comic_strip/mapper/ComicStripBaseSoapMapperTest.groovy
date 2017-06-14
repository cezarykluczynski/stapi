package com.cezarykluczynski.stapi.server.comic_strip.mapper

import com.cezarykluczynski.stapi.client.v1.soap.ComicStripBase
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.IntegerRange
import com.cezarykluczynski.stapi.model.comic_strip.dto.ComicStripRequestDTO
import com.cezarykluczynski.stapi.model.comic_strip.entity.ComicStrip
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class ComicStripBaseSoapMapperTest extends AbstractComicStripMapperTest {

	private ComicStripBaseSoapMapper comicStripBaseSoapMapper

	void setup() {
		comicStripBaseSoapMapper = Mappers.getMapper(ComicStripBaseSoapMapper)
	}

	void "maps SOAP ComicStripRequest to ComicStripRequestDTO"() {
		given:
		ComicStripBaseRequest comicStripBaseRequest = new ComicStripBaseRequest(
				title: TITLE,
				publishedYear: new IntegerRange(
						from: PUBLISHED_YEAR_FROM,
						to: PUBLISHED_YEAR_TO
				),
				numberOfPages: new IntegerRange(
						from: NUMBER_OF_PAGES_FROM,
						to: NUMBER_OF_PAGES_TO
				),
				year: new IntegerRange(
						from: YEAR_FROM,
						to: YEAR_TO,
				))

		when:
		ComicStripRequestDTO comicStripRequestDTO = comicStripBaseSoapMapper.mapBase comicStripBaseRequest

		then:
		comicStripRequestDTO.title == TITLE
		comicStripRequestDTO.numberOfPagesFrom == NUMBER_OF_PAGES_FROM
		comicStripRequestDTO.numberOfPagesTo == NUMBER_OF_PAGES_TO
		comicStripRequestDTO.yearFrom == YEAR_FROM
		comicStripRequestDTO.yearTo == YEAR_TO
	}

	void "maps DB entity to base SOAP entity"() {
		given:
		ComicStrip comicStrip = createComicStrip()

		when:
		ComicStripBase comicStripBase = comicStripBaseSoapMapper.mapBase(Lists.newArrayList(comicStrip))[0]

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
		comicStripBase.yearFrom.toInteger() == YEAR_FROM
		comicStripBase.yearTo.toInteger() == YEAR_TO
	}

}
