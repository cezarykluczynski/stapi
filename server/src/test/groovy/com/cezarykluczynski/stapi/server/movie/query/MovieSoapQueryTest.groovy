package com.cezarykluczynski.stapi.server.movie.query

import com.cezarykluczynski.stapi.client.v1.soap.MovieBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.MovieFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.model.movie.dto.MovieRequestDTO
import com.cezarykluczynski.stapi.model.movie.repository.MovieRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.movie.mapper.MovieBaseSoapMapper
import com.cezarykluczynski.stapi.server.movie.mapper.MovieFullSoapMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class MovieSoapQueryTest extends Specification {

	private MovieBaseSoapMapper movieBaseSoapMapperMock

	private MovieFullSoapMapper movieFullSoapMapperMock

	private PageMapper pageMapperMock

	private MovieRepository movieRepositoryMock

	private MovieSoapQuery movieSoapQuery

	void setup() {
		movieBaseSoapMapperMock = Mock()
		movieFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		movieRepositoryMock = Mock()
		movieSoapQuery = new MovieSoapQuery(movieBaseSoapMapperMock, movieFullSoapMapperMock, pageMapperMock, movieRepositoryMock)
	}

	void "maps MovieBaseRequest to MovieRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock()
		PageRequest pageRequest = Mock()
		MovieBaseRequest movieRequest = Mock()
		movieRequest.page >> requestPage
		MovieRequestDTO movieRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = movieSoapQuery.query(movieRequest)

		then:
		1 * movieBaseSoapMapperMock.mapBase(movieRequest) >> movieRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * movieRepositoryMock.findMatching(movieRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps MovieFullRequest to MovieRequestDTO, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		MovieFullRequest movieRequest = Mock()
		MovieRequestDTO movieRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = movieSoapQuery.query(movieRequest)

		then:
		1 * movieFullSoapMapperMock.mapFull(movieRequest) >> movieRequestDTO
		1 * pageMapperMock.defaultPageRequest >> pageRequest
		1 * movieRepositoryMock.findMatching(movieRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
