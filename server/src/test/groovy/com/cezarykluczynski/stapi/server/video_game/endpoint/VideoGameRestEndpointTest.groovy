package com.cezarykluczynski.stapi.server.video_game.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.VideoGameBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.VideoGameFullResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.video_game.dto.VideoGameRestBeanParams
import com.cezarykluczynski.stapi.server.video_game.reader.VideoGameRestReader

class VideoGameRestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String TITLE = 'TITLE'

	private VideoGameRestReader videoGameRestReaderMock

	private VideoGameRestEndpoint videoGameRestEndpoint

	void setup() {
		videoGameRestReaderMock = Mock()
		videoGameRestEndpoint = new VideoGameRestEndpoint(videoGameRestReaderMock)
	}

	void "passes get call to VideoGameRestReader"() {
		given:
		VideoGameFullResponse videoGameFullResponse = Mock()

		when:
		VideoGameFullResponse videoGameFullResponseOutput = videoGameRestEndpoint.getVideoGame(UID)

		then:
		1 * videoGameRestReaderMock.readFull(UID) >> videoGameFullResponse
		videoGameFullResponseOutput == videoGameFullResponse
	}

	void "passes search get call to VideoGameRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		VideoGameBaseResponse videoGameResponse = Mock()

		when:
		VideoGameBaseResponse videoGameResponseOutput = videoGameRestEndpoint.searchVideoGame(pageAwareBeanParams)

		then:
		1 * videoGameRestReaderMock.readBase(_ as VideoGameRestBeanParams) >> { VideoGameRestBeanParams videoGameRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			videoGameResponse
		}
		videoGameResponseOutput == videoGameResponse
	}

	void "passes search post call to VideoGameRestReader"() {
		given:
		VideoGameRestBeanParams videoGameRestBeanParams = new VideoGameRestBeanParams(title: TITLE)
		VideoGameBaseResponse videoGameResponse = Mock()

		when:
		VideoGameBaseResponse videoGameResponseOutput = videoGameRestEndpoint.searchVideoGame(videoGameRestBeanParams)

		then:
		1 * videoGameRestReaderMock.readBase(videoGameRestBeanParams as VideoGameRestBeanParams) >> { VideoGameRestBeanParams params ->
			assert params.title == TITLE
			videoGameResponse
		}
		videoGameResponseOutput == videoGameResponse
	}

}
