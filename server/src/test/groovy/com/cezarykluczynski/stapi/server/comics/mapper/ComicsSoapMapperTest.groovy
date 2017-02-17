package com.cezarykluczynski.stapi.server.comics.mapper

import com.cezarykluczynski.stapi.client.v1.soap.Comics as SOAPComics
import com.cezarykluczynski.stapi.client.v1.soap.ComicsRequest
import com.cezarykluczynski.stapi.client.v1.soap.FloatRange
import com.cezarykluczynski.stapi.client.v1.soap.IntegerRange
import com.cezarykluczynski.stapi.model.comics.dto.ComicsRequestDTO
import com.cezarykluczynski.stapi.model.comics.entity.Comics as DBComics
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class ComicsSoapMapperTest extends AbstractComicsMapperTest {

	private ComicsSoapMapper comicsSoapMapper

	void setup() {
		comicsSoapMapper = Mappers.getMapper(ComicsSoapMapper)
	}

	void "maps SOAP ComicsRequest to ComicsRequestDTO"() {
		given:
		ComicsRequest comicsRequest = new ComicsRequest(
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
				stardate: new FloatRange(
						from: STARDATE_FROM,
						to: STARDATE_TO,
				),
				year: new IntegerRange(
						from: YEAR_FROM,
						to: YEAR_TO,
				),
				photonovel: PHOTONOVEL)

		when:
		ComicsRequestDTO comicsRequestDTO = comicsSoapMapper.map comicsRequest

		then:
		comicsRequestDTO.guid == GUID
		comicsRequestDTO.title == TITLE
		comicsRequestDTO.stardateFrom == STARDATE_FROM
		comicsRequestDTO.stardateTo == STARDATE_TO
		comicsRequestDTO.numberOfPagesFrom == NUMBER_OF_PAGES_FROM
		comicsRequestDTO.numberOfPagesTo == NUMBER_OF_PAGES_TO
		comicsRequestDTO.yearFrom == YEAR_FROM
		comicsRequestDTO.yearTo == YEAR_TO
		comicsRequestDTO.photonovel == PHOTONOVEL
	}

	void "maps DB entity to SOAP entity"() {
		given:
		DBComics dBComics = createComics()

		when:
		SOAPComics soapComics = comicsSoapMapper.map(Lists.newArrayList(dBComics))[0]

		then:
		soapComics.guid == GUID
		soapComics.title == TITLE
		soapComics.publishedYear == PUBLISHED_YEAR
		soapComics.publishedMonth == PUBLISHED_MONTH
		soapComics.publishedDay == PUBLISHED_DAY
		soapComics.coverYear == COVER_YEAR
		soapComics.coverMonth == COVER_MONTH
		soapComics.coverDay == COVER_DAY
		soapComics.numberOfPages == NUMBER_OF_PAGES
		soapComics.stardateFrom == STARDATE_FROM
		soapComics.stardateTo == STARDATE_TO
		soapComics.yearFrom.toInteger() == YEAR_FROM
		soapComics.yearTo.toInteger() == YEAR_TO
		soapComics.photonovel == PHOTONOVEL
		soapComics.comicSeriesHeaders.size() == dBComics.comicSeries.size()
		soapComics.writerHeaders.size() == dBComics.writers.size()
		soapComics.artistHeaders.size() == dBComics.artists.size()
		soapComics.editorHeaders.size() == dBComics.editors.size()
		soapComics.staffHeaders.size() == dBComics.staff.size()
		soapComics.publisherHeaders.size() == dBComics.publishers.size()
		soapComics.characterHeaders.size() == dBComics.characters.size()
		soapComics.references.size() == dBComics.references.size()
	}

}
