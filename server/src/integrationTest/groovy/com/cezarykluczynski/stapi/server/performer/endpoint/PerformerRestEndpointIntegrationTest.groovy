package com.cezarykluczynski.stapi.server.performer.endpoint

import com.cezarykluczynski.stapi.client.api.dto.PerformerV2SearchCriteria
import com.cezarykluczynski.stapi.client.api.dto.RestSortClause
import com.cezarykluczynski.stapi.client.api.dto.enums.RestSortDirection
import com.cezarykluczynski.stapi.client.rest.model.EpisodeBase
import com.cezarykluczynski.stapi.client.rest.model.PerformerV2BaseResponse
import com.cezarykluczynski.stapi.client.rest.model.PerformerV2Full
import com.cezarykluczynski.stapi.client.rest.model.PerformerV2FullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractEndpointIntegrationTest
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_PERFORMERS)
})
class PerformerRestEndpointIntegrationTest extends AbstractEndpointIntegrationTest {

	@Requires({
		StaticJobCompletenessDecider.areStepsCompleted(StepName.CREATE_EPISODES, StepName.CREATE_MOVIES)
	})
	void "gets performer by UID"() {
		when:
		PerformerV2FullResponse performerV2FullResponse = stapiRestClient.performer.getV2('PEMA0000004852')
		PerformerV2Full performerV2Full = performerV2FullResponse.performer
		List<EpisodeBase> tngEpisodes = performerV2Full.episodesPerformances.stream()
				.filter {it.series.title ==  'Star Trek: The Next Generation' } .toList()

		then:
		performerV2Full != null
		performerV2Full.name == 'Sir Patrick Stewart'
		tngEpisodes.size() == 176
		performerV2Full.moviesPerformances.size() == 4
	}

	void "gets first page of performers"() {
		given:
		Integer pageNumber = 0
		Integer pageSize = 10
		PerformerV2SearchCriteria performerV2SearchCriteria = new PerformerV2SearchCriteria(
				pageNumber: pageNumber,
				pageSize: pageSize
		)

		when:
		PerformerV2BaseResponse performerV2Response = stapiRestClient.performer.searchV2(performerV2SearchCriteria)

		then:
		performerV2Response.page.pageNumber == pageNumber
		performerV2Response.page.pageSize == pageSize
		performerV2Response.performers.size() == pageSize
	}

	void "gets the only person to star in 6 series"() {
		given:
		PerformerV2SearchCriteria performerV2SearchCriteria = new PerformerV2SearchCriteria(
				tosPerformer: true,
				tasPerformer: true,
				tngPerformer: true,
				ds9Performer: true,
				voyPerformer: true,
				entPerformer: true
		)

		when:
		PerformerV2BaseResponse performerV2Response = stapiRestClient.performer.searchV2(performerV2SearchCriteria)

		then:
		performerV2Response.page.totalElements == 1
		performerV2Response.performers[0].name == 'Majel Barrett Roddenberry'
	}

	void "gets performers sorted by name"() {
		given:
		PerformerV2SearchCriteria performerV2SearchCriteria = new PerformerV2SearchCriteria()
		performerV2SearchCriteria.getSort().add(new RestSortClause(name: 'name', direction: RestSortDirection.ASC))

		when:
		PerformerV2BaseResponse performerV2Response = stapiRestClient.performer.searchV2(performerV2SearchCriteria)

		then:
		performerV2Response.performers[0].name.startsWith('A. ')
		performerV2Response.performers[1].name.startsWith('A. ')
	}

}
