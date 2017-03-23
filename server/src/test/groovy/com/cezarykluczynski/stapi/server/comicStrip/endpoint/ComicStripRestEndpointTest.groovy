package com.cezarykluczynski.stapi.server.comicStrip.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicStripBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicStripFullResponse
import com.cezarykluczynski.stapi.server.comicStrip.dto.ComicStripRestBeanParams
import com.cezarykluczynski.stapi.server.comicStrip.reader.ComicStripRestReader
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest

class ComicStripRestEndpointTest extends AbstractRestEndpointTest {

	private static final String GUID = 'GUID'
	private static final String TITLE = 'NAME'

	private ComicStripRestReader comicStripRestReaderMock

	private ComicStripRestEndpoint comicStripRestEndpoint

	void setup() {
		comicStripRestReaderMock = Mock(ComicStripRestReader)
		comicStripRestEndpoint = new ComicStripRestEndpoint(comicStripRestReaderMock)
	}

	void "passes get call to ComicStripRestReader"() {
		given:
		ComicStripFullResponse comicStripFullResponse = Mock(ComicStripFullResponse)

		when:
		ComicStripFullResponse comicStripFullResponseOutput = comicStripRestEndpoint.getComicStrip(GUID)

		then:
		1 * comicStripRestReaderMock.readFull(GUID) >> comicStripFullResponse
		comicStripFullResponseOutput == comicStripFullResponse
	}

	void "passes search get call to ComicStripRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock(PageSortBeanParams)
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		ComicStripBaseResponse comicStripResponse = Mock(ComicStripBaseResponse)

		when:
		ComicStripBaseResponse comicStripResponseOutput = comicStripRestEndpoint.searchComicStrip(pageAwareBeanParams)

		then:
		1 * comicStripRestReaderMock.readBase(_ as ComicStripRestBeanParams) >> { ComicStripRestBeanParams comicStripRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			comicStripResponse
		}
		comicStripResponseOutput == comicStripResponse
	}

	void "passes search post call to ComicStripRestReader"() {
		given:
		ComicStripRestBeanParams comicStripRestBeanParams = new ComicStripRestBeanParams(title: TITLE)
		ComicStripBaseResponse comicStripResponse = Mock(ComicStripBaseResponse)

		when:
		ComicStripBaseResponse comicStripResponseOutput = comicStripRestEndpoint.searchComicStrip(comicStripRestBeanParams)

		then:
		1 * comicStripRestReaderMock.readBase(comicStripRestBeanParams as ComicStripRestBeanParams) >> { ComicStripRestBeanParams params ->
			assert params.title == TITLE
			comicStripResponse
		}
		comicStripResponseOutput == comicStripResponse
	}

}
