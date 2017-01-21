package com.cezarykluczynski.stapi.server.staff.endpoint

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer
import com.cezarykluczynski.stapi.client.api.dto.RestSortClause
import com.cezarykluczynski.stapi.client.api.dto.enums.RestSortDirection
import com.cezarykluczynski.stapi.client.v1.rest.model.StaffResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.google.common.collect.Lists
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_STAFF)
})
class StaffRestEndpointIntegrationTest extends AbstractStaffEndpointIntegrationTest {

	void setup() {
		createRestClient()
	}

	void "gets first page of staff"() {
		given:
		Integer pageNumber = 0
		Integer pageSize = 10

		when:
		StaffResponse staffResponse = stapiRestClient.staffApi.staffGet(pageNumber, pageSize)

		then:
		staffResponse.page.pageNumber == pageNumber
		staffResponse.page.pageSize == pageSize
		staffResponse.staff.size() == pageSize
	}

	void "gets staff with series experience by guid"() {
		when:
		StaffResponse staffResponse = stapiRestClient.staffApi.staffPost(null, null, null, IRA_STEVEN_BEHR_GUID, null,
				null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null)

		then:
		staffResponse.page.totalElements == 1
		staffResponse.staff[0].guid == IRA_STEVEN_BEHR_GUID
		staffResponse.staff[0].writtenEpisodeHeaders.size() == 34
		staffResponse.staff[0].teleplayAuthoredEpisodeHeaders.size() == 18
		staffResponse.staff[0].storyAuthoredEpisodeHeaders.size() == 10
	}

	void "gets staff with movie experience by guid"() {
		when:
		StaffResponse staffResponse = stapiRestClient.staffApi.staffPost(null, null, null, RICK_BERMAN_GUID, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null)

		then:
		staffResponse.page.totalElements == 1
		staffResponse.staff[0].guid == RICK_BERMAN_GUID
		staffResponse.staff[0].storyAuthoredMovieHeaders.size() == 4
		staffResponse.staff[0].producedMovieHeaders.size() == 4
		staffResponse.staff[0].movieHeaders.size() == 4
	}

	void "gets staff sorted by name"() {
		when:
		StaffResponse staffResponse = stapiRestClient.staffApi.staffPost(null, null,
				StapiRestSortSerializer.serialize(Lists.newArrayList(
						new RestSortClause(name: 'name', direction: RestSortDirection.ASC)
				)), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null, null)

		then:
		staffResponse.staff[0].name.startsWith('Aaron ')
		staffResponse.staff[1].name.startsWith('Aaron ')
		staffResponse.staff[2].name.startsWith('Aaron ')
	}

}
