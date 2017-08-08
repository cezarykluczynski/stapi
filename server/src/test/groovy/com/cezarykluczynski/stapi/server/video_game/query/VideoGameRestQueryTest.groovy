package com.cezarykluczynski.stapi.server.video_game.query

import com.cezarykluczynski.stapi.model.video_game.dto.VideoGameRequestDTO
import com.cezarykluczynski.stapi.model.video_game.repository.VideoGameRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.video_game.dto.VideoGameRestBeanParams
import com.cezarykluczynski.stapi.server.video_game.mapper.VideoGameBaseRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class VideoGameRestQueryTest extends Specification {

	private VideoGameBaseRestMapper videoGameBaseRestMapperMock

	private PageMapper pageMapperMock

	private VideoGameRepository videoGameRepositoryMock

	private VideoGameRestQuery videoGameRestQuery

	void setup() {
		videoGameBaseRestMapperMock = Mock()
		pageMapperMock = Mock()
		videoGameRepositoryMock = Mock()
		videoGameRestQuery = new VideoGameRestQuery(videoGameBaseRestMapperMock, pageMapperMock, videoGameRepositoryMock)
	}

	void "maps VideoGameRestBeanParams to VideoGameRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		VideoGameRestBeanParams videoGameRestBeanParams = Mock()
		VideoGameRequestDTO videoGameRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = videoGameRestQuery.query(videoGameRestBeanParams)

		then:
		1 * videoGameBaseRestMapperMock.mapBase(videoGameRestBeanParams) >> videoGameRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(videoGameRestBeanParams) >> pageRequest
		1 * videoGameRepositoryMock.findMatching(videoGameRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
