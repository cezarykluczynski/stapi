package com.cezarykluczynski.stapi.server.comic_collection.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicCollectionBase
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicCollectionBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicCollectionFull
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicCollectionFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSort
import com.cezarykluczynski.stapi.model.comic_collection.entity.ComicCollection
import com.cezarykluczynski.stapi.server.comic_collection.dto.ComicCollectionRestBeanParams
import com.cezarykluczynski.stapi.server.comic_collection.mapper.ComicCollectionBaseRestMapper
import com.cezarykluczynski.stapi.server.comic_collection.mapper.ComicCollectionFullRestMapper
import com.cezarykluczynski.stapi.server.comic_collection.query.ComicCollectionRestQuery
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class ComicCollectionRestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private ComicCollectionRestQuery comicCollectionRestQueryBuilderMock

	private ComicCollectionBaseRestMapper comicCollectionBaseRestMapperMock

	private ComicCollectionFullRestMapper comicCollectionFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private ComicCollectionRestReader comicCollectionRestReader

	void setup() {
		comicCollectionRestQueryBuilderMock = Mock()
		comicCollectionBaseRestMapperMock = Mock()
		comicCollectionFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		comicCollectionRestReader = new ComicCollectionRestReader(comicCollectionRestQueryBuilderMock, comicCollectionBaseRestMapperMock,
				comicCollectionFullRestMapperMock, pageMapperMock, sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		ComicCollectionBase comicCollectionBase = Mock()
		ComicCollection comicCollection = Mock()
		ComicCollectionRestBeanParams comicCollectionRestBeanParams = Mock()
		List<ComicCollectionBase> comicCollectionBaseList = Lists.newArrayList(comicCollectionBase)
		List<ComicCollection> comicCollectionList = Lists.newArrayList(comicCollection)
		Page<ComicCollection> comicCollectionPage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		ComicCollectionBaseResponse comicCollectionResponseOutput = comicCollectionRestReader.readBase(comicCollectionRestBeanParams)

		then:
		1 * comicCollectionRestQueryBuilderMock.query(comicCollectionRestBeanParams) >> comicCollectionPage
		1 * pageMapperMock.fromPageToRestResponsePage(comicCollectionPage) >> responsePage
		1 * comicCollectionRestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * comicCollectionPage.content >> comicCollectionList
		1 * comicCollectionBaseRestMapperMock.mapBase(comicCollectionList) >> comicCollectionBaseList
		0 * _
		comicCollectionResponseOutput.comicCollections == comicCollectionBaseList
		comicCollectionResponseOutput.page == responsePage
		comicCollectionResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		ComicCollectionFull comicCollectionFull = Mock()
		ComicCollection comicCollection = Mock()
		List<ComicCollection> comicCollectionList = Lists.newArrayList(comicCollection)
		Page<ComicCollection> comicCollectionPage = Mock()

		when:
		ComicCollectionFullResponse comicCollectionResponseOutput = comicCollectionRestReader.readFull(UID)

		then:
		1 * comicCollectionRestQueryBuilderMock.query(_ as ComicCollectionRestBeanParams) >> {
				ComicCollectionRestBeanParams comicCollectionRestBeanParams ->
			assert comicCollectionRestBeanParams.uid == UID
			comicCollectionPage
		}
		1 * comicCollectionPage.content >> comicCollectionList
		1 * comicCollectionFullRestMapperMock.mapFull(comicCollection) >> comicCollectionFull
		0 * _
		comicCollectionResponseOutput.comicCollection == comicCollectionFull
	}

	void "requires UID in full request"() {
		when:
		comicCollectionRestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
