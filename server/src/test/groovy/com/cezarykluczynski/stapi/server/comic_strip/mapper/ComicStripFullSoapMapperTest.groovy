package com.cezarykluczynski.stapi.server.comic_strip.mapper

import com.cezarykluczynski.stapi.client.v1.soap.ComicStripFull
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripFullRequest
import com.cezarykluczynski.stapi.model.comic_strip.entity.ComicStrip
import com.cezarykluczynski.stapi.model.comic_strip.dto.ComicStripRequestDTO
import org.mapstruct.factory.Mappers

class ComicStripFullSoapMapperTest extends AbstractComicStripMapperTest {

	private ComicStripFullSoapMapper comicStripFullSoapMapper

	void setup() {
		comicStripFullSoapMapper = Mappers.getMapper(ComicStripFullSoapMapper)
	}

	void "maps SOAP ComicStripFullRequest to ComicStripBaseRequestDTO"() {
		given:
		ComicStripFullRequest comicStripFullRequest = new ComicStripFullRequest(uid: UID)

		when:
		ComicStripRequestDTO comicStripRequestDTO = comicStripFullSoapMapper.mapFull comicStripFullRequest

		then:
		comicStripRequestDTO.uid == UID
	}

	void "maps DB entity to full SOAP entity"() {
		given:
		ComicStrip comicStrip = createComicStrip()

		when:
		ComicStripFull comicStripFull = comicStripFullSoapMapper.mapFull(comicStrip)

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
		comicStripFull.yearFrom.toInteger() == YEAR_FROM
		comicStripFull.yearTo.toInteger() == YEAR_TO
		comicStripFull.comicSeries.size() == comicStrip.comicSeries.size()
		comicStripFull.writers.size() == comicStrip.writers.size()
		comicStripFull.artists.size() == comicStrip.artists.size()
		comicStripFull.characters.size() == comicStrip.characters.size()
	}

}
