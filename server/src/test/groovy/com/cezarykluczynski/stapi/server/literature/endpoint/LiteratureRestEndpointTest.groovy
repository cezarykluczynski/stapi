package com.cezarykluczynski.stapi.server.literature.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.LiteratureBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.LiteratureFullResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.literature.dto.LiteratureRestBeanParams
import com.cezarykluczynski.stapi.server.literature.reader.LiteratureRestReader

class LiteratureRestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String NAME = 'NAME'

	private LiteratureRestReader literatureRestReaderMock

	private LiteratureRestEndpoint literatureRestEndpoint

	void setup() {
		literatureRestReaderMock = Mock()
		literatureRestEndpoint = new LiteratureRestEndpoint(literatureRestReaderMock)
	}

	void "passes get call to LiteratureRestReader"() {
		given:
		LiteratureFullResponse literatureFullResponse = Mock()

		when:
		LiteratureFullResponse literatureFullResponseOutput = literatureRestEndpoint.getLiterature(UID)

		then:
		1 * literatureRestReaderMock.readFull(UID) >> literatureFullResponse
		literatureFullResponseOutput == literatureFullResponse
	}

	void "passes search get call to LiteratureRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		LiteratureBaseResponse literatureResponse = Mock()

		when:
		LiteratureBaseResponse literatureResponseOutput = literatureRestEndpoint.searchLiterature(pageAwareBeanParams)

		then:
		1 * literatureRestReaderMock.readBase(_ as LiteratureRestBeanParams) >> { LiteratureRestBeanParams literatureRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			literatureResponse
		}
		literatureResponseOutput == literatureResponse
	}

	void "passes search post call to LiteratureRestReader"() {
		given:
		LiteratureRestBeanParams literatureRestBeanParams = new LiteratureRestBeanParams(title: NAME)
		LiteratureBaseResponse literatureResponse = Mock()

		when:
		LiteratureBaseResponse literatureResponseOutput = literatureRestEndpoint.searchLiterature(literatureRestBeanParams)

		then:
		1 * literatureRestReaderMock.readBase(literatureRestBeanParams as LiteratureRestBeanParams) >> { LiteratureRestBeanParams params ->
			assert params.title == NAME
			literatureResponse
		}
		literatureResponseOutput == literatureResponse
	}

}
