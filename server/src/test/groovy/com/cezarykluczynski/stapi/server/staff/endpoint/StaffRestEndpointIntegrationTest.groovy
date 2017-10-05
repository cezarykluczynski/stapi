package com.cezarykluczynski.stapi.server.staff.endpoint

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer
import com.cezarykluczynski.stapi.client.api.dto.RestSortClause
import com.cezarykluczynski.stapi.client.api.dto.enums.RestSortDirection
import com.cezarykluczynski.stapi.client.v1.rest.model.StaffBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.StaffFullResponse
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

	void "gets staff with movie experience by UID"() {
		when:
		StaffFullResponse staffFullResponse = stapiRestClient.staffApi.staffGet(RICK_BERMAN_UID, null)

		then:
		staffFullResponse.staff.uid == RICK_BERMAN_UID
		staffFullResponse.staff.storyAuthoredMovies.size() == 4
		staffFullResponse.staff.producedMovies.size() == 4
		staffFullResponse.staff.movies.size() == 4
	}

	void "gets first page of staff"() {
		given:
		Integer pageNumber = 0
		Integer pageSize = 10

		when:
		StaffBaseResponse staffResponse = stapiRestClient.staffApi.staffSearchGet(pageNumber, pageSize, null)

		then:
		staffResponse.page.pageNumber == pageNumber
		staffResponse.page.pageSize == pageSize
		staffResponse.staff.size() == pageSize
	}

	void "gets staff with series experience by UID"() {
		when:
		StaffFullResponse staffFullResponse = stapiRestClient.staffApi.staffGet(IRA_STEVEN_BEHR_UID, null)

		then:
		staffFullResponse.staff.uid == IRA_STEVEN_BEHR_UID
		staffFullResponse.staff.writtenEpisodes.size() == 34
		staffFullResponse.staff.teleplayAuthoredEpisodes.size() == 18
		staffFullResponse.staff.storyAuthoredEpisodes.size() == 10
	}

	void "gets staff sorted by name"() {
		when:
		StaffBaseResponse staffResponse = stapiRestClient.staffApi.staffSearchPost(null, null,
				StapiRestSortSerializer.serialize(Lists.newArrayList(
						new RestSortClause(name: 'name', direction: RestSortDirection.ASC)
				)), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)

		then:
		staffResponse.staff[0].name.startsWith('Aaron ')
		staffResponse.staff[1].name.startsWith('Aaron ')
		staffResponse.staff[2].name.startsWith('Aaron ')
	}

}
