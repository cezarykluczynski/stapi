package com.cezarykluczynski.stapi.server.movie.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.Movie as RESTMovie
import com.cezarykluczynski.stapi.client.v1.rest.model.MovieResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.movie.dto.MovieRestBeanParams
import com.cezarykluczynski.stapi.server.movie.mapper.MovieRestMapper
import com.cezarykluczynski.stapi.server.movie.query.MovieRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class MovieRestReaderTest extends Specification {

	private static final String GUID = 'GUID'

	private MovieRestQuery movieRestQueryBuilderMock

	private MovieRestMapper movieRestMapperMock

	private PageMapper pageMapperMock

	private MovieRestReader movieRestReader

	void setup() {
		movieRestQueryBuilderMock = Mock(MovieRestQuery)
		movieRestMapperMock = Mock(MovieRestMapper)
		pageMapperMock = Mock(PageMapper)
		movieRestReader = new MovieRestReader(movieRestQueryBuilderMock, movieRestMapperMock, pageMapperMock)
	}

	void "gets database entities and puts them into MovieResponse"() {
		given:
		List<Movie> dbMovieList = Lists.newArrayList()
		Page<Movie> dbMoviePage = Mock(Page)
		dbMoviePage.content >> dbMovieList
		List<RESTMovie> soapMovieList = Lists.newArrayList(new RESTMovie(guid: GUID))
		MovieRestBeanParams seriesRestBeanParams = Mock(MovieRestBeanParams)
		ResponsePage responsePage = Mock(ResponsePage)

		when:
		MovieResponse movieResponse = movieRestReader.read(seriesRestBeanParams)

		then:
		1 * movieRestQueryBuilderMock.query(seriesRestBeanParams) >> dbMoviePage
		1 * pageMapperMock.fromPageToRestResponsePage(dbMoviePage) >> responsePage
		1 * movieRestMapperMock.map(dbMovieList) >> soapMovieList
		movieResponse.movies[0].guid == GUID
		movieResponse.page == responsePage
	}

}
