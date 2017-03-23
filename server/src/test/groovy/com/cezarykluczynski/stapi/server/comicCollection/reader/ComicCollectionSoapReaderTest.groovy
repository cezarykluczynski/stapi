package com.cezarykluczynski.stapi.server.comicCollection.reader

import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionBase
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionFull
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.model.comicCollection.entity.ComicCollection
import com.cezarykluczynski.stapi.server.comicCollection.mapper.ComicCollectionBaseSoapMapper
import com.cezarykluczynski.stapi.server.comicCollection.mapper.ComicCollectionFullSoapMapper
import com.cezarykluczynski.stapi.server.comicCollection.query.ComicCollectionSoapQuery
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class ComicCollectionSoapReaderTest extends Specification {

	private static final String GUID = 'GUID'

	private ComicCollectionSoapQuery comicCollectionSoapQueryBuilderMock

	private ComicCollectionBaseSoapMapper comicCollectionBaseSoapMapperMock

	private ComicCollectionFullSoapMapper comicCollectionFullSoapMapperMock

	private PageMapper pageMapperMock

	private ComicCollectionSoapReader comicCollectionSoapReader

	void setup() {
		comicCollectionSoapQueryBuilderMock = Mock(ComicCollectionSoapQuery)
		comicCollectionBaseSoapMapperMock = Mock(ComicCollectionBaseSoapMapper)
		comicCollectionFullSoapMapperMock = Mock(ComicCollectionFullSoapMapper)
		pageMapperMock = Mock(PageMapper)
		comicCollectionSoapReader = new ComicCollectionSoapReader(comicCollectionSoapQueryBuilderMock, comicCollectionBaseSoapMapperMock,
				comicCollectionFullSoapMapperMock, pageMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<ComicCollection> comicCollectionList = Lists.newArrayList()
		Page<ComicCollection> comicCollectionPage = Mock(Page)
		List<ComicCollectionBase> soapComicCollectionList = Lists.newArrayList(new ComicCollectionBase(guid: GUID))
		ComicCollectionBaseRequest comicCollectionBaseRequest = Mock(ComicCollectionBaseRequest)
		ResponsePage responsePage = Mock(ResponsePage)

		when:
		ComicCollectionBaseResponse comicCollectionResponse = comicCollectionSoapReader.readBase(comicCollectionBaseRequest)

		then:
		1 * comicCollectionSoapQueryBuilderMock.query(comicCollectionBaseRequest) >> comicCollectionPage
		1 * comicCollectionPage.content >> comicCollectionList
		1 * pageMapperMock.fromPageToSoapResponsePage(comicCollectionPage) >> responsePage
		1 * comicCollectionBaseSoapMapperMock.mapBase(comicCollectionList) >> soapComicCollectionList
		comicCollectionResponse.comicCollections[0].guid == GUID
		comicCollectionResponse.page == responsePage
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		ComicCollectionFull comicCollectionFull = new ComicCollectionFull(guid: GUID)
		ComicCollection comicCollection = Mock(ComicCollection)
		Page<ComicCollection> comicCollectionPage = Mock(Page)
		ComicCollectionFullRequest comicCollectionFullRequest = Mock(ComicCollectionFullRequest)

		when:
		ComicCollectionFullResponse comicCollectionFullResponse = comicCollectionSoapReader.readFull(comicCollectionFullRequest)

		then:
		1 * comicCollectionSoapQueryBuilderMock.query(comicCollectionFullRequest) >> comicCollectionPage
		1 * comicCollectionPage.content >> Lists.newArrayList(comicCollection)
		1 * comicCollectionFullSoapMapperMock.mapFull(comicCollection) >> comicCollectionFull
		comicCollectionFullResponse.comicCollection.guid == GUID
	}

}
