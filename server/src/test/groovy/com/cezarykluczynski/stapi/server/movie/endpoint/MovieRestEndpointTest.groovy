package com.cezarykluczynski.stapi.server.movie.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.MovieBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.MovieFullResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.movie.dto.MovieRestBeanParams
import com.cezarykluczynski.stapi.server.movie.reader.MovieRestReader

class MovieRestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String TITLE = 'TITLE'

	private MovieRestReader movieRestReaderMock

	private MovieRestEndpoint movieRestEndpoint

	void setup() {
		movieRestReaderMock = Mock()
		movieRestEndpoint = new MovieRestEndpoint(movieRestReaderMock)
	}

	void "passes get call to MovieRestReader"() {
		given:
		MovieFullResponse movieFullResponse = Mock()

		when:
		MovieFullResponse movieFullResponseOutput = movieRestEndpoint.getMovie(UID)

		then:
		1 * movieRestReaderMock.readFull(UID) >> movieFullResponse
		movieFullResponseOutput == movieFullResponse
	}

	void "passes search get call to MovieRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		MovieBaseResponse movieResponse = Mock()

		when:
		MovieBaseResponse movieResponseOutput = movieRestEndpoint.searchMovie(pageAwareBeanParams)

		then:
		1 * movieRestReaderMock.readBase(_ as MovieRestBeanParams) >> { MovieRestBeanParams movieRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			movieResponse
		}
		movieResponseOutput == movieResponse
	}

	void "passes search post call to MovieRestReader"() {
		given:
		MovieRestBeanParams movieRestBeanParams = new MovieRestBeanParams(title: TITLE)
		MovieBaseResponse movieResponse = Mock()

		when:
		MovieBaseResponse movieResponseOutput = movieRestEndpoint.searchMovie(movieRestBeanParams)

		then:
		1 * movieRestReaderMock.readBase(movieRestBeanParams as MovieRestBeanParams) >> { MovieRestBeanParams params ->
			assert params.title == TITLE
			movieResponse
		}
		movieResponseOutput == movieResponse
	}

}
