package com.cezarykluczynski.stapi.server.performer.endpoint

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer
import com.cezarykluczynski.stapi.client.api.dto.RestSortClause
import com.cezarykluczynski.stapi.client.api.dto.enums.RestSortDirection
import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerV2BaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerV2FullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.google.common.collect.Lists
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_PERFORMERS)
})
class PerformerV2RestEndpointIntegrationTest extends AbstractPerformerEndpointIntegrationTest {

	void setup() {
		createRestClient()
	}

	@Requires({
		StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_EPISODES) &&
				StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_MOVIES)
	})
	void "gets performer by uid"() {
		when:
		PerformerV2FullResponse performerV2Response = stapiRestClient.performerApi.v2RestPerformerGet(UID, null)

		then:
		performerV2Response.performer.uid == UID
		performerV2Response.performer.episodesPerformances.size() == 177
		performerV2Response.performer.moviesPerformances.size() == 4
	}

	void "gets first page of performers"() {
		given:
		Integer pageNumber = 0
		Integer pageSize = 10

		when:
		PerformerV2BaseResponse performerV2Response = stapiRestClient.performerApi.v2RestPerformerSearchGet(pageNumber, pageSize, null)

		then:
		performerV2Response.page.pageNumber == pageNumber
		performerV2Response.page.pageSize == pageSize
		performerV2Response.performers.size() == pageSize
	}

	void "gets the only person to star in 6 series"() {
		when:
		PerformerV2BaseResponse performerV2Response = stapiRestClient.performerApi.v2RestPerformerSearchPost(null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null, null, true, true, null, null, null, null, null, null, null, null, null,
				true, true, true, null, null, true)

		then:
		performerV2Response.page.totalElements == 1
		performerV2Response.performers[0].name == 'Majel Barrett-Roddenberry'
	}

	void "gets performers sorted by name"() {
		when:
		PerformerV2BaseResponse performerV2Response = stapiRestClient.performerApi.v2RestPerformerSearchPost(null, null,
				StapiRestSortSerializer.serialize(Lists.newArrayList(
						new RestSortClause(name: 'name', direction: RestSortDirection.ASC)
				)), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null)

		then:
		performerV2Response.performers[0].name.startsWith('A. ')
		performerV2Response.performers[1].name.startsWith('A. ')
	}

}
