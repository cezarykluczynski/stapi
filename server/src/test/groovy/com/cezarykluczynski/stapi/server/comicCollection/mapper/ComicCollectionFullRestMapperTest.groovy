package com.cezarykluczynski.stapi.server.comicCollection.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicCollectionFull
import com.cezarykluczynski.stapi.model.comicCollection.entity.ComicCollection
import org.mapstruct.factory.Mappers

class ComicCollectionFullRestMapperTest extends AbstractComicCollectionMapperTest {

	private ComicCollectionFullRestMapper comicCollectionFullRestMapper

	void setup() {
		comicCollectionFullRestMapper = Mappers.getMapper(ComicCollectionFullRestMapper)
	}

	void "maps DB entity to base REST entity"() {
		given:
		ComicCollection comicCollection = createComicCollection()

		when:
		ComicCollectionFull restComicCollection = comicCollectionFullRestMapper.mapFull(comicCollection)

		then:
		restComicCollection.uid == UID
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
		restComicCollection.comicSeries.size() == comicCollection.comicSeries.size()
		restComicCollection.writers.size() == comicCollection.writers.size()
		restComicCollection.artists.size() == comicCollection.artists.size()
		restComicCollection.editors.size() == comicCollection.editors.size()
		restComicCollection.staff.size() == comicCollection.staff.size()
		restComicCollection.publishers.size() == comicCollection.publishers.size()
		restComicCollection.characters.size() == comicCollection.characters.size()
		restComicCollection.references.size() == comicCollection.references.size()
		restComicCollection.comics.size() == comicCollection.comics.size()
	}

}
