package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.VideoReleaseApi
import com.cezarykluczynski.stapi.client.v1.rest.model.VideoReleaseBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.VideoReleaseFullResponse
import com.cezarykluczynski.stapi.util.AbstractVideoReleaseTest

class VideoReleaseTest extends AbstractVideoReleaseTest {

	private VideoReleaseApi videoReleaseApiMock

	private VideoRelease videoRelease

	void setup() {
		videoReleaseApiMock = Mock()
		videoRelease = new VideoRelease(videoReleaseApiMock, API_KEY)
	}

	void "gets single entity"() {
		given:
		VideoReleaseFullResponse videoReleaseFullResponse = Mock()

		when:
		VideoReleaseFullResponse videoReleaseFullResponseOutput = videoRelease.get(UID)

		then:
		1 * videoReleaseApiMock.videoReleaseGet(UID, API_KEY) >> videoReleaseFullResponse
		0 * _
		videoReleaseFullResponse == videoReleaseFullResponseOutput
	}

	void "searches entities"() {
		given:
		VideoReleaseBaseResponse videoReleaseBaseResponse = Mock()

		when:
		VideoReleaseBaseResponse videoReleaseBaseResponseOutput = videoRelease.search(PAGE_NUMBER, PAGE_SIZE, SORT, TITLE, YEAR_FROM, YEAR_TO,
				RUN_TIME_FROM, RUN_TIME_TO)

		then:
		1 * videoReleaseApiMock.videoReleaseSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, API_KEY, TITLE, YEAR_FROM, YEAR_TO, RUN_TIME_FROM,
				RUN_TIME_TO) >> videoReleaseBaseResponse
		0 * _
		videoReleaseBaseResponse == videoReleaseBaseResponseOutput
	}

}
