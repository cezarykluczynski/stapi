package com.cezarykluczynski.stapi.server.video_game.query

import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.client.v1.soap.VideoGameBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.VideoGameFullRequest
import com.cezarykluczynski.stapi.model.video_game.dto.VideoGameRequestDTO
import com.cezarykluczynski.stapi.model.video_game.repository.VideoGameRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.video_game.mapper.VideoGameBaseSoapMapper
import com.cezarykluczynski.stapi.server.video_game.mapper.VideoGameFullSoapMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class VideoGameSoapQueryTest extends Specification {

	private VideoGameBaseSoapMapper videoGameBaseSoapMapperMock

	private VideoGameFullSoapMapper videoGameFullSoapMapperMock

	private PageMapper pageMapperMock

	private VideoGameRepository videoGameRepositoryMock

	private VideoGameSoapQuery videoGameSoapQuery

	void setup() {
		videoGameBaseSoapMapperMock = Mock()
		videoGameFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		videoGameRepositoryMock = Mock()
		videoGameSoapQuery = new VideoGameSoapQuery(videoGameBaseSoapMapperMock, videoGameFullSoapMapperMock, pageMapperMock, videoGameRepositoryMock)
	}

	void "maps VideoGameBaseRequest to VideoGameRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock()
		PageRequest pageRequest = Mock()
		VideoGameBaseRequest videoGameRequest = Mock()
		videoGameRequest.page >> requestPage
		VideoGameRequestDTO videoGameRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = videoGameSoapQuery.query(videoGameRequest)

		then:
		1 * videoGameBaseSoapMapperMock.mapBase(videoGameRequest) >> videoGameRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * videoGameRepositoryMock.findMatching(videoGameRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps VideoGameFullRequest to VideoGameRequestDTO, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		VideoGameFullRequest videoGameRequest = Mock()
		VideoGameRequestDTO videoGameRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = videoGameSoapQuery.query(videoGameRequest)

		then:
		1 * videoGameFullSoapMapperMock.mapFull(videoGameRequest) >> videoGameRequestDTO
		1 * pageMapperMock.defaultPageRequest >> pageRequest
		1 * videoGameRepositoryMock.findMatching(videoGameRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
