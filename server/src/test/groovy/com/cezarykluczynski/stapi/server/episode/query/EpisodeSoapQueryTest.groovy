package com.cezarykluczynski.stapi.server.episode.query

import com.cezarykluczynski.stapi.client.v1.soap.EpisodeBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeFullRequest
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

	void setup() {
		episodeSoapMapperMock = Mock(EpisodeSoapMapper)
		pageMapperMock = Mock(PageMapper)
		episodeRepositoryMock = Mock(EpisodeRepository)
		episodeSoapQuery = new EpisodeSoapQuery(episodeSoapMapperMock, pageMapperMock, episodeRepositoryMock)
	}

	void "maps EpisodeBaseRequest to EpisodeRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock(RequestPage)
		PageRequest pageRequest = Mock(PageRequest)
		EpisodeBaseRequest episodeRequest = Mock(EpisodeBaseRequest)
		episodeRequest.page >> requestPage
		EpisodeRequestDTO episodeRequestDTO = Mock(EpisodeRequestDTO)
		Page page = Mock(Page)

		when:
		Page pageOutput = episodeSoapQuery.query(episodeRequest)

		then:
		1 * episodeSoapMapperMock.mapBase(episodeRequest) >> episodeRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * episodeRepositoryMock.findMatching(episodeRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps EpisodeFullRequest to EpisodeRequestDTO, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock(PageRequest)
		EpisodeFullRequest episodeRequest = Mock(EpisodeFullRequest)
		EpisodeRequestDTO episodeRequestDTO = Mock(EpisodeRequestDTO)
		Page page = Mock(Page)

		when:
		Page pageOutput = episodeSoapQuery.query(episodeRequest)

		then:
		1 * episodeSoapMapperMock.mapFull(episodeRequest) >> episodeRequestDTO
		1 * pageMapperMock.defaultPageRequest >> pageRequest
		1 * episodeRepositoryMock.findMatching(episodeRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
