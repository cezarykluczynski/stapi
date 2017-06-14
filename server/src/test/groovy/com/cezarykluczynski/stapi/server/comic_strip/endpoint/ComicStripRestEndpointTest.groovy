package com.cezarykluczynski.stapi.server.comic_strip.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicStripBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicStripFullResponse
import com.cezarykluczynski.stapi.server.comic_strip.dto.ComicStripRestBeanParams
import com.cezarykluczynski.stapi.server.comic_strip.reader.ComicStripRestReader
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest

class ComicStripRestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String TITLE = 'NAME'

	private ComicStripRestReader comicStripRestReaderMock

	private ComicStripRestEndpoint comicStripRestEndpoint

	void setup() {
		comicStripRestReaderMock = Mock()
		comicStripRestEndpoint = new ComicStripRestEndpoint(comicStripRestReaderMock)
	}

	void "passes get call to ComicStripRestReader"() {
		given:
		ComicStripFullResponse comicStripFullResponse = Mock()

		when:
		ComicStripFullResponse comicStripFullResponseOutput = comicStripRestEndpoint.getComicStrip(UID)

		then:
		1 * comicStripRestReaderMock.readFull(UID) >> comicStripFullResponse
		comicStripFullResponseOutput == comicStripFullResponse
	}

	void "passes search get call to ComicStripRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		ComicStripBaseResponse comicStripResponse = Mock()

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
		ComicStripBaseResponse comicStripResponse = Mock()

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
