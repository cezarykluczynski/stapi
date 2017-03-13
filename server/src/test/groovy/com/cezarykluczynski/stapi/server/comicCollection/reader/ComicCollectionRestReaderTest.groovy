package com.cezarykluczynski.stapi.server.comicCollection.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicCollection as RESTComicCollection
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicCollectionResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.model.comicCollection.entity.ComicCollection
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.comicCollection.dto.ComicCollectionRestBeanParams
import com.cezarykluczynski.stapi.server.comicCollection.mapper.ComicCollectionRestMapper
import com.cezarykluczynski.stapi.server.comicCollection.query.ComicCollectionRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class ComicCollectionRestReaderTest extends Specification {

	private static final String GUID = 'GUID'

	private ComicCollectionRestQuery comicCollectionRestQueryBuilderMock

	private ComicCollectionRestMapper comicCollectionRestMapperMock

	private PageMapper pageMapperMock

	private ComicCollectionRestReader comicCollectionRestReader

	void setup() {
		comicCollectionRestQueryBuilderMock = Mock(ComicCollectionRestQuery)
		comicCollectionRestMapperMock = Mock(ComicCollectionRestMapper)
		pageMapperMock = Mock(PageMapper)
		comicCollectionRestReader = new ComicCollectionRestReader(comicCollectionRestQueryBuilderMock, comicCollectionRestMapperMock, pageMapperMock)
	}

	void "gets database entities and puts them into ComicCollectionResponse"() {
		given:
		List<ComicCollection> dbComicCollectionList = Lists.newArrayList()
		Page<ComicCollection> dbComicCollectionPage = Mock(Page)
		dbComicCollectionPage.content >> dbComicCollectionList
		List<RESTComicCollection> soapComicCollectionList = Lists.newArrayList(new RESTComicCollection(guid: GUID))
		ComicCollectionRestBeanParams seriesRestBeanParams = Mock(ComicCollectionRestBeanParams)
		ResponsePage responsePage = Mock(ResponsePage)

		when:
		ComicCollectionResponse comicCollectionResponse = comicCollectionRestReader.readBase(seriesRestBeanParams)

		then:
		1 * comicCollectionRestQueryBuilderMock.query(seriesRestBeanParams) >> dbComicCollectionPage
		1 * pageMapperMock.fromPageToRestResponsePage(dbComicCollectionPage) >> responsePage
		1 * comicCollectionRestMapperMock.map(dbComicCollectionList) >> soapComicCollectionList
		comicCollectionResponse.comicCollections[0].guid == GUID
		comicCollectionResponse.page == responsePage
	}

}
