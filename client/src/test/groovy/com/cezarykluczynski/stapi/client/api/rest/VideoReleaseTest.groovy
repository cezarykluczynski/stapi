package com.cezarykluczynski.stapi.client.api.rest

import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT
import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.api.dto.VideoReleaseSearchCriteria
import com.cezarykluczynski.stapi.client.v1.rest.api.VideoReleaseApi
import com.cezarykluczynski.stapi.client.v1.rest.model.VideoReleaseBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.VideoReleaseFullResponse
import com.cezarykluczynski.stapi.util.AbstractVideoReleaseTest

class VideoReleaseTest extends AbstractVideoReleaseTest {

	private VideoReleaseApi videoReleaseApiMock

	private VideoRelease videoRelease

	void setup() {
		videoReleaseApiMock = Mock()
		videoRelease = new VideoRelease(videoReleaseApiMock)
	}

	void "gets single entity"() {
		given:
		VideoReleaseFullResponse videoReleaseFullResponse = Mock()

		when:
		VideoReleaseFullResponse videoReleaseFullResponseOutput = videoRelease.get(UID)

		then:
		1 * videoReleaseApiMock.v1RestVideoReleaseGet(UID, null) >> videoReleaseFullResponse
		0 * _
		videoReleaseFullResponse == videoReleaseFullResponseOutput
	}

	void "searches entities"() {
		given:
		VideoReleaseBaseResponse videoReleaseBaseResponse = Mock()

		when:
		VideoReleaseBaseResponse videoReleaseBaseResponseOutput = videoRelease.search(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, TITLE, YEAR_FROM, YEAR_TO,
				RUN_TIME_FROM, RUN_TIME_TO)

		then:
		1 * videoReleaseApiMock.v1RestVideoReleaseSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, null, TITLE, YEAR_FROM, YEAR_TO, RUN_TIME_FROM,
				RUN_TIME_TO) >> videoReleaseBaseResponse
		0 * _
		videoReleaseBaseResponse == videoReleaseBaseResponseOutput
	}

	void "searches entities with criteria"() {
		given:
		VideoReleaseBaseResponse videoReleaseBaseResponse = Mock()
		VideoReleaseSearchCriteria videoReleaseSearchCriteria = new VideoReleaseSearchCriteria(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				title: TITLE,
				yearFrom: YEAR_FROM,
				yearTo: YEAR_TO,
				runTimeFrom: RUN_TIME_FROM,
				runTimeTo: RUN_TIME_TO
		)
		videoReleaseSearchCriteria.sort.addAll(SORT)

		when:
		VideoReleaseBaseResponse videoReleaseBaseResponseOutput = videoRelease.search(videoReleaseSearchCriteria)

		then:
		1 * videoReleaseApiMock.v1RestVideoReleaseSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, null, TITLE, YEAR_FROM, YEAR_TO, RUN_TIME_FROM,
				RUN_TIME_TO) >> videoReleaseBaseResponse
		0 * _
		videoReleaseBaseResponse == videoReleaseBaseResponseOutput
	}

}
