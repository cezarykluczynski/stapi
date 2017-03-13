package com.cezarykluczynski.stapi.server.movie.reader

import com.cezarykluczynski.stapi.client.v1.soap.Movie as SOAPMovie
import com.cezarykluczynski.stapi.client.v1.soap.MovieRequest
import com.cezarykluczynski.stapi.client.v1.soap.MovieResponse
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.model.movie.entity.Movie as DBMovie
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.movie.mapper.MovieSoapMapper
import com.cezarykluczynski.stapi.server.movie.query.MovieSoapQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class MovieSoapReaderTest extends Specification {

	private static final String GUID = 'GUID'

	private MovieSoapQuery movieSoapQueryBuilderMock

	private MovieSoapMapper movieSoapMapperMock

	private PageMapper pageMapperMock

	private MovieSoapReader movieSoapReader

	void setup() {
		movieSoapQueryBuilderMock = Mock(MovieSoapQuery)
		movieSoapMapperMock = Mock(MovieSoapMapper)
		pageMapperMock = Mock(PageMapper)
		movieSoapReader = new MovieSoapReader(movieSoapQueryBuilderMock, movieSoapMapperMock, pageMapperMock)
	}

	void "gets database entities and puts them into MovieResponse"() {
		given:
		List<DBMovie> dbMovieList = Lists.newArrayList()
		Page<DBMovie> dbMoviePage = Mock(Page)
		dbMoviePage.content >> dbMovieList
		List<SOAPMovie> soapMovieList = Lists.newArrayList(new SOAPMovie(guid: GUID))
		MovieRequest movieRequest = Mock(MovieRequest)
		ResponsePage responsePage = Mock(ResponsePage)

		when:
		MovieResponse movieResponse = movieSoapReader.readBase(movieRequest)

		then:
		1 * movieSoapQueryBuilderMock.query(movieRequest) >> dbMoviePage
		1 * pageMapperMock.fromPageToSoapResponsePage(dbMoviePage) >> responsePage
		1 * movieSoapMapperMock.map(dbMovieList) >> soapMovieList
		movieResponse.movies[0].guid == GUID
		movieResponse.page == responsePage
	}

}
