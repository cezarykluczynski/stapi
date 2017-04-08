package com.cezarykluczynski.stapi.server.episode.query

import com.cezarykluczynski.stapi.model.episode.dto.EpisodeRequestDTO
import com.cezarykluczynski.stapi.model.episode.repository.EpisodeRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.episode.dto.EpisodeRestBeanParams
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeBaseRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class EpisodeRestQueryTest extends Specification {

	private EpisodeBaseRestMapper episodeBaseRestMapperMock

	private PageMapper pageMapperMock

	private EpisodeRepository episodeRepositoryMock

	private EpisodeRestQuery episodeRestQuery

	void setup() {
		episodeBaseRestMapperMock = Mock()
		pageMapperMock = Mock()
		episodeRepositoryMock = Mock()
		episodeRestQuery = new EpisodeRestQuery(episodeBaseRestMapperMock, pageMapperMock, episodeRepositoryMock)
	}

	void "maps EpisodeRestBeanParams to EpisodeRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		EpisodeRestBeanParams episodeRestBeanParams = Mock()
		EpisodeRequestDTO episodeRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = episodeRestQuery.query(episodeRestBeanParams)

		then:
		1 * episodeBaseRestMapperMock.mapBase(episodeRestBeanParams) >> episodeRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(episodeRestBeanParams) >> pageRequest
		1 * episodeRepositoryMock.findMatching(episodeRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
