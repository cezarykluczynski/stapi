package com.cezarykluczynski.stapi.server.video_release.query

import com.cezarykluczynski.stapi.model.video_release.dto.VideoReleaseRequestDTO
import com.cezarykluczynski.stapi.model.video_release.repository.VideoReleaseRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.video_release.dto.VideoReleaseRestBeanParams
import com.cezarykluczynski.stapi.server.video_release.mapper.VideoReleaseBaseRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class VideoReleaseRestQueryTest extends Specification {

	private VideoReleaseBaseRestMapper videoReleaseBaseRestMapperMock

	private PageMapper pageMapperMock

	private VideoReleaseRepository videoReleaseRepositoryMock

	private VideoReleaseRestQuery videoReleaseRestQuery

	void setup() {
		videoReleaseBaseRestMapperMock = Mock()
		pageMapperMock = Mock()
		videoReleaseRepositoryMock = Mock()
		videoReleaseRestQuery = new VideoReleaseRestQuery(videoReleaseBaseRestMapperMock, pageMapperMock, videoReleaseRepositoryMock)
	}

	void "maps VideoReleaseRestBeanParams to VideoReleaseRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		VideoReleaseRestBeanParams videoReleaseRestBeanParams = Mock()
		VideoReleaseRequestDTO videoReleaseRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = videoReleaseRestQuery.query(videoReleaseRestBeanParams)

		then:
		1 * videoReleaseBaseRestMapperMock.mapBase(videoReleaseRestBeanParams) >> videoReleaseRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(videoReleaseRestBeanParams) >> pageRequest
		1 * videoReleaseRepositoryMock.findMatching(videoReleaseRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
