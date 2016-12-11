package com.cezarykluczynski.stapi.server.episode.query

import com.cezarykluczynski.stapi.client.v1.soap.EpisodeRequest
import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.model.episode.dto.EpisodeRequestDTO
import com.cezarykluczynski.stapi.model.episode.repository.EpisodeRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeSoapMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class EpisodeSoapQueryTest extends Specification {

	private EpisodeSoapMapper episodeSoapMapperMock

	private PageMapper pageMapperMock

	private EpisodeRepository episodeRepositoryMock

	private EpisodeSoapQuery episodeSoapQuery

	def setup() {
		episodeSoapMapperMock = Mock(EpisodeSoapMapper)
		pageMapperMock = Mock(PageMapper)
		episodeRepositoryMock = Mock(EpisodeRepository)
		episodeSoapQuery = new EpisodeSoapQuery(episodeSoapMapperMock, pageMapperMock,
				episodeRepositoryMock)
	}

	def "maps EpisodeRequest to EpisodeRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock(RequestPage)
		PageRequest pageRequest = Mock(PageRequest)
		EpisodeRequest episodeRequest = Mock(EpisodeRequest) {
			getPage() >> requestPage
		}
		EpisodeRequestDTO episodeRequestDTO = Mock(EpisodeRequestDTO)
		Page page = Mock(Page)

		when:
		Page pageOutput = episodeSoapQuery.query(episodeRequest)

		then:
		1 * episodeSoapMapperMock.map(episodeRequest) >> episodeRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * episodeRepositoryMock.findMatching(episodeRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
