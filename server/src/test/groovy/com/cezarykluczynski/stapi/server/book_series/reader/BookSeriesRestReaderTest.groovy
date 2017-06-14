package com.cezarykluczynski.stapi.server.book_series.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.BookSeriesBase
import com.cezarykluczynski.stapi.client.v1.rest.model.BookSeriesBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.BookSeriesFull
import com.cezarykluczynski.stapi.client.v1.rest.model.BookSeriesFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSort
import com.cezarykluczynski.stapi.model.book_series.entity.BookSeries
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.book_series.dto.BookSeriesRestBeanParams
import com.cezarykluczynski.stapi.server.book_series.mapper.BookSeriesBaseRestMapper
import com.cezarykluczynski.stapi.server.book_series.mapper.BookSeriesFullRestMapper
import com.cezarykluczynski.stapi.server.book_series.query.BookSeriesRestQuery
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class BookSeriesRestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private BookSeriesRestQuery bookSeriesRestQueryBuilderMock

	private BookSeriesBaseRestMapper bookSeriesBaseRestMapperMock

	private BookSeriesFullRestMapper bookSeriesFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private BookSeriesRestReader bookSeriesRestReader

	void setup() {
		bookSeriesRestQueryBuilderMock = Mock()
		bookSeriesBaseRestMapperMock = Mock()
		bookSeriesFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		bookSeriesRestReader = new BookSeriesRestReader(bookSeriesRestQueryBuilderMock, bookSeriesBaseRestMapperMock, bookSeriesFullRestMapperMock,
				pageMapperMock, sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		BookSeriesBase bookSeriesBase = Mock()
		BookSeries bookSeries = Mock()
		BookSeriesRestBeanParams bookSeriesRestBeanParams = Mock()
		List<BookSeriesBase> restBookSeriesList = Lists.newArrayList(bookSeriesBase)
		List<BookSeries> bookSeriesList = Lists.newArrayList(bookSeries)
		Page<BookSeries> bookSeriesPage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		BookSeriesBaseResponse bookSeriesResponseOutput = bookSeriesRestReader.readBase(bookSeriesRestBeanParams)

		then:
		1 * bookSeriesRestQueryBuilderMock.query(bookSeriesRestBeanParams) >> bookSeriesPage
		1 * pageMapperMock.fromPageToRestResponsePage(bookSeriesPage) >> responsePage
		1 * bookSeriesRestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * bookSeriesPage.content >> bookSeriesList
		1 * bookSeriesBaseRestMapperMock.mapBase(bookSeriesList) >> restBookSeriesList
		0 * _
		bookSeriesResponseOutput.bookSeries == restBookSeriesList
		bookSeriesResponseOutput.page == responsePage
		bookSeriesResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		BookSeriesFull bookSeriesFull = Mock()
		BookSeries bookSeries = Mock()
		List<BookSeries> bookSeriesList = Lists.newArrayList(bookSeries)
		Page<BookSeries> bookSeriesPage = Mock()

		when:
		BookSeriesFullResponse bookSeriesResponseOutput = bookSeriesRestReader.readFull(UID)

		then:
		1 * bookSeriesRestQueryBuilderMock.query(_ as BookSeriesRestBeanParams) >> { BookSeriesRestBeanParams bookSeriesRestBeanParams ->
			assert bookSeriesRestBeanParams.uid == UID
			bookSeriesPage
		}
		1 * bookSeriesPage.content >> bookSeriesList
		1 * bookSeriesFullRestMapperMock.mapFull(bookSeries) >> bookSeriesFull
		0 * _
		bookSeriesResponseOutput.bookSeries == bookSeriesFull
	}

	void "requires UID in full request"() {
		when:
		bookSeriesRestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
