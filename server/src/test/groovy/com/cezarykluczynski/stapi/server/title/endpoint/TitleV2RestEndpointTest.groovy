package com.cezarykluczynski.stapi.server.title.endpoint

import com.cezarykluczynski.stapi.client.rest.model.TitleV2BaseResponse
import com.cezarykluczynski.stapi.client.rest.model.TitleV2FullResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.title.dto.TitleV2RestBeanParams
import com.cezarykluczynski.stapi.server.title.reader.TitleV2RestReader

class TitleV2RestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String NAME = 'NAME'

	private TitleV2RestReader titleV2RestReaderMock

	private TitleV2RestEndpoint titleV2RestEndpoint

	void setup() {
		titleV2RestReaderMock = Mock()
		titleV2RestEndpoint = new TitleV2RestEndpoint(titleV2RestReaderMock)
	}

	void "passes get call to TitleRestReader"() {
		given:
		TitleV2FullResponse titleV2FullResponse = Mock()

		when:
		TitleV2FullResponse titleV2FullResponseOutput = titleV2RestEndpoint.getTitle(UID)

		then:
		1 * titleV2RestReaderMock.readFull(UID) >> titleV2FullResponse
		titleV2FullResponseOutput == titleV2FullResponse
	}

	void "passes search get call to TitleRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		TitleV2BaseResponse titleV2Response = Mock()

		when:
		TitleV2BaseResponse titleV2ResponseOutput = titleV2RestEndpoint
				.searchTitle(pageAwareBeanParams)

		then:
		1 * titleV2RestReaderMock.readBase(_ as TitleV2RestBeanParams) >> { TitleV2RestBeanParams titleRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			titleV2Response
		}
		titleV2ResponseOutput == titleV2Response
	}

	void "passes search post call to TitleRestReader"() {
		given:
		TitleV2RestBeanParams titleV2RestBeanParams = new TitleV2RestBeanParams(name: NAME)
		TitleV2BaseResponse titleV2Response = Mock()

		when:
		TitleV2BaseResponse titleV2ResponseOutput = titleV2RestEndpoint.searchTitle(titleV2RestBeanParams)

		then:
		1 * titleV2RestReaderMock.readBase(titleV2RestBeanParams as TitleV2RestBeanParams) >> {
			TitleV2RestBeanParams params ->
				assert params.name == NAME
				titleV2Response
		}
		titleV2ResponseOutput == titleV2Response
	}

}
