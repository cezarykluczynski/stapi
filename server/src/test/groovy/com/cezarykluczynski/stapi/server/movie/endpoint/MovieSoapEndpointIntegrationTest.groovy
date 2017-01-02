package com.cezarykluczynski.stapi.server.movie.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.Movie
import com.cezarykluczynski.stapi.client.v1.soap.MovieRequest
import com.cezarykluczynski.stapi.client.v1.soap.MovieResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_MOVIES)
})
class MovieSoapEndpointIntegrationTest extends AbstractMovieEndpointIntegrationTest {

	def setup() {
		createSoapClient()
	}

	def "gets movie by title"() {
		when:
		MovieResponse movieResponse = stapiSoapClient.moviePortType.getMovies(new MovieRequest(
				title: 'Star Trek Into Darkness'
		))
		List<Movie> movieList = movieResponse.movies

		then:
		movieList.size() == 1
		movieList[0].title == 'Star Trek Into Darkness'
	}

}
