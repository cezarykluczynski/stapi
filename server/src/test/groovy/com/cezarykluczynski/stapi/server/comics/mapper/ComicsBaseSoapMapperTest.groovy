package com.cezarykluczynski.stapi.server.comics.mapper

import com.cezarykluczynski.stapi.client.v1.soap.ComicsBase as ComicsBase
import com.cezarykluczynski.stapi.client.v1.soap.ComicsBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.FloatRange
import com.cezarykluczynski.stapi.client.v1.soap.IntegerRange
import com.cezarykluczynski.stapi.model.comics.dto.ComicsRequestDTO
import com.cezarykluczynski.stapi.model.comics.entity.Comics as Comics
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class ComicsBaseSoapMapperTest extends AbstractComicsMapperTest {

	private ComicsBaseSoapMapper comicsBaseSoapMapper

	void setup() {
		comicsBaseSoapMapper = Mappers.getMapper(ComicsBaseSoapMapper)
	}

	void "maps SOAP ComicsRequest to ComicsRequestDTO"() {
		given:
		ComicsBaseRequest comicsBaseRequest = new ComicsBaseRequest(
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
		ComicsRequestDTO comicsRequestDTO = comicsBaseSoapMapper.mapBase comicsBaseRequest

		then:
		comicsRequestDTO.title == TITLE
		comicsRequestDTO.stardateFrom == STARDATE_FROM
		comicsRequestDTO.stardateTo == STARDATE_TO
		comicsRequestDTO.numberOfPagesFrom == NUMBER_OF_PAGES_FROM
		comicsRequestDTO.numberOfPagesTo == NUMBER_OF_PAGES_TO
		comicsRequestDTO.yearFrom == YEAR_FROM
		comicsRequestDTO.yearTo == YEAR_TO
		comicsRequestDTO.photonovel == PHOTONOVEL
	}

	void "maps DB entity to base SOAP entity"() {
		given:
		Comics comics = createComics()

		when:
		ComicsBase soapComics = comicsBaseSoapMapper.mapBase(Lists.newArrayList(comics))[0]

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
	}

}
