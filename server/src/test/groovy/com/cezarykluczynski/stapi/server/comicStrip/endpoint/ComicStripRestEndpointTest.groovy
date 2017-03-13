package com.cezarykluczynski.stapi.server.comicStrip.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicStripResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.comicStrip.dto.ComicStripRestBeanParams
import com.cezarykluczynski.stapi.server.comicStrip.reader.ComicStripRestReader

class ComicStripRestEndpointTest extends AbstractRestEndpointTest {

	private ComicStripRestReader comicStripRestReaderMock

	private ComicStripRestEndpoint comicStripRestEndpoint

	void setup() {
		comicStripRestReaderMock = Mock(ComicStripRestReader)
		comicStripRestEndpoint = new ComicStripRestEndpoint(comicStripRestReaderMock)
	}

	void "passes get call to ComicStripRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock(PageSortBeanParams)
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		ComicStripResponse comicStripResponse = Mock(ComicStripResponse)

		when:
		ComicStripResponse comicStripResponseOutput = comicStripRestEndpoint.getComicStrip(pageAwareBeanParams)

		then:
		1 * comicStripRestReaderMock.readBase(_ as ComicStripRestBeanParams) >> { ComicStripRestBeanParams comicStripRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			comicStripResponse
		}
		comicStripResponseOutput == comicStripResponse
	}

	void "passes post call to ComicStripRestReader"() {
		given:
		ComicStripRestBeanParams comicStripRestBeanParams = new ComicStripRestBeanParams()
		ComicStripResponse comicStripResponse = Mock(ComicStripResponse)

		when:
		ComicStripResponse comicStripResponseOutput = comicStripRestEndpoint.searchComicStrip(comicStripRestBeanParams)

		then:
		1 * comicStripRestReaderMock.readBase(comicStripRestBeanParams as ComicStripRestBeanParams) >> { ComicStripRestBeanParams params ->
			comicStripResponse
		}
		comicStripResponseOutput == comicStripResponse
	}

}
