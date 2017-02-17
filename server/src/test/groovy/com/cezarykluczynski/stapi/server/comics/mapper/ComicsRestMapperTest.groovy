package com.cezarykluczynski.stapi.server.comics.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.Comics as RESTComics
import com.cezarykluczynski.stapi.model.comics.dto.ComicsRequestDTO
import com.cezarykluczynski.stapi.model.comics.entity.Comics as DBComics
import com.cezarykluczynski.stapi.server.comics.dto.ComicsRestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class ComicsRestMapperTest extends AbstractComicsMapperTest {

	private ComicsRestMapper comicsRestMapper

	void setup() {
		comicsRestMapper = Mappers.getMapper(ComicsRestMapper)
	}

	void "maps ComicsRestBeanParams to ComicsRequestDTO"() {
		given:
		ComicsRestBeanParams comicsRestBeanParams = new ComicsRestBeanParams(
				guid: GUID,
				title: TITLE,
				publishedYearFrom: PUBLISHED_YEAR_FROM,
				publishedYearTo: PUBLISHED_YEAR_TO,
				numberOfPagesFrom: NUMBER_OF_PAGES_FROM,
				numberOfPagesTo: NUMBER_OF_PAGES_TO,
				stardateFrom: STARDATE_FROM,
				stardateTo: STARDATE_TO,
				yearFrom: YEAR_FROM,
				yearTo: YEAR_TO,
				photonovel: PHOTONOVEL)

		when:
		ComicsRequestDTO comicsRequestDTO = comicsRestMapper.map comicsRestBeanParams

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

	void "maps DB entity to REST entity"() {
		given:
		DBComics dBComics = createComics()

		when:
		RESTComics restComics = comicsRestMapper.map(Lists.newArrayList(dBComics))[0]

		then:
		restComics.guid == GUID
		restComics.title == TITLE
		restComics.publishedYear == PUBLISHED_YEAR
		restComics.publishedMonth == PUBLISHED_MONTH
		restComics.publishedDay == PUBLISHED_DAY
		restComics.coverYear == COVER_YEAR
		restComics.coverMonth == COVER_MONTH
		restComics.coverDay == COVER_DAY
		restComics.numberOfPages == NUMBER_OF_PAGES
		restComics.stardateFrom == STARDATE_FROM
		restComics.stardateTo == STARDATE_TO
		restComics.yearFrom == YEAR_FROM
		restComics.yearTo == YEAR_TO
		restComics.photonovel == PHOTONOVEL
		restComics.comicSeriesHeaders.size() == dBComics.comicSeries.size()
		restComics.writerHeaders.size() == dBComics.writers.size()
		restComics.artistHeaders.size() == dBComics.artists.size()
		restComics.editorHeaders.size() == dBComics.editors.size()
		restComics.staffHeaders.size() == dBComics.staff.size()
		restComics.publisherHeaders.size() == dBComics.publishers.size()
		restComics.characterHeaders.size() == dBComics.characters.size()
		restComics.references.size() == dBComics.references.size()
	}

}
