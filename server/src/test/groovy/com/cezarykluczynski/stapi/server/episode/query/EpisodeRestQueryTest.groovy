package com.cezarykluczynski.stapi.server.episode.query

import com.cezarykluczynski.stapi.model.episode.dto.EpisodeRequestDTO
import com.cezarykluczynski.stapi.model.episode.repository.EpisodeRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.episode.dto.EpisodeRestBeanParams
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class EpisodeRestQueryTest extends Specification {

	private EpisodeRestMapper episodeRestMapperMock

	private PageMapper pageMapperMock

	private EpisodeRepository episodeRepositoryMock

	private EpisodeRestQuery episodeRestQuery

	void setup() {
		episodeRestMapperMock = Mock(EpisodeRestMapper)
		pageMapperMock = Mock(PageMapper)
		episodeRepositoryMock = Mock(EpisodeRepository)
		episodeRestQuery = new EpisodeRestQuery(episodeRestMapperMock, pageMapperMock,
				episodeRepositoryMock)
	}

	void "maps EpisodeRestBeanParams to EpisodeRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock(PageRequest)
		EpisodeRestBeanParams episodeRestBeanParams = Mock(EpisodeRestBeanParams) {

		}
		EpisodeRequestDTO episodeRequestDTO = Mock(EpisodeRequestDTO)
		Page page = Mock(Page)

		when:
		Page pageOutput = episodeRestQuery.query(episodeRestBeanParams)

		then:
		1 * episodeRestMapperMock.mapBase(episodeRestBeanParams) >> episodeRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(episodeRestBeanParams) >> pageRequest
		1 * episodeRepositoryMock.findMatching(episodeRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
