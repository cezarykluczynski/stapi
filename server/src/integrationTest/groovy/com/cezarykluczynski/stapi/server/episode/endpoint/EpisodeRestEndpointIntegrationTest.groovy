package com.cezarykluczynski.stapi.server.episode.endpoint

import com.cezarykluczynski.stapi.client.rest.model.EpisodeBaseResponse
import com.cezarykluczynski.stapi.client.rest.model.EpisodeFullResponse
import com.cezarykluczynski.stapi.client.rest.model.EpisodeSearchCriteria
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractEndpointIntegrationTest
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_EPISODES)
})
class EpisodeRestEndpointIntegrationTest extends AbstractEndpointIntegrationTest {

	void "gets episode by UID"() {
		when:
		EpisodeFullResponse episodeFullResponse = stapiRestClient.episode.get('EPMA0000001458')

		then:
		episodeFullResponse.episode.title == 'All Good Things...'
		episodeFullResponse.episode.season != null
		episodeFullResponse.episode.series != null
	}

	void "episode has stardate and year set"() {
		given:
		EpisodeSearchCriteria episodeSearchCriteria = new EpisodeSearchCriteria(
				title: 'Bem'
		)

		when:
		EpisodeBaseResponse episodeBaseResponse = stapiRestClient.episode.search(episodeSearchCriteria)

		then:
		episodeBaseResponse.episodes.size() == 1
		episodeBaseResponse.episodes[0].stardateFrom != null
		episodeBaseResponse.episodes[0].stardateTo != null
		episodeBaseResponse.episodes[0].yearFrom != null
		episodeBaseResponse.episodes[0].yearTo != null
	}

}
