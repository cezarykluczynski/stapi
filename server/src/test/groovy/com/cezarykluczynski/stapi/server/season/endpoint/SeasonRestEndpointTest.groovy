package com.cezarykluczynski.stapi.server.season.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.SeasonBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.SeasonFullResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.season.dto.SeasonRestBeanParams
import com.cezarykluczynski.stapi.server.season.reader.SeasonRestReader

class SeasonRestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String TITLE = 'TITLE'

	private SeasonRestReader seasonRestReaderMock

	private SeasonRestEndpoint seasonRestEndpoint

	void setup() {
		seasonRestReaderMock = Mock()
		seasonRestEndpoint = new SeasonRestEndpoint(seasonRestReaderMock)
	}

	void "passes get call to SeasonRestReader"() {
		given:
		SeasonFullResponse seasonFullResponse = Mock()

		when:
		SeasonFullResponse seasonFullResponseOutput = seasonRestEndpoint.getSeason(UID)

		then:
		1 * seasonRestReaderMock.readFull(UID) >> seasonFullResponse
		seasonFullResponseOutput == seasonFullResponse
	}

	void "passes search get call to SeasonRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		SeasonBaseResponse seasonResponse = Mock()

		when:
		SeasonBaseResponse seasonResponseOutput = seasonRestEndpoint.searchSeasons(pageAwareBeanParams)

		then:
		1 * seasonRestReaderMock.readBase(_ as SeasonRestBeanParams) >> { SeasonRestBeanParams seasonRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			seasonResponse
		}
		seasonResponseOutput == seasonResponse
	}

	void "passes search post call to SeasonRestReader"() {
		given:
		SeasonRestBeanParams seasonRestBeanParams = new SeasonRestBeanParams(title: TITLE)
		SeasonBaseResponse seasonResponse = Mock()

		when:
		SeasonBaseResponse seasonResponseOutput = seasonRestEndpoint.searchSeasons(seasonRestBeanParams)

		then:
		1 * seasonRestReaderMock.readBase(seasonRestBeanParams as SeasonRestBeanParams) >> { SeasonRestBeanParams params ->
			assert params.title == TITLE
			seasonResponse
		}
		seasonResponseOutput == seasonResponse
	}

}
