package com.cezarykluczynski.stapi.server.comic_collection.mapper

import com.cezarykluczynski.stapi.client.rest.model.ComicCollectionFull
import com.cezarykluczynski.stapi.client.rest.model.ComicCollectionV2Full
import com.cezarykluczynski.stapi.model.comic_collection.entity.ComicCollection
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
		ComicCollectionFull comicCollectionFull = comicCollectionFullRestMapper.mapFull(comicCollection)

		then:
		comicCollectionFull.uid == UID
		comicCollectionFull.title == TITLE
		comicCollectionFull.publishedYear == PUBLISHED_YEAR
		comicCollectionFull.publishedMonth == PUBLISHED_MONTH
		comicCollectionFull.publishedDay == PUBLISHED_DAY
		comicCollectionFull.coverYear == COVER_YEAR
		comicCollectionFull.coverMonth == COVER_MONTH
		comicCollectionFull.coverDay == COVER_DAY
		comicCollectionFull.numberOfPages == NUMBER_OF_PAGES
		comicCollectionFull.stardateFrom == STARDATE_FROM
		comicCollectionFull.stardateTo == STARDATE_TO
		comicCollectionFull.yearFrom == YEAR_FROM
		comicCollectionFull.yearTo == YEAR_TO
		comicCollectionFull.photonovel == PHOTONOVEL
		comicCollectionFull.comicSeries.size() == comicCollection.comicSeries.size()
		comicCollectionFull.writers.size() == comicCollection.writers.size()
		comicCollectionFull.artists.size() == comicCollection.artists.size()
		comicCollectionFull.editors.size() == comicCollection.editors.size()
		comicCollectionFull.staff.size() == comicCollection.staff.size()
		comicCollectionFull.publishers.size() == comicCollection.publishers.size()
		comicCollectionFull.characters.size() == comicCollection.characters.size()
		comicCollectionFull.references.size() == comicCollection.references.size()
		comicCollectionFull.comics.size() == comicCollection.comics.size()
	}

	void "maps DB entity to base REST V2 entity"() {
		given:
		ComicCollection comicCollection = createComicCollection()

		when:
		ComicCollectionV2Full comicCollectionV2Full = comicCollectionFullRestMapper.mapV2Full(comicCollection)

		then:
		comicCollectionV2Full.uid == UID
		comicCollectionV2Full.title == TITLE
		comicCollectionV2Full.publishedYear == PUBLISHED_YEAR
		comicCollectionV2Full.publishedMonth == PUBLISHED_MONTH
		comicCollectionV2Full.publishedDay == PUBLISHED_DAY
		comicCollectionV2Full.coverYear == COVER_YEAR
		comicCollectionV2Full.coverMonth == COVER_MONTH
		comicCollectionV2Full.coverDay == COVER_DAY
		comicCollectionV2Full.numberOfPages == NUMBER_OF_PAGES
		comicCollectionV2Full.stardateFrom == STARDATE_FROM
		comicCollectionV2Full.stardateTo == STARDATE_TO
		comicCollectionV2Full.yearFrom == YEAR_FROM
		comicCollectionV2Full.yearTo == YEAR_TO
		comicCollectionV2Full.photonovel == PHOTONOVEL
		comicCollectionV2Full.comicSeries.size() == comicCollection.comicSeries.size()
		comicCollectionV2Full.childComicSeries.size() == comicCollection.childComicSeries.size()
		comicCollectionV2Full.writers.size() == comicCollection.writers.size()
		comicCollectionV2Full.artists.size() == comicCollection.artists.size()
		comicCollectionV2Full.editors.size() == comicCollection.editors.size()
		comicCollectionV2Full.staff.size() == comicCollection.staff.size()
		comicCollectionV2Full.publishers.size() == comicCollection.publishers.size()
		comicCollectionV2Full.characters.size() == comicCollection.characters.size()
		comicCollectionV2Full.references.size() == comicCollection.references.size()
		comicCollectionV2Full.comics.size() == comicCollection.comics.size()
	}

}
