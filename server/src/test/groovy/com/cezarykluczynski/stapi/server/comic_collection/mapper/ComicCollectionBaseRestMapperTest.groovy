package com.cezarykluczynski.stapi.server.comic_collection.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicCollectionBase
import com.cezarykluczynski.stapi.model.comic_collection.dto.ComicCollectionRequestDTO
import com.cezarykluczynski.stapi.model.comic_collection.entity.ComicCollection
import com.cezarykluczynski.stapi.server.comic_collection.dto.ComicCollectionRestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class ComicCollectionBaseRestMapperTest extends AbstractComicCollectionMapperTest {

	private ComicCollectionBaseRestMapper comicCollectionRestMapper

	void setup() {
		comicCollectionRestMapper = Mappers.getMapper(ComicCollectionBaseRestMapper)
	}

	void "maps ComicCollectionRestBeanParams to ComicCollectionRequestDTO"() {
		given:
		ComicCollectionRestBeanParams comicCollectionRestBeanParams = new ComicCollectionRestBeanParams(
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
				photonovel: PHOTONOVEL)

		when:
		ComicCollectionRequestDTO comicCollectionRequestDTO = comicCollectionRestMapper.mapBase comicCollectionRestBeanParams

		then:
		comicCollectionRequestDTO.uid == UID
		comicCollectionRequestDTO.title == TITLE
		comicCollectionRequestDTO.stardateFrom == STARDATE_FROM
		comicCollectionRequestDTO.stardateTo == STARDATE_TO
		comicCollectionRequestDTO.numberOfPagesFrom == NUMBER_OF_PAGES_FROM
		comicCollectionRequestDTO.numberOfPagesTo == NUMBER_OF_PAGES_TO
		comicCollectionRequestDTO.yearFrom == YEAR_FROM
		comicCollectionRequestDTO.yearTo == YEAR_TO
		comicCollectionRequestDTO.photonovel == PHOTONOVEL
	}

	void "maps DB entity to base REST entity"() {
		given:
		ComicCollection comicCollection = createComicCollection()

		when:
		ComicCollectionBase comicCollectionBase = comicCollectionRestMapper.mapBase(Lists.newArrayList(comicCollection))[0]

		then:
		comicCollectionBase.uid == UID
		comicCollectionBase.title == TITLE
		comicCollectionBase.publishedYear == PUBLISHED_YEAR
		comicCollectionBase.publishedMonth == PUBLISHED_MONTH
		comicCollectionBase.publishedDay == PUBLISHED_DAY
		comicCollectionBase.coverYear == COVER_YEAR
		comicCollectionBase.coverMonth == COVER_MONTH
		comicCollectionBase.coverDay == COVER_DAY
		comicCollectionBase.numberOfPages == NUMBER_OF_PAGES
		comicCollectionBase.stardateFrom == STARDATE_FROM
		comicCollectionBase.stardateTo == STARDATE_TO
		comicCollectionBase.yearFrom == YEAR_FROM
		comicCollectionBase.yearTo == YEAR_TO
		comicCollectionBase.photonovel == PHOTONOVEL
	}

}
