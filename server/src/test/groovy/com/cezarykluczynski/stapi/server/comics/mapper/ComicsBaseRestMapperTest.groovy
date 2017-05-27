package com.cezarykluczynski.stapi.server.comics.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicsBase
import com.cezarykluczynski.stapi.model.comics.dto.ComicsRequestDTO
import com.cezarykluczynski.stapi.model.comics.entity.Comics
import com.cezarykluczynski.stapi.server.comics.dto.ComicsRestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class ComicsBaseRestMapperTest extends AbstractComicsMapperTest {

	private ComicsBaseRestMapper comicsBaseRestMapper

	void setup() {
		comicsBaseRestMapper = Mappers.getMapper(ComicsBaseRestMapper)
	}

	void "maps ComicsRestBeanParams to ComicsRequestDTO"() {
		given:
		ComicsRestBeanParams comicsRestBeanParams = new ComicsRestBeanParams(
				uid: UID,
				title: TITLE,
				publishedYearFrom: PUBLISHED_YEAR_FROM,
				publishedYearTo: PUBLISHED_YEAR_TO,
				numberOfPagesFrom: NUMBER_OF_PAGES_FROM,
				numberOfPagesTo: NUMBER_OF_PAGES_TO,
				stardateFrom: STARDATE_FROM,
				stardateTo: STARDATE_TO,
				yearFrom: YEAR_FROM,
				yearTo: YEAR_TO,
				photonovel: PHOTONOVEL,
				adaptation: ADAPTATION)

		when:
		ComicsRequestDTO comicsRequestDTO = comicsBaseRestMapper.mapBase comicsRestBeanParams

		then:
		comicsRequestDTO.uid == UID
		comicsRequestDTO.title == TITLE
		comicsRequestDTO.stardateFrom == STARDATE_FROM
		comicsRequestDTO.stardateTo == STARDATE_TO
		comicsRequestDTO.numberOfPagesFrom == NUMBER_OF_PAGES_FROM
		comicsRequestDTO.numberOfPagesTo == NUMBER_OF_PAGES_TO
		comicsRequestDTO.yearFrom == YEAR_FROM
		comicsRequestDTO.yearTo == YEAR_TO
		comicsRequestDTO.photonovel == PHOTONOVEL
		comicsRequestDTO.adaptation == ADAPTATION
	}

	void "maps DB entity to base REST entity"() {
		given:
		Comics comics = createComics()

		when:
		ComicsBase comicsBase = comicsBaseRestMapper.mapBase(Lists.newArrayList(comics))[0]

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
		comicsBase.yearFrom == YEAR_FROM
		comicsBase.yearTo == YEAR_TO
		comicsBase.photonovel == PHOTONOVEL
		comicsBase.adaptation == ADAPTATION
	}

}
