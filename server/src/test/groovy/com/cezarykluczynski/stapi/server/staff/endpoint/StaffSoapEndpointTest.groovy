package com.cezarykluczynski.stapi.server.staff.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.StaffBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.StaffBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.StaffFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.StaffFullResponse
import com.cezarykluczynski.stapi.server.staff.reader.StaffSoapReader
import spock.lang.Specification

class StaffSoapEndpointTest extends Specification {

	private StaffSoapReader staffSoapReaderMock

	private StaffSoapEndpoint staffSoapEndpoint

	void setup() {
		staffSoapReaderMock = Mock()
		staffSoapEndpoint = new StaffSoapEndpoint(staffSoapReaderMock)
	}

	void "passes base call to StaffSoapReader"() {
		given:
		StaffBaseRequest staffBaseRequest = Mock()
		StaffBaseResponse staffBaseResponse = Mock()

		when:
		StaffBaseResponse staffResponseResult = staffSoapEndpoint.getStaffBase(staffBaseRequest)

		then:
		1 * staffSoapReaderMock.readBase(staffBaseRequest) >> staffBaseResponse
		staffResponseResult == staffBaseResponse
	}

	void "passes full call to StaffSoapReader"() {
		given:
		StaffFullRequest staffFullRequest = Mock()
		StaffFullResponse staffFullResponse = Mock()

		when:
		StaffFullResponse staffResponseResult = staffSoapEndpoint.getStaffFull(staffFullRequest)

		then:
		1 * staffSoapReaderMock.readFull(staffFullRequest) >> staffFullResponse
		staffResponseResult == staffFullResponse
	}

}
