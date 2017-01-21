package com.cezarykluczynski.stapi.server.episode.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.EpisodeResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_EPISODES)
})
class EpisodeRestEndpointIntegrationTest extends AbstractEpisodeEndpointIntegrationTest {

	void setup() {
		createRestClient()
	}

	void "episode has stardate and year set"() {
		when:
		EpisodeResponse episodeResponse = stapiRestClient.episodeApi.episodePost(null, null, null, null, 'Bem', null,
				null, null, null, null, null, null, null, null, null, null, null)

		then:
		episodeResponse.episodes.size() == 1
		episodeResponse.episodes[0].stardateFrom != null
		episodeResponse.episodes[0].stardateTo != null
		episodeResponse.episodes[0].yearFrom != null
		episodeResponse.episodes[0].yearTo != null
	}

	void "episodes could be found by guid"() {
		when:
		EpisodeResponse episodeResponse = stapiRestClient.episodeApi.episodePost(null, null, null, 'EPMA0000001458',
				null, null, null, null, null, null, null, null, null, null, null, null, null)

		then:
		episodeResponse.episodes.size() == 1
		episodeResponse.episodes[0].title == 'All Good Things...'
	}

}
