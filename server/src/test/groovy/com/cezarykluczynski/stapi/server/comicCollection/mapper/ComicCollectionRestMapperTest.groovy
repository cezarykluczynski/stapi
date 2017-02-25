package com.cezarykluczynski.stapi.server.comicCollection.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicCollection as RESTComicCollection
import com.cezarykluczynski.stapi.model.comicCollection.dto.ComicCollectionRequestDTO
import com.cezarykluczynski.stapi.model.comicCollection.entity.ComicCollection as DBComicCollection
import com.cezarykluczynski.stapi.server.comicCollection.dto.ComicCollectionRestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class ComicCollectionRestMapperTest extends AbstractComicCollectionMapperTest {

	private ComicCollectionRestMapper comicCollectionRestMapper

	void setup() {
		comicCollectionRestMapper = Mappers.getMapper(ComicCollectionRestMapper)
	}

	void "maps ComicCollectionRestBeanParams to ComicCollectionRequestDTO"() {
		given:
		ComicCollectionRestBeanParams comicCollectionRestBeanParams = new ComicCollectionRestBeanParams(
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
		ComicCollectionRequestDTO comicCollectionRequestDTO = comicCollectionRestMapper.map comicCollectionRestBeanParams

		then:
		comicCollectionRequestDTO.guid == GUID
		comicCollectionRequestDTO.title == TITLE
		comicCollectionRequestDTO.stardateFrom == STARDATE_FROM
		comicCollectionRequestDTO.stardateTo == STARDATE_TO
		comicCollectionRequestDTO.numberOfPagesFrom == NUMBER_OF_PAGES_FROM
		comicCollectionRequestDTO.numberOfPagesTo == NUMBER_OF_PAGES_TO
		comicCollectionRequestDTO.yearFrom == YEAR_FROM
		comicCollectionRequestDTO.yearTo == YEAR_TO
		comicCollectionRequestDTO.photonovel == PHOTONOVEL
	}

	void "maps DB entity to REST entity"() {
		given:
		DBComicCollection dBComicCollection = createComicCollection()

		when:
		RESTComicCollection restComicCollection = comicCollectionRestMapper.map(Lists.newArrayList(dBComicCollection))[0]

		then:
		restComicCollection.guid == GUID
		restComicCollection.title == TITLE
		restComicCollection.publishedYear == PUBLISHED_YEAR
		restComicCollection.publishedMonth == PUBLISHED_MONTH
		restComicCollection.publishedDay == PUBLISHED_DAY
		restComicCollection.coverYear == COVER_YEAR
		restComicCollection.coverMonth == COVER_MONTH
		restComicCollection.coverDay == COVER_DAY
		restComicCollection.numberOfPages == NUMBER_OF_PAGES
		restComicCollection.stardateFrom == STARDATE_FROM
		restComicCollection.stardateTo == STARDATE_TO
		restComicCollection.yearFrom == YEAR_FROM
		restComicCollection.yearTo == YEAR_TO
		restComicCollection.photonovel == PHOTONOVEL
		restComicCollection.comicSeriesHeaders.size() == dBComicCollection.comicSeries.size()
		restComicCollection.writerHeaders.size() == dBComicCollection.writers.size()
		restComicCollection.artistHeaders.size() == dBComicCollection.artists.size()
		restComicCollection.editorHeaders.size() == dBComicCollection.editors.size()
		restComicCollection.staffHeaders.size() == dBComicCollection.staff.size()
		restComicCollection.publisherHeaders.size() == dBComicCollection.publishers.size()
		restComicCollection.characterHeaders.size() == dBComicCollection.characters.size()
		restComicCollection.references.size() == dBComicCollection.references.size()
		restComicCollection.comicsHeaders.size() == dBComicCollection.comics.size()
	}

}
