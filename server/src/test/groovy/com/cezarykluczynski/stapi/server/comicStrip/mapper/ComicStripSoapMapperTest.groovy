package com.cezarykluczynski.stapi.server.comicStrip.mapper

import com.cezarykluczynski.stapi.client.v1.soap.ComicStrip as SOAPComicStrip
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripRequest
import com.cezarykluczynski.stapi.client.v1.soap.IntegerRange
import com.cezarykluczynski.stapi.model.comicStrip.dto.ComicStripRequestDTO
import com.cezarykluczynski.stapi.model.comicStrip.entity.ComicStrip as DBComicStrip
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class ComicStripSoapMapperTest extends AbstractComicStripMapperTest {

	private ComicStripSoapMapper comicStripSoapMapper

	void setup() {
		comicStripSoapMapper = Mappers.getMapper(ComicStripSoapMapper)
	}

	void "maps SOAP ComicStripRequest to ComicStripRequestDTO"() {
		given:
		ComicStripRequest comicStripRequest = new ComicStripRequest(
				guid: GUID,
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
		ComicStripRequestDTO comicStripRequestDTO = comicStripSoapMapper.map comicStripRequest

		then:
		comicStripRequestDTO.guid == GUID
		comicStripRequestDTO.title == TITLE
		comicStripRequestDTO.numberOfPagesFrom == NUMBER_OF_PAGES_FROM
		comicStripRequestDTO.numberOfPagesTo == NUMBER_OF_PAGES_TO
		comicStripRequestDTO.yearFrom == YEAR_FROM
		comicStripRequestDTO.yearTo == YEAR_TO
	}

	void "maps DB entity to SOAP entity"() {
		given:
		DBComicStrip dBComicStrip = createComicStrip()

		when:
		SOAPComicStrip soapComicStrip = comicStripSoapMapper.map(Lists.newArrayList(dBComicStrip))[0]

		then:
		soapComicStrip.guid == GUID
		soapComicStrip.title == TITLE
		soapComicStrip.periodical == PERIODICAL
		soapComicStrip.publishedYearFrom == PUBLISHED_YEAR_FROM
		soapComicStrip.publishedMonthFrom == PUBLISHED_MONTH_FROM
		soapComicStrip.publishedDayFrom == PUBLISHED_DAY_FROM
		soapComicStrip.publishedYearTo == PUBLISHED_YEAR_TO
		soapComicStrip.publishedMonthTo == PUBLISHED_MONTH_TO
		soapComicStrip.publishedDayTo == PUBLISHED_DAY_TO
		soapComicStrip.numberOfPages == NUMBER_OF_PAGES
		soapComicStrip.yearFrom.toInteger() == YEAR_FROM
		soapComicStrip.yearTo.toInteger() == YEAR_TO
		soapComicStrip.comicSeriesHeaders.size() == dBComicStrip.comicSeries.size()
		soapComicStrip.writerHeaders.size() == dBComicStrip.writers.size()
		soapComicStrip.artistHeaders.size() == dBComicStrip.artists.size()
		soapComicStrip.characterHeaders.size() == dBComicStrip.characters.size()
	}

}
