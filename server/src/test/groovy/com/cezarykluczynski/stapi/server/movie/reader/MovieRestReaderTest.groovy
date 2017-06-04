package com.cezarykluczynski.stapi.server.movie.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.MovieBase
import com.cezarykluczynski.stapi.client.v1.rest.model.MovieBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.MovieFull
import com.cezarykluczynski.stapi.client.v1.rest.model.MovieFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSort
import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.movie.dto.MovieRestBeanParams
import com.cezarykluczynski.stapi.server.movie.mapper.MovieBaseRestMapper
import com.cezarykluczynski.stapi.server.movie.mapper.MovieFullRestMapper
import com.cezarykluczynski.stapi.server.movie.query.MovieRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class MovieRestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private MovieRestQuery movieRestQueryBuilderMock

	private MovieBaseRestMapper movieBaseRestMapperMock

	private MovieFullRestMapper movieFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private MovieRestReader movieRestReader

	void setup() {
		movieRestQueryBuilderMock = Mock()
		movieBaseRestMapperMock = Mock()
		movieFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		movieRestReader = new MovieRestReader(movieRestQueryBuilderMock, movieBaseRestMapperMock, movieFullRestMapperMock, pageMapperMock,
				sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		MovieBase movieBase = Mock()
		Movie movie = Mock()
		MovieRestBeanParams movieRestBeanParams = Mock()
		List<MovieBase> restMovieList = Lists.newArrayList(movieBase)
		List<Movie> movieList = Lists.newArrayList(movie)
		Page<Movie> moviePage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		MovieBaseResponse movieResponseOutput = movieRestReader.readBase(movieRestBeanParams)

		then:
		1 * movieRestQueryBuilderMock.query(movieRestBeanParams) >> moviePage
		1 * pageMapperMock.fromPageToRestResponsePage(moviePage) >> responsePage
		1 * movieRestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * moviePage.content >> movieList
		1 * movieBaseRestMapperMock.mapBase(movieList) >> restMovieList
		0 * _
		movieResponseOutput.movies == restMovieList
		movieResponseOutput.page == responsePage
		movieResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		MovieFull movieFull = Mock()
		Movie movie = Mock()
		List<Movie> movieList = Lists.newArrayList(movie)
		Page<Movie> moviePage = Mock()

		when:
		MovieFullResponse movieResponseOutput = movieRestReader.readFull(UID)

		then:
		1 * movieRestQueryBuilderMock.query(_ as MovieRestBeanParams) >> { MovieRestBeanParams movieRestBeanParams ->
			assert movieRestBeanParams.uid == UID
			moviePage
		}
		1 * moviePage.content >> movieList
		1 * movieFullRestMapperMock.mapFull(movie) >> movieFull
		0 * _
		movieResponseOutput.movie == movieFull
	}

	void "requires UID in full request"() {
		when:
		movieRestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
