package com.cezarykluczynski.stapi.server.movie.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.MovieResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.movie.dto.MovieRestBeanParams
import com.cezarykluczynski.stapi.server.movie.reader.MovieRestReader

class MovieRestEndpointTest extends AbstractRestEndpointTest {

	private MovieRestReader movieRestReaderMock

	private MovieRestEndpoint movieRestEndpoint

	void setup() {
		movieRestReaderMock = Mock(MovieRestReader)
		movieRestEndpoint = new MovieRestEndpoint(movieRestReaderMock)
	}

	void "passes get call to MovieRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock(PageSortBeanParams)
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		MovieResponse movieResponse = Mock(MovieResponse)

		when:
		MovieResponse movieResponseOutput = movieRestEndpoint.getMovies(pageAwareBeanParams)

		then:
		1 * movieRestReaderMock.readBase(_ as MovieRestBeanParams) >> { MovieRestBeanParams movieRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			movieResponse
		}
		movieResponseOutput == movieResponse
	}

	void "passes post call to MovieRestReader"() {
		given:
		MovieRestBeanParams movieRestBeanParams = new MovieRestBeanParams()
		MovieResponse movieResponse = Mock(MovieResponse)

		when:
		MovieResponse movieResponseOutput = movieRestEndpoint.searchMovies(movieRestBeanParams)

		then:
		1 * movieRestReaderMock.readBase(movieRestBeanParams as MovieRestBeanParams) >> { MovieRestBeanParams params ->
			movieResponse
		}
		movieResponseOutput == movieResponse
	}

}
