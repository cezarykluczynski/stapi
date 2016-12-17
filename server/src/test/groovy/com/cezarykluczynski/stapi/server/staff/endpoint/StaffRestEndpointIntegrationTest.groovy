package com.cezarykluczynski.stapi.server.staff.endpoint

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer
import com.cezarykluczynski.stapi.client.api.dto.RestSortClause
import com.cezarykluczynski.stapi.client.api.dto.enums.RestSortOrder
import com.cezarykluczynski.stapi.client.v1.rest.model.StaffResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.google.common.collect.Lists
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_STAFF)
})
class StaffRestEndpointIntegrationTest extends AbstractStaffEndpointIntegrationTest {

	def setup() {
		createRestClient()
	}

	def "gets first page of staff"() {
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

	def "gets staff by guid"() {
		when:
		StaffResponse staffResponse = stapiRestClient.staffApi.staffPost(null, null, null, GUID, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null)

		then:
		staffResponse.page.totalElements == 1
		staffResponse.staff[0].guid == GUID
	}

	def "gets staff sorted by name"() {
		when:
		StaffResponse staffResponse = stapiRestClient.staffApi.staffPost(null, null,
				StapiRestSortSerializer.serialize(Lists.newArrayList(
						new RestSortClause(name: 'name', sortOrder: RestSortOrder.ASC)
				)), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null, null)

		then:
		staffResponse.staff[0].name.startsWith("Aaron ")
		staffResponse.staff[1].name.startsWith("Aaron ")
		staffResponse.staff[2].name.startsWith("Aaron ")
	}

}
