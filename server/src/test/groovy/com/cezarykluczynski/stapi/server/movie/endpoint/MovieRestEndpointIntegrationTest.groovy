package com.cezarykluczynski.stapi.server.movie.endpoint

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer
import com.cezarykluczynski.stapi.client.api.dto.RestSortClause
import com.cezarykluczynski.stapi.client.api.dto.enums.RestSortDirection
import com.cezarykluczynski.stapi.client.v1.rest.model.MovieBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.MovieFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.google.common.collect.Lists
import spock.lang.Requires

import java.time.LocalDate

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_MOVIES)
})
class MovieRestEndpointIntegrationTest extends AbstractMovieEndpointIntegrationTest {

	void setup() {
		createRestClient()
	}

	void 'gets movie by UID'() {
		when:
		MovieFullResponse movieFullResponse = stapiRestClient.movieApi.movieGet('MOMA0000003135', null)

		then:
		movieFullResponse.movie.title == 'Star Trek Nemesis'
	}

	void "gets all movie releases in the last decade of XX century, sorted by us release date"() {
		when:
		MovieBaseResponse movieBaseResponse = stapiRestClient.movieApi.movieSearchPost(null, null,
				StapiRestSortSerializer.serialize(Lists.newArrayList(new RestSortClause(
						name: 'usReleaseDate',
						direction: RestSortDirection.ASC))
				), null, null, null, null, null, null, LocalDate.of(1991, 1, 1), LocalDate.of(2000, 12, 30))

		then:
		movieBaseResponse.movies.size() == 4
	}

	void "movie has stardate and year set"() {
		when:
		MovieBaseResponse movieBaseResponse = stapiRestClient.movieApi.movieSearchPost(null, null, null, null, 'Star Trek Into Darkness', null, null,
				null, null, null, null)

		then:
		movieBaseResponse.movies.size() == 1
		movieBaseResponse.movies[0].stardateFrom != null
		movieBaseResponse.movies[0].stardateTo != null
		movieBaseResponse.movies[0].yearFrom != null
		movieBaseResponse.movies[0].yearTo != null
	}

	void "confirms that Kathryn Janeway appeared in Nemesis"() {
		when:
		MovieBaseResponse movieBaseResponse = stapiRestClient.movieApi.movieSearchPost(null, null, null, null, 'Star Trek Nemesis', null, null, null,
				null, null, null)
		MovieFullResponse movieFullResponse = stapiRestClient.movieApi.movieGet(movieBaseResponse.movies[0].uid, null)

		then:
		movieFullResponse.movie.characters.stream().anyMatch { it.name == 'Kathryn Janeway' }
	}

}
