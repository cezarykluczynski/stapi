package com.cezarykluczynski.stapi.server.movie.endpoint

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer
import com.cezarykluczynski.stapi.client.api.dto.RestSortClause
import com.cezarykluczynski.stapi.client.api.dto.enums.RestSortOrder
import com.cezarykluczynski.stapi.client.v1.rest.model.MovieResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.google.common.collect.Lists
import spock.lang.Requires

import java.time.LocalDate

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_MOVIES)
})
class MovieRestEndpointIntegrationTest extends AbstractMovieEndpointIntegrationTest {

	def setup() {
		createRestClient()
	}

	def "gets all movie releases in the last decade of XX century, sorted by us release date"() {
		when:
		MovieResponse movieResponse = stapiRestClient.movieApi.moviePost(null, null,
				StapiRestSortSerializer.serialize(Lists.newArrayList(new RestSortClause(
						name: 'usReleaseDate',
						sortOrder: RestSortOrder.ASC))
				), null, null, null, null, null, null, LocalDate.of(1991, 1, 1), LocalDate.of(2000, 12, 30))

		then:
		movieResponse.movies.size() == 4
	}

	def "movie has stardate and year set"() {
		when:
		MovieResponse movieResponse = stapiRestClient.movieApi.moviePost(null, null, null, null,
				'Star Trek Into Darkness', null, null, null, null, null, null)

		then:
		movieResponse.movies.size() == 1
		movieResponse.movies[0].stardateFrom != null
		movieResponse.movies[0].stardateTo != null
		movieResponse.movies[0].yearFrom != null
		movieResponse.movies[0].yearTo != null
	}

}
