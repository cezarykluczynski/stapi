package com.cezarykluczynski.stapi.server.comic_collection.reader

import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionBase
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionFull
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.RequestSort
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.client.v1.soap.ResponseSort
import com.cezarykluczynski.stapi.model.comic_collection.entity.ComicCollection
import com.cezarykluczynski.stapi.server.comic_collection.mapper.ComicCollectionBaseSoapMapper
import com.cezarykluczynski.stapi.server.comic_collection.mapper.ComicCollectionFullSoapMapper
import com.cezarykluczynski.stapi.server.comic_collection.query.ComicCollectionSoapQuery
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class ComicCollectionSoapReaderTest extends Specification {

	private static final String UID = 'UID'

	private ComicCollectionSoapQuery comicCollectionSoapQueryBuilderMock

	private ComicCollectionBaseSoapMapper comicCollectionBaseSoapMapperMock

	private ComicCollectionFullSoapMapper comicCollectionFullSoapMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private ComicCollectionSoapReader comicCollectionSoapReader

	void setup() {
		comicCollectionSoapQueryBuilderMock = Mock()
		comicCollectionBaseSoapMapperMock = Mock()
		comicCollectionFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		comicCollectionSoapReader = new ComicCollectionSoapReader(comicCollectionSoapQueryBuilderMock, comicCollectionBaseSoapMapperMock,
				comicCollectionFullSoapMapperMock, pageMapperMock, sortMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<ComicCollection> comicCollectionList = Lists.newArrayList()
		Page<ComicCollection> comicCollectionPage = Mock()
		List<ComicCollectionBase> soapComicCollectionList = Lists.newArrayList(new ComicCollectionBase(uid: UID))
		ComicCollectionBaseRequest comicCollectionBaseRequest = Mock()
		ResponsePage responsePage = Mock()
		RequestSort requestSort = Mock()
		ResponseSort responseSort = Mock()

		when:
		ComicCollectionBaseResponse comicCollectionResponse = comicCollectionSoapReader.readBase(comicCollectionBaseRequest)

		then:
		1 * comicCollectionSoapQueryBuilderMock.query(comicCollectionBaseRequest) >> comicCollectionPage
		1 * comicCollectionPage.content >> comicCollectionList
		1 * pageMapperMock.fromPageToSoapResponsePage(comicCollectionPage) >> responsePage
		1 * comicCollectionBaseRequest.sort >> requestSort
		1 * sortMapperMock.map(requestSort) >> responseSort
		1 * comicCollectionBaseSoapMapperMock.mapBase(comicCollectionList) >> soapComicCollectionList
		0 * _
		comicCollectionResponse.comicCollections[0].uid == UID
		comicCollectionResponse.page == responsePage
		comicCollectionResponse.sort == responseSort
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		ComicCollectionFull comicCollectionFull = new ComicCollectionFull(uid: UID)
		ComicCollection comicCollection = Mock()
		Page<ComicCollection> comicCollectionPage = Mock()
		ComicCollectionFullRequest comicCollectionFullRequest = new ComicCollectionFullRequest(uid: UID)

		when:
		ComicCollectionFullResponse comicCollectionFullResponse = comicCollectionSoapReader.readFull(comicCollectionFullRequest)

		then:
		1 * comicCollectionSoapQueryBuilderMock.query(comicCollectionFullRequest) >> comicCollectionPage
		1 * comicCollectionPage.content >> Lists.newArrayList(comicCollection)
		1 * comicCollectionFullSoapMapperMock.mapFull(comicCollection) >> comicCollectionFull
		0 * _
		comicCollectionFullResponse.comicCollection.uid == UID
	}

	void "requires UID in full request"() {
		given:
		ComicCollectionFullRequest comicCollectionFullRequest = Mock()

		when:
		comicCollectionSoapReader.readFull(comicCollectionFullRequest)

		then:
		thrown(MissingUIDException)
	}

}
