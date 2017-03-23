package com.cezarykluczynski.stapi.server.comicCollection.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicCollectionBase
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicCollectionBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicCollectionFull
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicCollectionFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.model.comicCollection.entity.ComicCollection
import com.cezarykluczynski.stapi.server.comicCollection.dto.ComicCollectionRestBeanParams
import com.cezarykluczynski.stapi.server.comicCollection.mapper.ComicCollectionBaseRestMapper
import com.cezarykluczynski.stapi.server.comicCollection.mapper.ComicCollectionFullRestMapper
import com.cezarykluczynski.stapi.server.comicCollection.query.ComicCollectionRestQuery
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class ComicCollectionRestReaderTest extends Specification {

	private static final String GUID = 'GUID'

	private ComicCollectionRestQuery comicCollectionRestQueryBuilderMock

	private ComicCollectionBaseRestMapper comicCollectionBaseRestMapperMock

	private ComicCollectionFullRestMapper comicCollectionFullRestMapperMock

	private PageMapper pageMapperMock

	private ComicCollectionRestReader comicCollectionRestReader

	void setup() {
		comicCollectionRestQueryBuilderMock = Mock(ComicCollectionRestQuery)
		comicCollectionBaseRestMapperMock = Mock(ComicCollectionBaseRestMapper)
		comicCollectionFullRestMapperMock = Mock(ComicCollectionFullRestMapper)
		pageMapperMock = Mock(PageMapper)
		comicCollectionRestReader = new ComicCollectionRestReader(comicCollectionRestQueryBuilderMock, comicCollectionBaseRestMapperMock,
				comicCollectionFullRestMapperMock, pageMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		ComicCollectionRestBeanParams comicCollectionRestBeanParams = Mock(ComicCollectionRestBeanParams)
		List<ComicCollectionBase> restComicCollectionList = Lists.newArrayList(Mock(ComicCollectionBase))
		List<ComicCollection> comicCollectionList = Lists.newArrayList(Mock(ComicCollection))
		Page<ComicCollection> comicCollectionPage = Mock(Page)
		ResponsePage responsePage = Mock(ResponsePage)

		when:
		ComicCollectionBaseResponse comicCollectionResponseOutput = comicCollectionRestReader.readBase(comicCollectionRestBeanParams)

		then:
		1 * comicCollectionRestQueryBuilderMock.query(comicCollectionRestBeanParams) >> comicCollectionPage
		1 * pageMapperMock.fromPageToRestResponsePage(comicCollectionPage) >> responsePage
		1 * comicCollectionPage.content >> comicCollectionList
		1 * comicCollectionBaseRestMapperMock.mapBase(comicCollectionList) >> restComicCollectionList
		0 * _
		comicCollectionResponseOutput.comicCollections == restComicCollectionList
		comicCollectionResponseOutput.page == responsePage
	}

	void "passed GUID to queryBuilder, then to mapper, and returns result"() {
		given:
		ComicCollectionFull comicCollectionFull = Mock(ComicCollectionFull)
		ComicCollection comicCollection = Mock(ComicCollection)
		List<ComicCollection> comicCollectionList = Lists.newArrayList(comicCollection)
		Page<ComicCollection> comicCollectionPage = Mock(Page)

		when:
		ComicCollectionFullResponse comicCollectionResponseOutput = comicCollectionRestReader.readFull(GUID)

		then:
		1 * comicCollectionRestQueryBuilderMock.query(_ as ComicCollectionRestBeanParams) >> {
				ComicCollectionRestBeanParams comicCollectionRestBeanParams ->
			assert comicCollectionRestBeanParams.guid == GUID
			comicCollectionPage
		}
		1 * comicCollectionPage.content >> comicCollectionList
		1 * comicCollectionFullRestMapperMock.mapFull(comicCollection) >> comicCollectionFull
		0 * _
		comicCollectionResponseOutput.comicCollection == comicCollectionFull
	}

}
