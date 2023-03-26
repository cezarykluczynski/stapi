package com.cezarykluczynski.stapi.client.rest.facade

import static AbstractFacadeTest.SORT
import static AbstractFacadeTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.rest.api.VideoReleaseApi
import com.cezarykluczynski.stapi.client.rest.model.VideoReleaseV2BaseResponse
import com.cezarykluczynski.stapi.client.rest.model.VideoReleaseV2FullResponse
import com.cezarykluczynski.stapi.client.rest.model.VideoReleaseV2SearchCriteria
import com.cezarykluczynski.stapi.util.AbstractVideoReleaseTest

class VideoReleaseTest extends AbstractVideoReleaseTest {

	private VideoReleaseApi videoReleaseApiMock

	private VideoRelease videoRelease

	void setup() {
		videoReleaseApiMock = Mock()
		videoRelease = new VideoRelease(videoReleaseApiMock)
	}

	void "gets single entity (V2)"() {
		given:
		VideoReleaseV2FullResponse videoReleaseV2FullResponse = Mock()

		when:
		VideoReleaseV2FullResponse videoReleaseV2FullResponseOutput = videoRelease.getV2(UID)

		then:
		1 * videoReleaseApiMock.v2GetVideoRelease(UID) >> videoReleaseV2FullResponse
		0 * _
		videoReleaseV2FullResponse == videoReleaseV2FullResponseOutput
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
		videoReleaseSearchCriteria.sort = SORT

		when:
		VideoReleaseV2BaseResponse videoReleaseBaseResponseOutput = videoRelease.searchV2(videoReleaseSearchCriteria)

		then:
		1 * videoReleaseApiMock.v2SearchVideoReleases(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, TITLE, YEAR_FROM, YEAR_TO, RUN_TIME_FROM,
				RUN_TIME_TO, DOCUMENTARY, SPECIAL_FEATURES) >> videoReleaseV2BaseResponse
		0 * _
		videoReleaseV2BaseResponse == videoReleaseBaseResponseOutput
	}

}
