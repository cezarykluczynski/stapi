package com.cezarykluczynski.stapi.client.api.rest

import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT
import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.api.dto.VideoReleaseSearchCriteria
import com.cezarykluczynski.stapi.client.api.dto.VideoReleaseV2SearchCriteria
import com.cezarykluczynski.stapi.client.rest.api.VideoReleaseApi
import com.cezarykluczynski.stapi.client.rest.model.VideoReleaseBaseResponse
import com.cezarykluczynski.stapi.client.rest.model.VideoReleaseFullResponse
import com.cezarykluczynski.stapi.client.rest.model.VideoReleaseV2BaseResponse
import com.cezarykluczynski.stapi.client.rest.model.VideoReleaseV2FullResponse
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
		1 * videoReleaseApiMock.v1RestVideoReleaseGet(UID) >> videoReleaseFullResponse
		0 * _
		videoReleaseFullResponse == videoReleaseFullResponseOutput
	}

	void "gets single entity (V2)"() {
		given:
		VideoReleaseV2FullResponse videoReleaseV2FullResponse = Mock()

		when:
		VideoReleaseV2FullResponse videoReleaseV2FullResponseOutput = videoRelease.getV2(UID)

		then:
		1 * videoReleaseApiMock.v2RestVideoReleaseGet(UID) >> videoReleaseV2FullResponse
		0 * _
		videoReleaseV2FullResponse == videoReleaseV2FullResponseOutput
	}

	void "searches entities"() {
		given:
		VideoReleaseBaseResponse videoReleaseBaseResponse = Mock()

		when:
		VideoReleaseBaseResponse videoReleaseBaseResponseOutput = videoRelease.search(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, TITLE, YEAR_FROM, YEAR_TO,
				RUN_TIME_FROM, RUN_TIME_TO)

		then:
		1 * videoReleaseApiMock.v1RestVideoReleaseSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, TITLE, YEAR_FROM, YEAR_TO, RUN_TIME_FROM,
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
		1 * videoReleaseApiMock.v1RestVideoReleaseSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, TITLE, YEAR_FROM, YEAR_TO, RUN_TIME_FROM,
				RUN_TIME_TO) >> videoReleaseBaseResponse
		0 * _
		videoReleaseBaseResponse == videoReleaseBaseResponseOutput
	}

	void "searches entities with criteria (V2)"() {
		given:
		VideoReleaseV2BaseResponse videoReleaseV2BaseResponse = Mock()
		VideoReleaseV2SearchCriteria videoReleaseSearchCriteria = new VideoReleaseV2SearchCriteria(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				title: TITLE,
				yearFrom: YEAR_FROM,
				yearTo: YEAR_TO,
				runTimeFrom: RUN_TIME_FROM,
				runTimeTo: RUN_TIME_TO,
				documentary: DOCUMENTARY,
				specialFeatures: SPECIAL_FEATURES,
		)
		videoReleaseSearchCriteria.sort.addAll(SORT)

		when:
		VideoReleaseV2BaseResponse videoReleaseBaseResponseOutput = videoRelease.searchV2(videoReleaseSearchCriteria)

		then:
		1 * videoReleaseApiMock.v2RestVideoReleaseSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, TITLE, YEAR_FROM, YEAR_TO, RUN_TIME_FROM,
				RUN_TIME_TO, DOCUMENTARY, SPECIAL_FEATURES) >> videoReleaseV2BaseResponse
		0 * _
		videoReleaseV2BaseResponse == videoReleaseBaseResponseOutput
	}

}
