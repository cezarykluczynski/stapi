package com.cezarykluczynski.stapi.server.movie.query

import com.cezarykluczynski.stapi.model.movie.dto.MovieRequestDTO
import com.cezarykluczynski.stapi.model.movie.repository.MovieRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.movie.dto.MovieRestBeanParams
import com.cezarykluczynski.stapi.server.movie.mapper.MovieRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class MovieRestQueryTest extends Specification {

	private MovieRestMapper movieRestMapperMock

	private PageMapper pageMapperMock

	private MovieRepository movieRepositoryMock

	private MovieRestQuery movieRestQuery

	void setup() {
		movieRestMapperMock = Mock(MovieRestMapper)
		pageMapperMock = Mock(PageMapper)
		movieRepositoryMock = Mock(MovieRepository)
		movieRestQuery = new MovieRestQuery(movieRestMapperMock, pageMapperMock,
				movieRepositoryMock)
	}

	void "maps MovieRestBeanParams to MovieRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock(PageRequest)
		MovieRestBeanParams movieRestBeanParams = Mock(MovieRestBeanParams) {

		}
		MovieRequestDTO movieRequestDTO = Mock(MovieRequestDTO)
		Page page = Mock(Page)

		when:
		Page pageOutput = movieRestQuery.query(movieRestBeanParams)

		then:
		1 * movieRestMapperMock.map(movieRestBeanParams) >> movieRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(movieRestBeanParams) >> pageRequest
		1 * movieRepositoryMock.findMatching(movieRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
