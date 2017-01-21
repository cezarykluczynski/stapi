package com.cezarykluczynski.stapi.server.staff.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.StaffRequest
import com.cezarykluczynski.stapi.client.v1.soap.StaffResponse
import com.cezarykluczynski.stapi.server.staff.reader.StaffSoapReader
import spock.lang.Specification

class StaffSoapEndpointTest extends Specification {

	private StaffSoapReader staffSoapReaderMock

	private StaffSoapEndpoint staffSoapEndpoint

	void setup() {
		staffSoapReaderMock = Mock(StaffSoapReader)
		staffSoapEndpoint = new StaffSoapEndpoint(staffSoapReaderMock)
	}

	void "passes call to StaffSoapReader"() {
		given:
		StaffRequest staffRequest = Mock(StaffRequest)
		StaffResponse staffResponse = Mock(StaffResponse)

		when:
		StaffResponse staffResponseResult = staffSoapEndpoint.getStaff(staffRequest)

		then:
		1 * staffSoapReaderMock.read(staffRequest) >> staffResponse
		staffResponseResult == staffResponse
	}

}
