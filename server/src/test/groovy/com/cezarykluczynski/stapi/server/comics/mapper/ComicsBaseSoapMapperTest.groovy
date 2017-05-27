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
				photonovel: PHOTONOVEL,
				adaptation: ADAPTATION)

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
		ComicsBase comicsBase = comicsBaseSoapMapper.mapBase(Lists.newArrayList(comics))[0]

		then:
		comicsBase.uid == UID
		comicsBase.title == TITLE
		comicsBase.publishedYear == PUBLISHED_YEAR
		comicsBase.publishedMonth == PUBLISHED_MONTH
		comicsBase.publishedDay == PUBLISHED_DAY
		comicsBase.coverYear == COVER_YEAR
		comicsBase.coverMonth == COVER_MONTH
		comicsBase.coverDay == COVER_DAY
		comicsBase.numberOfPages == NUMBER_OF_PAGES
		comicsBase.stardateFrom == STARDATE_FROM
		comicsBase.stardateTo == STARDATE_TO
		comicsBase.yearFrom.toInteger() == YEAR_FROM
		comicsBase.yearTo.toInteger() == YEAR_TO
		comicsBase.photonovel == PHOTONOVEL
		comicsBase.adaptation == ADAPTATION
	}

}
