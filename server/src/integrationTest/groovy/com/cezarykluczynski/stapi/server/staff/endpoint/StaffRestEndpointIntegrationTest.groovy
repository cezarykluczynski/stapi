package com.cezarykluczynski.stapi.server.staff.endpoint

import com.cezarykluczynski.stapi.client.rest.model.RequestSort
import com.cezarykluczynski.stapi.client.rest.model.RequestSortClause
import com.cezarykluczynski.stapi.client.rest.model.RequestSortDirection
import com.cezarykluczynski.stapi.client.rest.model.StaffV2BaseResponse
import com.cezarykluczynski.stapi.client.rest.model.StaffV2FullResponse
import com.cezarykluczynski.stapi.client.rest.model.StaffV2SearchCriteria
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractEndpointIntegrationTest
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_STAFF)
})
class StaffRestEndpointIntegrationTest extends AbstractEndpointIntegrationTest {

	@Requires({
		StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_MOVIES)
	})
	void "gets staff with movie experience by UID"() {
		when:
		StaffV2FullResponse staffV2FullResponse = stapiRestClient.staff.getV2('STMA0000104646')

		then:
		staffV2FullResponse.staff.name == 'Rick Berman'
		staffV2FullResponse.staff.storyAuthoredMovies.size() == 4
		staffV2FullResponse.staff.producedMovies.size() == 4
		staffV2FullResponse.staff.movies.size() == 4
	}

	void "gets first page of staff"() {
		given:
		StaffV2SearchCriteria staffV2SearchCriteria = new StaffV2SearchCriteria(
				pageNumber: 0,
				pageSize: 10
		)
		Integer pageNumber = 0
		Integer pageSize = 10

		when:
		StaffV2BaseResponse staffV2Response = stapiRestClient.staff.searchV2(staffV2SearchCriteria)

		then:
		staffV2Response.page.pageNumber == pageNumber
		staffV2Response.page.pageSize == pageSize
		staffV2Response.staff.size() == pageSize
	}

	@Requires({
		StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_EPISODES)
	})
	void "gets staff with series experience by UID"() {
		when:
		StaffV2FullResponse staffV2FullResponse = stapiRestClient.staff.getV2('STMA0000001846')

		then:
		staffV2FullResponse.staff.name == 'Ira Steven Behr'
		staffV2FullResponse.staff.writtenEpisodes.size() == 34
		staffV2FullResponse.staff.teleplayAuthoredEpisodes.size() == 18
		staffV2FullResponse.staff.storyAuthoredEpisodes.size() == 10
	}

	void "gets staff sorted by name"() {
		given:
		StaffV2SearchCriteria staffV2SearchCriteria = new StaffV2SearchCriteria()
		staffV2SearchCriteria.setSort(new RequestSort().addClausesItem(new RequestSortClause(
						name: 'name', direction: RequestSortDirection.ASC, clauseOrder: 0)))

		when:
		StaffV2BaseResponse staffV2BaseResponse = stapiRestClient.staff.searchV2(staffV2SearchCriteria)

		then:
		staffV2BaseResponse.staff[0].name.startsWith('A. ')
		staffV2BaseResponse.staff[1].name.startsWith('A. ')
	}

}
