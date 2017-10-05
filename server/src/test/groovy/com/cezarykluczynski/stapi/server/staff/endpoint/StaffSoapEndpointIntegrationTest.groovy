package com.cezarykluczynski.stapi.server.staff.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.client.v1.soap.StaffBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.StaffBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.StaffFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.StaffFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_STAFF)
})
class StaffSoapEndpointIntegrationTest extends AbstractStaffEndpointIntegrationTest {

	void setup() {
		createSoapClient()
	}

	void "gets staff by UID"() {
		when:
		StaffFullResponse staffResponse = stapiSoapClient.staffPortType.getStaffFull(new StaffFullRequest(
				uid: IRA_STEVEN_BEHR_UID
		))

		then:
		staffResponse.staff.uid == IRA_STEVEN_BEHR_UID
	}

	void "gets first page of staff"() {
		given:
		Integer pageNumber = 0
		Integer pageSize = 10

		when:
		StaffBaseResponse staffResponse = stapiSoapClient.staffPortType.getStaffBase(new StaffBaseRequest(
				page: new RequestPage(
						pageNumber: pageNumber,
						pageSize: pageSize)))

		then:
		staffResponse.page.pageNumber == pageNumber
		staffResponse.page.pageSize == pageSize
		staffResponse.staff.size() == pageSize
	}

}
