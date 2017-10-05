package com.cezarykluczynski.stapi.server.performer.endpoint

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer
import com.cezarykluczynski.stapi.client.api.dto.RestSortClause
import com.cezarykluczynski.stapi.client.api.dto.enums.RestSortDirection
import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.google.common.collect.Lists
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_PERFORMERS)
})
class PerformerRestEndpointIntegrationTest extends AbstractPerformerEndpointIntegrationTest {

	void setup() {
		createRestClient()
	}

	@Requires({
		StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_EPISODES) &&
				StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_MOVIES)
	})
	void "gets performer by uid"() {
		when:
		PerformerFullResponse performerResponse = stapiRestClient.performerApi.performerGet(UID, null)

		then:
		performerResponse.performer.uid == UID
		performerResponse.performer.episodesPerformances.size() == 177
		performerResponse.performer.moviesPerformances.size() == 4
	}

	void "gets first page of performers"() {
		given:
		Integer pageNumber = 0
		Integer pageSize = 10

		when:
		PerformerBaseResponse performerResponse = stapiRestClient.performerApi.performerSearchGet(pageNumber, pageSize, null)

		then:
		performerResponse.page.pageNumber == pageNumber
		performerResponse.page.pageSize == pageSize
		performerResponse.performers.size() == pageSize
	}

	void "gets the only person to star in 6 series"() {
		when:
		PerformerBaseResponse performerResponse = stapiRestClient.performerApi.performerSearchPost(null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, true, true, null, null, null, true, true, true, null, null, true)

		then:
		performerResponse.page.totalElements == 1
		performerResponse.performers[0].name == 'Majel Barrett-Roddenberry'
	}

	void "gets performers sorted by name"() {
		when:
		PerformerBaseResponse performerResponse = stapiRestClient.performerApi.performerSearchPost(null, null,
				StapiRestSortSerializer.serialize(Lists.newArrayList(
						new RestSortClause(name: 'name', direction: RestSortDirection.ASC)
				)), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null)

		then:
		performerResponse.performers[0].name.startsWith('A. ')
		performerResponse.performers[1].name.startsWith('A. ')
	}

}
