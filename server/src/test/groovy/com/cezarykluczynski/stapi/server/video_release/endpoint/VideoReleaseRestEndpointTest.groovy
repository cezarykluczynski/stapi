package com.cezarykluczynski.stapi.server.video_release.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.VideoReleaseBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.VideoReleaseFullResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.video_release.dto.VideoReleaseRestBeanParams
import com.cezarykluczynski.stapi.server.video_release.reader.VideoReleaseRestReader

class VideoReleaseRestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String TITLE = 'TITLE'

	private VideoReleaseRestReader videoReleaseRestReaderMock

	private VideoReleaseRestEndpoint videoReleaseRestEndpoint

	void setup() {
		videoReleaseRestReaderMock = Mock()
		videoReleaseRestEndpoint = new VideoReleaseRestEndpoint(videoReleaseRestReaderMock)
	}

	void "passes get call to VideoReleaseRestReader"() {
		given:
		VideoReleaseFullResponse videoReleaseFullResponse = Mock()

		when:
		VideoReleaseFullResponse videoReleaseFullResponseOutput = videoReleaseRestEndpoint.getVideoRelease(UID)

		then:
		1 * videoReleaseRestReaderMock.readFull(UID) >> videoReleaseFullResponse
		videoReleaseFullResponseOutput == videoReleaseFullResponse
	}

	void "passes search get call to VideoReleaseRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		VideoReleaseBaseResponse videoReleaseResponse = Mock()

		when:
		VideoReleaseBaseResponse videoReleaseResponseOutput = videoReleaseRestEndpoint.searchVideoRelease(pageAwareBeanParams)

		then:
		1 * videoReleaseRestReaderMock.readBase(_ as VideoReleaseRestBeanParams) >> { VideoReleaseRestBeanParams videoReleaseRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			videoReleaseResponse
		}
		videoReleaseResponseOutput == videoReleaseResponse
	}

	void "passes search post call to VideoReleaseRestReader"() {
		given:
		VideoReleaseRestBeanParams videoReleaseRestBeanParams = new VideoReleaseRestBeanParams(title: TITLE)
		VideoReleaseBaseResponse videoReleaseResponse = Mock()

		when:
		VideoReleaseBaseResponse videoReleaseResponseOutput = videoReleaseRestEndpoint.searchVideoRelease(videoReleaseRestBeanParams)

		then:
		1 * videoReleaseRestReaderMock.readBase(videoReleaseRestBeanParams as VideoReleaseRestBeanParams) >> { VideoReleaseRestBeanParams params ->
			assert params.title == TITLE
			videoReleaseResponse
		}
		videoReleaseResponseOutput == videoReleaseResponse
	}

}
