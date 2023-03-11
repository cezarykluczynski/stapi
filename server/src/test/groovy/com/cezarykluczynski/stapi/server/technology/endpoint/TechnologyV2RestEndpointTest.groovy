package com.cezarykluczynski.stapi.server.technology.endpoint

import com.cezarykluczynski.stapi.client.rest.model.TechnologyV2BaseResponse
import com.cezarykluczynski.stapi.client.rest.model.TechnologyV2FullResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.technology.dto.TechnologyV2RestBeanParams
import com.cezarykluczynski.stapi.server.technology.reader.TechnologyV2RestReader

class TechnologyV2RestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String NAME = 'NAME'

	private TechnologyV2RestReader technologyV2RestReaderMock

	private TechnologyV2RestEndpoint technologyV2RestEndpoint

	void setup() {
		technologyV2RestReaderMock = Mock()
		technologyV2RestEndpoint = new TechnologyV2RestEndpoint(technologyV2RestReaderMock)
	}

	void "passes get call to TechnologyRestReader"() {
		given:
		TechnologyV2FullResponse technologyV2FullResponse = Mock()

		when:
		TechnologyV2FullResponse technologyV2FullResponseOutput = technologyV2RestEndpoint.getTechnology(UID)

		then:
		1 * technologyV2RestReaderMock.readFull(UID) >> technologyV2FullResponse
		technologyV2FullResponseOutput == technologyV2FullResponse
	}

	void "passes search get call to TechnologyRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		TechnologyV2BaseResponse technologyV2Response = Mock()

		when:
		TechnologyV2BaseResponse technologyV2ResponseOutput = technologyV2RestEndpoint
				.searchTechnology(pageAwareBeanParams)

		then:
		1 * technologyV2RestReaderMock.readBase(_ as TechnologyV2RestBeanParams) >> { TechnologyV2RestBeanParams technologyRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			technologyV2Response
		}
		technologyV2ResponseOutput == technologyV2Response
	}

	void "passes search post call to TechnologyRestReader"() {
		given:
		TechnologyV2RestBeanParams technologyV2RestBeanParams = new TechnologyV2RestBeanParams(name: NAME)
		TechnologyV2BaseResponse technologyV2Response = Mock()

		when:
		TechnologyV2BaseResponse technologyV2ResponseOutput = technologyV2RestEndpoint.searchTechnology(technologyV2RestBeanParams)

		then:
		1 * technologyV2RestReaderMock.readBase(technologyV2RestBeanParams as TechnologyV2RestBeanParams) >> {
			TechnologyV2RestBeanParams params ->
				assert params.name == NAME
				technologyV2Response
		}
		technologyV2ResponseOutput == technologyV2Response
	}

}
