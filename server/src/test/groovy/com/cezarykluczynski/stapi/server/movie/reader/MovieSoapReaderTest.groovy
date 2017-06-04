package com.cezarykluczynski.stapi.server.movie.reader

import com.cezarykluczynski.stapi.client.v1.soap.MovieBase
import com.cezarykluczynski.stapi.client.v1.soap.MovieBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.MovieBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.MovieFull
import com.cezarykluczynski.stapi.client.v1.soap.MovieFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.MovieFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.RequestSort
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.client.v1.soap.ResponseSort
import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.movie.mapper.MovieBaseSoapMapper
import com.cezarykluczynski.stapi.server.movie.mapper.MovieFullSoapMapper
import com.cezarykluczynski.stapi.server.movie.query.MovieSoapQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class MovieSoapReaderTest extends Specification {

	private static final String UID = 'UID'

	private MovieSoapQuery movieSoapQueryBuilderMock

	private MovieBaseSoapMapper movieBaseSoapMapperMock

	private MovieFullSoapMapper movieFullSoapMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private MovieSoapReader movieSoapReader

	void setup() {
		movieSoapQueryBuilderMock = Mock()
		movieBaseSoapMapperMock = Mock()
		movieFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		movieSoapReader = new MovieSoapReader(movieSoapQueryBuilderMock, movieBaseSoapMapperMock, movieFullSoapMapperMock, pageMapperMock,
				sortMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<Movie> movieList = Lists.newArrayList()
		Page<Movie> moviePage = Mock()
		List<MovieBase> soapMovieList = Lists.newArrayList(new MovieBase(uid: UID))
		MovieBaseRequest movieBaseRequest = Mock()
		ResponsePage responsePage = Mock()
		RequestSort requestSort = Mock()
		ResponseSort responseSort = Mock()

		when:
		MovieBaseResponse movieResponse = movieSoapReader.readBase(movieBaseRequest)

		then:
		1 * movieSoapQueryBuilderMock.query(movieBaseRequest) >> moviePage
		1 * moviePage.content >> movieList
		1 * pageMapperMock.fromPageToSoapResponsePage(moviePage) >> responsePage
		1 * movieBaseRequest.sort >> requestSort
		1 * sortMapperMock.map(requestSort) >> responseSort
		1 * movieBaseSoapMapperMock.mapBase(movieList) >> soapMovieList
		0 * _
		movieResponse.movies[0].uid == UID
		movieResponse.page == responsePage
		movieResponse.sort == responseSort
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		MovieFull movieFull = new MovieFull(uid: UID)
		Movie movie = Mock()
		Page<Movie> moviePage = Mock()
		MovieFullRequest movieFullRequest = new MovieFullRequest(uid: UID)

		when:
		MovieFullResponse movieFullResponse = movieSoapReader.readFull(movieFullRequest)

		then:
		1 * movieSoapQueryBuilderMock.query(movieFullRequest) >> moviePage
		1 * moviePage.content >> Lists.newArrayList(movie)
		1 * movieFullSoapMapperMock.mapFull(movie) >> movieFull
		0 * _
		movieFullResponse.movie.uid == UID
	}

	void "requires UID in full request"() {
		given:
		MovieFullRequest movieFullRequest = Mock()

		when:
		movieSoapReader.readFull(movieFullRequest)

		then:
		thrown(MissingUIDException)
	}

}
