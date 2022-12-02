package com.cezarykluczynski.stapi.server.element.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.ElementV2BaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ElementV2FullResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.element.dto.ElementV2RestBeanParams
import com.cezarykluczynski.stapi.server.element.reader.ElementV2RestReader

class ElementV2RestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String NAME = 'NAME'

	private ElementV2RestReader elementV2RestReaderMock

	private ElementV2RestEndpoint elementV2RestEndpoint

	void setup() {
		elementV2RestReaderMock = Mock()
		elementV2RestEndpoint = new ElementV2RestEndpoint(elementV2RestReaderMock)
	}

	void "passes get call to ElementV2RestReader"() {
		given:
		ElementV2FullResponse elementV2FullResponse = Mock()

		when:
		ElementV2FullResponse elementV2FullResponseOutput = elementV2RestEndpoint.getElement(UID)

		then:
		1 * elementV2RestReaderMock.readFull(UID) >> elementV2FullResponse
		elementV2FullResponseOutput == elementV2FullResponse
	}

	void "passes search get call to ElementV2RestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		ElementV2BaseResponse elementV2Response = Mock()

		when:
		ElementV2BaseResponse elementV2ResponseOutput = elementV2RestEndpoint.searchElements(pageAwareBeanParams)

		then:
		1 * elementV2RestReaderMock.readBase(_ as ElementV2RestBeanParams) >> { ElementV2RestBeanParams elementV2RestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			elementV2Response
		}
		elementV2ResponseOutput == elementV2Response
	}

	void "passes search post call to ElementV2RestReader"() {
		given:
		ElementV2RestBeanParams elementV2RestBeanParams = new ElementV2RestBeanParams(name: NAME)
		ElementV2BaseResponse elementV2Response = Mock()

		when:
		ElementV2BaseResponse elementV2ResponseOutput = elementV2RestEndpoint.searchElements(elementV2RestBeanParams)

		then:
		1 * elementV2RestReaderMock.readBase(elementV2RestBeanParams as ElementV2RestBeanParams) >> { ElementV2RestBeanParams params ->
			assert params.name == NAME
			elementV2Response
		}
		elementV2ResponseOutput == elementV2Response
	}

}
