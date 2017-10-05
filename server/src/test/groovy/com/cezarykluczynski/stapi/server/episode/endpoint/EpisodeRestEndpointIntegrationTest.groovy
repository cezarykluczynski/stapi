package com.cezarykluczynski.stapi.server.episode.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.EpisodeBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.EpisodeFullResponse
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

	void "gets episode by UID"() {
		when:
		EpisodeFullResponse episodeResponse = stapiRestClient.episodeApi.episodeGet('EPMA0000001458', null)

		then:
		episodeResponse.episode.title == 'All Good Things...'
	}

	void "episode has stardate and year set"() {
		when:
		EpisodeBaseResponse episodeBaseResponse = stapiRestClient.episodeApi.episodeSearchPost(null, null, null, null, 'Bem', null, null, null, null,
				null, null, null, null, null, null, null, null, null, null)

		then:
		episodeBaseResponse.episodes.size() == 1
		episodeBaseResponse.episodes[0].stardateFrom != null
		episodeBaseResponse.episodes[0].stardateTo != null
		episodeBaseResponse.episodes[0].yearFrom != null
		episodeBaseResponse.episodes[0].yearTo != null
	}

}
