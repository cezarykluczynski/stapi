package com.cezarykluczynski.stapi.client.api.soap

import com.cezarykluczynski.stapi.client.v1.soap.OccupationBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.OccupationBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.OccupationFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.OccupationFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.OccupationPortType
import spock.lang.Specification

class OccupationTest extends Specification {

	private OccupationPortType occupationPortTypeMock

	private ApiKeySupplier apiKeySupplierMock

	private Occupation occupation

	void setup() {
		occupationPortTypeMock = Mock()
		apiKeySupplierMock = Mock()
		occupation = new Occupation(occupationPortTypeMock, apiKeySupplierMock)
	}

	void "gets single entity"() {
		given:
		OccupationBaseRequest occupationBaseRequest = Mock()
		OccupationBaseResponse occupationBaseResponse = Mock()

		when:
		OccupationBaseResponse occupationBaseResponseOutput = occupation.search(occupationBaseRequest)

		then:
		1 * apiKeySupplierMock.supply(occupationBaseRequest)
		1 * occupationPortTypeMock.getOccupationBase(occupationBaseRequest) >> occupationBaseResponse
		0 * _
		occupationBaseResponse == occupationBaseResponseOutput
	}

	void "searches entities"() {
		given:
		OccupationFullRequest occupationFullRequest = Mock()
		OccupationFullResponse occupationFullResponse = Mock()

		when:
		OccupationFullResponse occupationFullResponseOutput = occupation.get(occupationFullRequest)

		then:
		1 * apiKeySupplierMock.supply(occupationFullRequest)
		1 * occupationPortTypeMock.getOccupationFull(occupationFullRequest) >> occupationFullResponse
		0 * _
		occupationFullResponse == occupationFullResponseOutput
	}

}
