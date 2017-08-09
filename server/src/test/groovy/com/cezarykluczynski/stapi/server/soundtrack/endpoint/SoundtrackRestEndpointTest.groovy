package com.cezarykluczynski.stapi.server.soundtrack.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.SoundtrackBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.SoundtrackFullResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.soundtrack.dto.SoundtrackRestBeanParams
import com.cezarykluczynski.stapi.server.soundtrack.reader.SoundtrackRestReader

class SoundtrackRestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String TITLE = 'TITLE'

	private SoundtrackRestReader soundtrackRestReaderMock

	private SoundtrackRestEndpoint soundtrackRestEndpoint

	void setup() {
		soundtrackRestReaderMock = Mock()
		soundtrackRestEndpoint = new SoundtrackRestEndpoint(soundtrackRestReaderMock)
	}

	void "passes get call to SoundtrackRestReader"() {
		given:
		SoundtrackFullResponse soundtrackFullResponse = Mock()

		when:
		SoundtrackFullResponse soundtrackFullResponseOutput = soundtrackRestEndpoint.getSoundtrack(UID)

		then:
		1 * soundtrackRestReaderMock.readFull(UID) >> soundtrackFullResponse
		soundtrackFullResponseOutput == soundtrackFullResponse
	}

	void "passes search get call to SoundtrackRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		SoundtrackBaseResponse soundtrackResponse = Mock()

		when:
		SoundtrackBaseResponse soundtrackResponseOutput = soundtrackRestEndpoint.searchSoundtrack(pageAwareBeanParams)

		then:
		1 * soundtrackRestReaderMock.readBase(_ as SoundtrackRestBeanParams) >> { SoundtrackRestBeanParams soundtrackRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			soundtrackResponse
		}
		soundtrackResponseOutput == soundtrackResponse
	}

	void "passes search post call to SoundtrackRestReader"() {
		given:
		SoundtrackRestBeanParams soundtrackRestBeanParams = new SoundtrackRestBeanParams(title: TITLE)
		SoundtrackBaseResponse soundtrackResponse = Mock()

		when:
		SoundtrackBaseResponse soundtrackResponseOutput = soundtrackRestEndpoint.searchSoundtrack(soundtrackRestBeanParams)

		then:
		1 * soundtrackRestReaderMock.readBase(soundtrackRestBeanParams as SoundtrackRestBeanParams) >> { SoundtrackRestBeanParams params ->
			assert params.title == TITLE
			soundtrackResponse
		}
		soundtrackResponseOutput == soundtrackResponse
	}

}
