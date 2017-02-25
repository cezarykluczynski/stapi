package com.cezarykluczynski.stapi.server.comicCollection.mapper

import com.cezarykluczynski.stapi.client.v1.soap.ComicCollection as SOAPComicCollection
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionRequest
import com.cezarykluczynski.stapi.client.v1.soap.FloatRange
import com.cezarykluczynski.stapi.client.v1.soap.IntegerRange
import com.cezarykluczynski.stapi.model.comicCollection.dto.ComicCollectionRequestDTO
import com.cezarykluczynski.stapi.model.comicCollection.entity.ComicCollection as DBComicCollection
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class ComicCollectionSoapMapperTest extends AbstractComicCollectionMapperTest {

	private ComicCollectionSoapMapper comicCollectionSoapMapper

	void setup() {
		comicCollectionSoapMapper = Mappers.getMapper(ComicCollectionSoapMapper)
	}

	void "maps SOAP ComicCollectionRequest to ComicCollectionRequestDTO"() {
		given:
		ComicCollectionRequest comicCollectionRequest = new ComicCollectionRequest(
				guid: GUID,
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
		ComicCollectionRequestDTO comicCollectionRequestDTO = comicCollectionSoapMapper.map comicCollectionRequest

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

	void "maps DB entity to SOAP entity"() {
		given:
		DBComicCollection dBComicCollection = createComicCollection()

		when:
		SOAPComicCollection soapComicCollection = comicCollectionSoapMapper.map(Lists.newArrayList(dBComicCollection))[0]

		then:
		soapComicCollection.guid == GUID
		soapComicCollection.title == TITLE
		soapComicCollection.publishedYear == PUBLISHED_YEAR
		soapComicCollection.publishedMonth == PUBLISHED_MONTH
		soapComicCollection.publishedDay == PUBLISHED_DAY
		soapComicCollection.coverYear == COVER_YEAR
		soapComicCollection.coverMonth == COVER_MONTH
		soapComicCollection.coverDay == COVER_DAY
		soapComicCollection.numberOfPages == NUMBER_OF_PAGES
		soapComicCollection.stardateFrom == STARDATE_FROM
		soapComicCollection.stardateTo == STARDATE_TO
		soapComicCollection.yearFrom.toInteger() == YEAR_FROM
		soapComicCollection.yearTo.toInteger() == YEAR_TO
		soapComicCollection.photonovel == PHOTONOVEL
		soapComicCollection.comicSeriesHeaders.size() == dBComicCollection.comicSeries.size()
		soapComicCollection.writerHeaders.size() == dBComicCollection.writers.size()
		soapComicCollection.artistHeaders.size() == dBComicCollection.artists.size()
		soapComicCollection.editorHeaders.size() == dBComicCollection.editors.size()
		soapComicCollection.staffHeaders.size() == dBComicCollection.staff.size()
		soapComicCollection.publisherHeaders.size() == dBComicCollection.publishers.size()
		soapComicCollection.characterHeaders.size() == dBComicCollection.characters.size()
		soapComicCollection.references.size() == dBComicCollection.references.size()
		soapComicCollection.comicsHeaders.size() == dBComicCollection.comics.size()
	}

}
