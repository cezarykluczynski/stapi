package com.cezarykluczynski.stapi.server.comic_collection.mapper

import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionFull
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionFullRequest
import com.cezarykluczynski.stapi.model.comic_collection.dto.ComicCollectionRequestDTO
import com.cezarykluczynski.stapi.model.comic_collection.entity.ComicCollection
import org.mapstruct.factory.Mappers

class ComicCollectionFullSoapMapperTest extends AbstractComicCollectionMapperTest {

	private ComicCollectionFullSoapMapper comicCollectionFullSoapMapper

	void setup() {
		comicCollectionFullSoapMapper = Mappers.getMapper(ComicCollectionFullSoapMapper)
	}

	void "maps SOAP ComicCollectionFullRequest to ComicCollectionBaseRequestDTO"() {
		given:
		ComicCollectionFullRequest comicCollectionFullRequest = new ComicCollectionFullRequest(uid: UID)

		when:
		ComicCollectionRequestDTO comicCollectionRequestDTO = comicCollectionFullSoapMapper.mapFull comicCollectionFullRequest

		then:
		comicCollectionRequestDTO.uid == UID
	}

	void "maps DB entity to full SOAP entity"() {
		given:
		ComicCollection comicCollection = createComicCollection()

		when:
		ComicCollectionFull comicCollectionFull = comicCollectionFullSoapMapper.mapFull(comicCollection)

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
		comicCollectionFull.yearFrom.toInteger() == YEAR_FROM
		comicCollectionFull.yearTo.toInteger() == YEAR_TO
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

}
