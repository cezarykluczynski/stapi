package com.cezarykluczynski.stapi.server.comic_collection.mapper

import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionBase
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.FloatRange
import com.cezarykluczynski.stapi.client.v1.soap.IntegerRange
import com.cezarykluczynski.stapi.model.comic_collection.dto.ComicCollectionRequestDTO
import com.cezarykluczynski.stapi.model.comic_collection.entity.ComicCollection
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class ComicCollectionBaseSoapMapperTest extends AbstractComicCollectionMapperTest {

	private ComicCollectionBaseSoapMapper comicCollectionBaseSoapMapper

	void setup() {
		comicCollectionBaseSoapMapper = Mappers.getMapper(ComicCollectionBaseSoapMapper)
	}

	void "maps SOAP ComicCollectionBaseRequest to ComicCollectionRequestDTO"() {
		given:
		ComicCollectionBaseRequest comicCollectionRequest = new ComicCollectionBaseRequest(
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
		ComicCollectionRequestDTO comicCollectionRequestDTO = comicCollectionBaseSoapMapper.mapBase comicCollectionRequest

		then:
		comicCollectionRequestDTO.title == TITLE
		comicCollectionRequestDTO.stardateFrom == STARDATE_FROM
		comicCollectionRequestDTO.stardateTo == STARDATE_TO
		comicCollectionRequestDTO.numberOfPagesFrom == NUMBER_OF_PAGES_FROM
		comicCollectionRequestDTO.numberOfPagesTo == NUMBER_OF_PAGES_TO
		comicCollectionRequestDTO.yearFrom == YEAR_FROM
		comicCollectionRequestDTO.yearTo == YEAR_TO
		comicCollectionRequestDTO.photonovel == PHOTONOVEL
	}

	void "maps DB entity to base SOAP entity"() {
		given:
		ComicCollection comicCollection = createComicCollection()

		when:
		ComicCollectionBase comicCollectionBase = comicCollectionBaseSoapMapper.mapBase(Lists.newArrayList(comicCollection))[0]

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
		comicCollectionBase.yearFrom.toInteger() == YEAR_FROM
		comicCollectionBase.yearTo.toInteger() == YEAR_TO
		comicCollectionBase.photonovel == PHOTONOVEL
	}

}
