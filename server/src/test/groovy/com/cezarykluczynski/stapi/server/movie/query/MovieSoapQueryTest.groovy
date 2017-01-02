package com.cezarykluczynski.stapi.server.movie.query

import com.cezarykluczynski.stapi.client.v1.soap.MovieRequest
import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.model.movie.dto.MovieRequestDTO
import com.cezarykluczynski.stapi.model.movie.repository.MovieRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.movie.mapper.MovieSoapMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class MovieSoapQueryTest extends Specification {

	private MovieSoapMapper movieSoapMapperMock

	private PageMapper pageMapperMock

	private MovieRepository movieRepositoryMock

	private MovieSoapQuery movieSoapQuery

	def setup() {
		movieSoapMapperMock = Mock(MovieSoapMapper)
		pageMapperMock = Mock(PageMapper)
		movieRepositoryMock = Mock(MovieRepository)
		movieSoapQuery = new MovieSoapQuery(movieSoapMapperMock, pageMapperMock,
				movieRepositoryMock)
	}

	def "maps MovieRequest to MovieRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock(RequestPage)
		PageRequest pageRequest = Mock(PageRequest)
		MovieRequest movieRequest = Mock(MovieRequest) {
			getPage() >> requestPage
		}
		MovieRequestDTO movieRequestDTO = Mock(MovieRequestDTO)
		Page page = Mock(Page)

		when:
		Page pageOutput = movieSoapQuery.query(movieRequest)

		then:
		1 * movieSoapMapperMock.map(movieRequest) >> movieRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * movieRepositoryMock.findMatching(movieRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
