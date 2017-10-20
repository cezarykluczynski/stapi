package com.cezarykluczynski.stapi.server.technology.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.TechnologyBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.TechnologyFullResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.technology.dto.TechnologyRestBeanParams
import com.cezarykluczynski.stapi.server.technology.reader.TechnologyRestReader

class TechnologyRestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String NAME = 'NAME'

	private TechnologyRestReader technologyRestReaderMock

	private TechnologyRestEndpoint technologyRestEndpoint

	void setup() {
		technologyRestReaderMock = Mock()
		technologyRestEndpoint = new TechnologyRestEndpoint(technologyRestReaderMock)
	}

	void "passes get call to TechnologyRestReader"() {
		given:
		TechnologyFullResponse technologyFullResponse = Mock()

		when:
		TechnologyFullResponse technologyFullResponseOutput = technologyRestEndpoint.getTechnology(UID)

		then:
		1 * technologyRestReaderMock.readFull(UID) >> technologyFullResponse
		technologyFullResponseOutput == technologyFullResponse
	}

	void "passes search get call to TechnologyRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		TechnologyBaseResponse technologyResponse = Mock()

		when:
		TechnologyBaseResponse technologyResponseOutput = technologyRestEndpoint.searchTechnologys(pageAwareBeanParams)

		then:
		1 * technologyRestReaderMock.readBase(_ as TechnologyRestBeanParams) >> { TechnologyRestBeanParams technologyRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			technologyResponse
		}
		technologyResponseOutput == technologyResponse
	}

	void "passes search post call to TechnologyRestReader"() {
		given:
		TechnologyRestBeanParams technologyRestBeanParams = new TechnologyRestBeanParams(name: NAME)
		TechnologyBaseResponse technologyResponse = Mock()

		when:
		TechnologyBaseResponse technologyResponseOutput = technologyRestEndpoint.searchTechnologys(technologyRestBeanParams)

		then:
		1 * technologyRestReaderMock.readBase(technologyRestBeanParams as TechnologyRestBeanParams) >> { TechnologyRestBeanParams params ->
			assert params.name == NAME
			technologyResponse
		}
		technologyResponseOutput == technologyResponse
	}

}
