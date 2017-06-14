package com.cezarykluczynski.stapi.server.book_collection.reader

import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionBase
import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionFull
import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.RequestSort
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.client.v1.soap.ResponseSort
import com.cezarykluczynski.stapi.model.book_collection.entity.BookCollection
import com.cezarykluczynski.stapi.server.book_collection.mapper.BookCollectionBaseSoapMapper
import com.cezarykluczynski.stapi.server.book_collection.mapper.BookCollectionFullSoapMapper
import com.cezarykluczynski.stapi.server.book_collection.query.BookCollectionSoapQuery
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class BookCollectionSoapReaderTest extends Specification {

	private static final String UID = 'UID'

	private BookCollectionSoapQuery bookCollectionSoapQueryBuilderMock

	private BookCollectionBaseSoapMapper bookCollectionBaseSoapMapperMock

	private BookCollectionFullSoapMapper bookCollectionFullSoapMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private BookCollectionSoapReader bookCollectionSoapReader

	void setup() {
		bookCollectionSoapQueryBuilderMock = Mock()
		bookCollectionBaseSoapMapperMock = Mock()
		bookCollectionFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		bookCollectionSoapReader = new BookCollectionSoapReader(bookCollectionSoapQueryBuilderMock, bookCollectionBaseSoapMapperMock,
				bookCollectionFullSoapMapperMock, pageMapperMock, sortMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<BookCollection> bookCollectionList = Lists.newArrayList()
		Page<BookCollection> bookCollectionPage = Mock()
		List<BookCollectionBase> soapBookCollectionList = Lists.newArrayList(new BookCollectionBase(uid: UID))
		BookCollectionBaseRequest bookCollectionBaseRequest = Mock()
		ResponsePage responsePage = Mock()
		RequestSort requestSort = Mock()
		ResponseSort responseSort = Mock()

		when:
		BookCollectionBaseResponse bookCollectionResponse = bookCollectionSoapReader.readBase(bookCollectionBaseRequest)

		then:
		1 * bookCollectionSoapQueryBuilderMock.query(bookCollectionBaseRequest) >> bookCollectionPage
		1 * bookCollectionPage.content >> bookCollectionList
		1 * pageMapperMock.fromPageToSoapResponsePage(bookCollectionPage) >> responsePage
		1 * bookCollectionBaseRequest.sort >> requestSort
		1 * sortMapperMock.map(requestSort) >> responseSort
		1 * bookCollectionBaseSoapMapperMock.mapBase(bookCollectionList) >> soapBookCollectionList
		0 * _
		bookCollectionResponse.bookCollections[0].uid == UID
		bookCollectionResponse.page == responsePage
		bookCollectionResponse.sort == responseSort
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		BookCollectionFull bookCollectionFull = new BookCollectionFull(uid: UID)
		BookCollection bookCollection = Mock()
		Page<BookCollection> bookCollectionPage = Mock()
		BookCollectionFullRequest bookCollectionFullRequest = new BookCollectionFullRequest(uid: UID)

		when:
		BookCollectionFullResponse bookCollectionFullResponse = bookCollectionSoapReader.readFull(bookCollectionFullRequest)

		then:
		1 * bookCollectionSoapQueryBuilderMock.query(bookCollectionFullRequest) >> bookCollectionPage
		1 * bookCollectionPage.content >> Lists.newArrayList(bookCollection)
		1 * bookCollectionFullSoapMapperMock.mapFull(bookCollection) >> bookCollectionFull
		0 * _
		bookCollectionFullResponse.bookCollection.uid == UID
	}

	void "requires UID in full request"() {
		given:
		BookCollectionFullRequest bookCollectionFullRequest = Mock()

		when:
		bookCollectionSoapReader.readFull(bookCollectionFullRequest)

		then:
		thrown(MissingUIDException)
	}

}
