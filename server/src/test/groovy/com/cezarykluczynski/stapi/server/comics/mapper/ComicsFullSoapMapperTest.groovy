package com.cezarykluczynski.stapi.server.comics.mapper

import com.cezarykluczynski.stapi.client.v1.soap.ComicsFull
import com.cezarykluczynski.stapi.client.v1.soap.ComicsFullRequest
import com.cezarykluczynski.stapi.model.comics.entity.Comics
import com.cezarykluczynski.stapi.model.comics.dto.ComicsRequestDTO
import org.mapstruct.factory.Mappers

class ComicsFullSoapMapperTest extends AbstractComicsMapperTest {

	private ComicsFullSoapMapper comicsFullSoapMapper

	void setup() {
		comicsFullSoapMapper = Mappers.getMapper(ComicsFullSoapMapper)
	}

	void "maps SOAP ComicsFullRequest to ComicsBaseRequestDTO"() {
		given:
		ComicsFullRequest comicsFullRequest = new ComicsFullRequest(uid: UID)

		when:
		ComicsRequestDTO comicsRequestDTO = comicsFullSoapMapper.mapFull comicsFullRequest

		then:
		comicsRequestDTO.uid == UID
	}

	void "maps DB entity to full SOAP entity"() {
		given:
		Comics comics = createComics()

		when:
		ComicsFull comicsFull = comicsFullSoapMapper.mapFull(comics)

		then:
		comicsFull.uid == UID
		comicsFull.title == TITLE
		comicsFull.publishedYear == PUBLISHED_YEAR
		comicsFull.publishedMonth == PUBLISHED_MONTH
		comicsFull.publishedDay == PUBLISHED_DAY
		comicsFull.coverYear == COVER_YEAR
		comicsFull.coverMonth == COVER_MONTH
		comicsFull.coverDay == COVER_DAY
		comicsFull.numberOfPages == NUMBER_OF_PAGES
		comicsFull.stardateFrom == STARDATE_FROM
		comicsFull.stardateTo == STARDATE_TO
		comicsFull.yearFrom.toInteger() == YEAR_FROM
		comicsFull.yearTo.toInteger() == YEAR_TO
		comicsFull.photonovel == PHOTONOVEL
		comicsFull.adaptation == ADAPTATION
		comicsFull.comicSeries.size() == comics.comicSeries.size()
		comicsFull.writers.size() == comics.writers.size()
		comicsFull.artists.size() == comics.artists.size()
		comicsFull.editors.size() == comics.editors.size()
		comicsFull.staff.size() == comics.staff.size()
		comicsFull.publishers.size() == comics.publishers.size()
		comicsFull.characters.size() == comics.characters.size()
		comicsFull.references.size() == comics.references.size()
		comicsFull.comicCollections.size() == comics.comicCollections.size()
	}

}
