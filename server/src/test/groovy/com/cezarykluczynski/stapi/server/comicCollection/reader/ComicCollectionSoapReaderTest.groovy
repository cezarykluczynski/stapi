package com.cezarykluczynski.stapi.server.comicCollection.reader

import com.cezarykluczynski.stapi.client.v1.soap.ComicCollection as SOAPComicCollection
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionResponse
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.model.comicCollection.entity.ComicCollection as DBComicCollection
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.comicCollection.mapper.ComicCollectionSoapMapper
import com.cezarykluczynski.stapi.server.comicCollection.query.ComicCollectionSoapQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class ComicCollectionSoapReaderTest extends Specification {

	private static final String GUID = 'GUID'

	private ComicCollectionSoapQuery comicCollectionSoapQueryBuilderMock

	private ComicCollectionSoapMapper comicCollectionSoapMapperMock

	private PageMapper pageMapperMock

	private ComicCollectionSoapReader comicCollectionSoapReader

	void setup() {
		comicCollectionSoapQueryBuilderMock = Mock(ComicCollectionSoapQuery)
		comicCollectionSoapMapperMock = Mock(ComicCollectionSoapMapper)
		pageMapperMock = Mock(PageMapper)
		comicCollectionSoapReader = new ComicCollectionSoapReader(comicCollectionSoapQueryBuilderMock, comicCollectionSoapMapperMock, pageMapperMock)
	}

	void "gets database entities and puts them into ComicCollectionResponse"() {
		given:
		List<DBComicCollection> dbComicCollectionList = Lists.newArrayList()
		Page<DBComicCollection> dbComicCollectionPage = Mock(Page)
		dbComicCollectionPage.content >> dbComicCollectionList
		List<SOAPComicCollection> soapComicCollectionList = Lists.newArrayList(new SOAPComicCollection(guid: GUID))
		ComicCollectionRequest comicCollectionRequest = Mock(ComicCollectionRequest)
		ResponsePage responsePage = Mock(ResponsePage)

		when:
		ComicCollectionResponse comicCollectionResponse = comicCollectionSoapReader.readBase(comicCollectionRequest)

		then:
		1 * comicCollectionSoapQueryBuilderMock.query(comicCollectionRequest) >> dbComicCollectionPage
		1 * pageMapperMock.fromPageToSoapResponsePage(dbComicCollectionPage) >> responsePage
		1 * comicCollectionSoapMapperMock.map(dbComicCollectionList) >> soapComicCollectionList
		comicCollectionResponse.comicCollections[0].guid == GUID
		comicCollectionResponse.page == responsePage
	}

}
