package com.cezarykluczynski.stapi.server.movie.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.MovieBase
import com.cezarykluczynski.stapi.client.v1.soap.MovieBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.MovieBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.MovieFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.MovieFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_MOVIES)
})
class MovieSoapEndpointIntegrationTest extends AbstractMovieEndpointIntegrationTest {

	void setup() {
		createSoapClient()
	}

	void "gets movie by UID"() {
		when:
		MovieFullResponse movieFullResponse = stapiSoapClient.moviePortType.getMovieFull(new MovieFullRequest(
				uid: 'MOMA0000103536'
		))

		then:
		movieFullResponse.movie.title == 'Star Trek Generations'
	}

	void "gets movie by title"() {
		when:
		MovieBaseResponse movieBaseResponse = stapiSoapClient.moviePortType.getMovieBase(new MovieBaseRequest(
				title: 'Star Trek Into Darkness'
		))
		List<MovieBase> movieBaseList = movieBaseResponse.movies

		then:
		movieBaseList.size() == 1
		movieBaseList[0].title == 'Star Trek Into Darkness'
	}

}
