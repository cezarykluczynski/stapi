package com.cezarykluczynski.stapi.server.bookCollection.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.BookCollectionBase
import com.cezarykluczynski.stapi.client.v1.rest.model.BookCollectionBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.BookCollectionFull
import com.cezarykluczynski.stapi.client.v1.rest.model.BookCollectionFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.model.bookCollection.entity.BookCollection
import com.cezarykluczynski.stapi.server.bookCollection.dto.BookCollectionRestBeanParams
import com.cezarykluczynski.stapi.server.bookCollection.mapper.BookCollectionBaseRestMapper
import com.cezarykluczynski.stapi.server.bookCollection.mapper.BookCollectionFullRestMapper
import com.cezarykluczynski.stapi.server.bookCollection.query.BookCollectionRestQuery
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class BookCollectionRestReaderTest extends Specification {

	private static final String UID = 'UID'

	private BookCollectionRestQuery bookCollectionRestQueryBuilderMock

	private BookCollectionBaseRestMapper bookCollectionBaseRestMapperMock

	private BookCollectionFullRestMapper bookCollectionFullRestMapperMock

	private PageMapper pageMapperMock

	private BookCollectionRestReader bookCollectionRestReader

	void setup() {
		bookCollectionRestQueryBuilderMock = Mock()
		bookCollectionBaseRestMapperMock = Mock()
		bookCollectionFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		bookCollectionRestReader = new BookCollectionRestReader(bookCollectionRestQueryBuilderMock, bookCollectionBaseRestMapperMock,
				bookCollectionFullRestMapperMock, pageMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		BookCollectionBase bookCollectionBase = Mock()
		BookCollection bookCollection = Mock()
		BookCollectionRestBeanParams bookCollectionRestBeanParams = Mock()
		List<BookCollectionBase> restBookCollectionList = Lists.newArrayList(bookCollectionBase)
		List<BookCollection> bookCollectionList = Lists.newArrayList(bookCollection)
		Page<BookCollection> bookCollectionPage = Mock()
		ResponsePage responsePage = Mock()

		when:
		BookCollectionBaseResponse bookCollectionResponseOutput = bookCollectionRestReader.readBase(bookCollectionRestBeanParams)

		then:
		1 * bookCollectionRestQueryBuilderMock.query(bookCollectionRestBeanParams) >> bookCollectionPage
		1 * pageMapperMock.fromPageToRestResponsePage(bookCollectionPage) >> responsePage
		1 * bookCollectionPage.content >> bookCollectionList
		1 * bookCollectionBaseRestMapperMock.mapBase(bookCollectionList) >> restBookCollectionList
		0 * _
		bookCollectionResponseOutput.bookCollections == restBookCollectionList
		bookCollectionResponseOutput.page == responsePage
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		BookCollectionFull bookCollectionFull = Mock()
		BookCollection bookCollection = Mock()
		List<BookCollection> bookCollectionList = Lists.newArrayList(bookCollection)
		Page<BookCollection> bookCollectionPage = Mock()

		when:
		BookCollectionFullResponse bookCollectionResponseOutput = bookCollectionRestReader.readFull(UID)

		then:
		1 * bookCollectionRestQueryBuilderMock.query(_ as BookCollectionRestBeanParams) >> {
				BookCollectionRestBeanParams bookCollectionRestBeanParams ->
			assert bookCollectionRestBeanParams.uid == UID
			bookCollectionPage
		}
		1 * bookCollectionPage.content >> bookCollectionList
		1 * bookCollectionFullRestMapperMock.mapFull(bookCollection) >> bookCollectionFull
		0 * _
		bookCollectionResponseOutput.bookCollection == bookCollectionFull
	}

	void "requires UID in full request"() {
		when:
		bookCollectionRestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
