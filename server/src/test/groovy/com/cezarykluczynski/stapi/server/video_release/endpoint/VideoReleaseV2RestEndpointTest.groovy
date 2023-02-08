package com.cezarykluczynski.stapi.server.video_release.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.VideoReleaseV2BaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.VideoReleaseV2FullResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.video_release.dto.VideoReleaseV2RestBeanParams
import com.cezarykluczynski.stapi.server.video_release.reader.VideoReleaseV2RestReader

class VideoReleaseV2RestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String TITLE = 'TITLE'

	private VideoReleaseV2RestReader videoReleaseV2RestReaderMock

	private VideoReleaseV2RestEndpoint videoReleaseV2RestEndpoint

	void setup() {
		videoReleaseV2RestReaderMock = Mock()
		videoReleaseV2RestEndpoint = new VideoReleaseV2RestEndpoint(videoReleaseV2RestReaderMock)
	}

	void "passes get call to VideoReleaseRestReader"() {
		given:
		VideoReleaseV2FullResponse videoReleaseV2FullResponse = Mock()

		when:
		VideoReleaseV2FullResponse videoReleaseV2FullResponseOutput = videoReleaseV2RestEndpoint.getVideoRelease(UID)

		then:
		1 * videoReleaseV2RestReaderMock.readFull(UID) >> videoReleaseV2FullResponse
		videoReleaseV2FullResponseOutput == videoReleaseV2FullResponse
	}

	void "passes search get call to VideoReleaseRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		VideoReleaseV2BaseResponse videoReleaseV2Response = Mock()

		when:
		VideoReleaseV2BaseResponse videoReleaseV2ResponseOutput = videoReleaseV2RestEndpoint.searchVideoRelease(pageAwareBeanParams)

		then:
		1 * videoReleaseV2RestReaderMock.readBase(_ as VideoReleaseV2RestBeanParams) >> { VideoReleaseV2RestBeanParams videoReleaseRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			videoReleaseV2Response
		}
		videoReleaseV2ResponseOutput == videoReleaseV2Response
	}

	void "passes search post call to VideoReleaseRestReader"() {
		given:
		VideoReleaseV2RestBeanParams videoReleaseV2RestBeanParams = new VideoReleaseV2RestBeanParams(title: TITLE)
		VideoReleaseV2BaseResponse videoReleaseV2Response = Mock()

		when:
		VideoReleaseV2BaseResponse videoReleaseV2ResponseOutput = videoReleaseV2RestEndpoint.searchVideoRelease(videoReleaseV2RestBeanParams)

		then:
		1 * videoReleaseV2RestReaderMock.readBase(videoReleaseV2RestBeanParams as VideoReleaseV2RestBeanParams) >> {
				VideoReleaseV2RestBeanParams params ->
			assert params.title == TITLE
			videoReleaseV2Response
		}
		videoReleaseV2ResponseOutput == videoReleaseV2Response
	}

}
