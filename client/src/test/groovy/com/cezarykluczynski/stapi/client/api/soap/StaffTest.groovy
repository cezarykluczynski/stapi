package com.cezarykluczynski.stapi.client.api.soap

import com.cezarykluczynski.stapi.client.v1.soap.StaffBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.StaffBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.StaffFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.StaffFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.StaffPortType
import spock.lang.Specification

class StaffTest extends Specification {

	private StaffPortType staffPortTypeMock

	private ApiKeySupplier apiKeySupplierMock

	private Staff staff

	void setup() {
		staffPortTypeMock = Mock()
		apiKeySupplierMock = Mock()
		staff = new Staff(staffPortTypeMock, apiKeySupplierMock)
	}

	void "gets single entity"() {
		given:
		StaffBaseRequest staffBaseRequest = Mock()
		StaffBaseResponse staffBaseResponse = Mock()

		when:
		StaffBaseResponse staffBaseResponseOutput = staff.search(staffBaseRequest)

		then:
		1 * apiKeySupplierMock.supply(staffBaseRequest)
		1 * staffPortTypeMock.getStaffBase(staffBaseRequest) >> staffBaseResponse
		0 * _
		staffBaseResponse == staffBaseResponseOutput
	}

	void "searches entities"() {
		given:
		StaffFullRequest staffFullRequest = Mock()
		StaffFullResponse staffFullResponse = Mock()

		when:
		StaffFullResponse staffFullResponseOutput = staff.get(staffFullRequest)

		then:
		1 * apiKeySupplierMock.supply(staffFullRequest)
		1 * staffPortTypeMock.getStaffFull(staffFullRequest) >> staffFullResponse
		0 * _
		staffFullResponse == staffFullResponseOutput
	}

}
