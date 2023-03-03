package com.cezarykluczynski.stapi.server.movie.endpoint

import com.cezarykluczynski.stapi.client.api.dto.MovieSearchCriteria
import com.cezarykluczynski.stapi.client.api.dto.RestSortClause
import com.cezarykluczynski.stapi.client.api.dto.enums.RestSortDirection
import com.cezarykluczynski.stapi.client.v1.rest.model.MovieBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.MovieFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractEndpointIntegrationTest
import spock.lang.Requires

import java.time.LocalDate

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_MOVIES)
})
class MovieRestEndpointIntegrationTest extends AbstractEndpointIntegrationTest {

	void 'gets movie by UID'() {
		when:
		MovieFullResponse movieFullResponse = stapiRestClient.movie.get('MOMA0000267713')

		then:
		movieFullResponse.movie.title == 'Star Trek Nemesis'
	}

	void "gets all movie releases in the last decade of XX century, sorted by us release date"() {
		given:
		MovieSearchCriteria movieSearchCriteria = new MovieSearchCriteria(
				usReleaseDateFrom: LocalDate.of(1991, 1, 1),
				usReleaseDateTo: LocalDate.of(2000, 12, 30)
		)
		movieSearchCriteria.getSort().add(new RestSortClause(name: 'usReleaseDate', direction: RestSortDirection.ASC))

		when:
		MovieBaseResponse movieBaseResponse = stapiRestClient.movie.search(movieSearchCriteria)

		then:
		movieBaseResponse.movies.size() == 4
	}

	void "movie has stardate and year set"() {
		given:
		MovieSearchCriteria movieSearchCriteria = new MovieSearchCriteria(
				title: 'Star Trek Into Darkness'
		)

		when:
		MovieBaseResponse movieBaseResponse = stapiRestClient.movie.search(movieSearchCriteria)

		then:
		movieBaseResponse.movies.size() == 1
		movieBaseResponse.movies[0].stardateFrom != null
		movieBaseResponse.movies[0].stardateTo != null
		movieBaseResponse.movies[0].yearFrom != null
		movieBaseResponse.movies[0].yearTo != null
	}

	void "Kathryn Janeway appeared in Nemesis"() {
		given:
		MovieSearchCriteria movieSearchCriteria = new MovieSearchCriteria(
				title: 'Star Trek Nemesis'
		)

		when:
		MovieBaseResponse movieBaseResponse = stapiRestClient.movie.search(movieSearchCriteria)
		MovieFullResponse movieFullResponse = stapiRestClient.movie.get(movieBaseResponse.movies[0].uid)

		then:
		movieFullResponse.movie.characters.stream().anyMatch { it.name == 'Kathryn Janeway' }
	}

}
