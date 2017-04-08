package com.cezarykluczynski.stapi.server.episode.query

import com.cezarykluczynski.stapi.client.v1.soap.EpisodeBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.model.episode.dto.EpisodeRequestDTO
import com.cezarykluczynski.stapi.model.episode.repository.EpisodeRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeBaseSoapMapper
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeFullSoapMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class EpisodeSoapQueryTest extends Specification {

	private EpisodeBaseSoapMapper episodeBaseSoapMapperMock

	private EpisodeFullSoapMapper episodeFullSoapMapperMock

	private PageMapper pageMapperMock

	private EpisodeRepository episodeRepositoryMock

	private EpisodeSoapQuery episodeSoapQuery

	void setup() {
		episodeBaseSoapMapperMock = Mock()
		episodeFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		episodeRepositoryMock = Mock()
		episodeSoapQuery = new EpisodeSoapQuery(episodeBaseSoapMapperMock, episodeFullSoapMapperMock, pageMapperMock, episodeRepositoryMock)
	}

	void "maps EpisodeBaseRequest to EpisodeRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock()
		PageRequest pageRequest = Mock()
		EpisodeBaseRequest episodeRequest = Mock()
		episodeRequest.page >> requestPage
		EpisodeRequestDTO episodeRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = episodeSoapQuery.query(episodeRequest)

		then:
		1 * episodeBaseSoapMapperMock.mapBase(episodeRequest) >> episodeRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * episodeRepositoryMock.findMatching(episodeRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps EpisodeFullRequest to EpisodeRequestDTO, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		EpisodeFullRequest episodeRequest = Mock()
		EpisodeRequestDTO episodeRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = episodeSoapQuery.query(episodeRequest)

		then:
		1 * episodeFullSoapMapperMock.mapFull(episodeRequest) >> episodeRequestDTO
		1 * pageMapperMock.defaultPageRequest >> pageRequest
		1 * episodeRepositoryMock.findMatching(episodeRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
