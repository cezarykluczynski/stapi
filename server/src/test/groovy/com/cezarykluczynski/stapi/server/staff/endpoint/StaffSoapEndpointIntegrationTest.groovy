package com.cezarykluczynski.stapi.server.staff.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.client.v1.soap.StaffRequest
import com.cezarykluczynski.stapi.client.v1.soap.StaffResponse

class StaffSoapEndpointIntegrationTest extends AbstractStaffEndpointIntegrationTest {

	def setup() {
		createSoapClient()
	}

	def "gets first page of staff"() {
		given:
		Integer pageNumber = 0
		Integer pageSize = 10

		when:
		StaffResponse staffResponse = stapiSoapClient.staffPortType.getStaff(new StaffRequest(
				page: new RequestPage(
						pageNumber: pageNumber,
						pageSize: pageSize)))

		then:
		staffResponse.page.pageNumber == pageNumber
		staffResponse.page.pageSize == pageSize
		staffResponse.staff.size() == pageSize
	}

	def "gets staff by guid"() {
		when:
		StaffResponse staffResponse = stapiSoapClient.staffPortType.getStaff(new StaffRequest(
				guid: GUID
		))

		then:
		staffResponse.page.totalElements == 1
		staffResponse.staff[0].guid == GUID
	}

}
