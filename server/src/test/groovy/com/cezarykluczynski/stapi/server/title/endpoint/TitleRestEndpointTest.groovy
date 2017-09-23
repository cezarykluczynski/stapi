package com.cezarykluczynski.stapi.server.title.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.TitleBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.TitleFullResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.title.dto.TitleRestBeanParams
import com.cezarykluczynski.stapi.server.title.reader.TitleRestReader

class TitleRestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String NAME = 'NAME'

	private TitleRestReader titleRestReaderMock

	private TitleRestEndpoint titleRestEndpoint

	void setup() {
		titleRestReaderMock = Mock()
		titleRestEndpoint = new TitleRestEndpoint(titleRestReaderMock)
	}

	void "passes get call to TitleRestReader"() {
		given:
		TitleFullResponse titleFullResponse = Mock()

		when:
		TitleFullResponse titleFullResponseOutput = titleRestEndpoint.getTitle(UID)

		then:
		1 * titleRestReaderMock.readFull(UID) >> titleFullResponse
		titleFullResponseOutput == titleFullResponse
	}

	void "passes search get call to TitleRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		TitleBaseResponse titleResponse = Mock()

		when:
		TitleBaseResponse titleResponseOutput = titleRestEndpoint.searchTitles(pageAwareBeanParams)

		then:
		1 * titleRestReaderMock.readBase(_ as TitleRestBeanParams) >> { TitleRestBeanParams titleRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			titleResponse
		}
		titleResponseOutput == titleResponse
	}

	void "passes search post call to TitleRestReader"() {
		given:
		TitleRestBeanParams titleRestBeanParams = new TitleRestBeanParams(name: NAME)
		TitleBaseResponse titleResponse = Mock()

		when:
		TitleBaseResponse titleResponseOutput = titleRestEndpoint.searchTitles(titleRestBeanParams)

		then:
		1 * titleRestReaderMock.readBase(titleRestBeanParams as TitleRestBeanParams) >> { TitleRestBeanParams params ->
			assert params.name == NAME
			titleResponse
		}
		titleResponseOutput == titleResponse
	}

}
