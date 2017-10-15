package com.cezarykluczynski.stapi.server.element.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.ElementBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ElementFullResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.element.dto.ElementRestBeanParams
import com.cezarykluczynski.stapi.server.element.reader.ElementRestReader

class ElementRestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String NAME = 'NAME'

	private ElementRestReader elementRestReaderMock

	private ElementRestEndpoint elementRestEndpoint

	void setup() {
		elementRestReaderMock = Mock()
		elementRestEndpoint = new ElementRestEndpoint(elementRestReaderMock)
	}

	void "passes get call to ElementRestReader"() {
		given:
		ElementFullResponse elementFullResponse = Mock()

		when:
		ElementFullResponse elementFullResponseOutput = elementRestEndpoint.getElement(UID)

		then:
		1 * elementRestReaderMock.readFull(UID) >> elementFullResponse
		elementFullResponseOutput == elementFullResponse
	}

	void "passes search get call to ElementRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		ElementBaseResponse elementResponse = Mock()

		when:
		ElementBaseResponse elementResponseOutput = elementRestEndpoint.searchElements(pageAwareBeanParams)

		then:
		1 * elementRestReaderMock.readBase(_ as ElementRestBeanParams) >> { ElementRestBeanParams elementRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			elementResponse
		}
		elementResponseOutput == elementResponse
	}

	void "passes search post call to ElementRestReader"() {
		given:
		ElementRestBeanParams elementRestBeanParams = new ElementRestBeanParams(name: NAME)
		ElementBaseResponse elementResponse = Mock()

		when:
		ElementBaseResponse elementResponseOutput = elementRestEndpoint.searchElements(elementRestBeanParams)

		then:
		1 * elementRestReaderMock.readBase(elementRestBeanParams as ElementRestBeanParams) >> { ElementRestBeanParams params ->
			assert params.name == NAME
			elementResponse
		}
		elementResponseOutput == elementResponse
	}

}
