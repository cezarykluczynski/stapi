package com.cezarykluczynski.stapi.client.api.soap

import com.cezarykluczynski.stapi.client.v1.soap.TechnologyBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.TechnologyBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.TechnologyFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.TechnologyFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.TechnologyPortType
import spock.lang.Specification

class TechnologyTest extends Specification {

	private TechnologyPortType technologyPortTypeMock

	private ApiKeySupplier apiKeySupplierMock

	private Technology technology

	void setup() {
		technologyPortTypeMock = Mock()
		apiKeySupplierMock = Mock()
		technology = new Technology(technologyPortTypeMock, apiKeySupplierMock)
	}

	void "gets single entity"() {
		given:
		TechnologyBaseRequest technologyBaseRequest = Mock()
		TechnologyBaseResponse technologyBaseResponse = Mock()

		when:
		TechnologyBaseResponse technologyBaseResponseOutput = technology.search(technologyBaseRequest)

		then:
		1 * apiKeySupplierMock.supply(technologyBaseRequest)
		1 * technologyPortTypeMock.getTechnologyBase(technologyBaseRequest) >> technologyBaseResponse
		0 * _
		technologyBaseResponse == technologyBaseResponseOutput
	}

	void "searches entities"() {
		given:
		TechnologyFullRequest technologyFullRequest = Mock()
		TechnologyFullResponse technologyFullResponse = Mock()

		when:
		TechnologyFullResponse technologyFullResponseOutput = technology.get(technologyFullRequest)

		then:
		1 * apiKeySupplierMock.supply(technologyFullRequest)
		1 * technologyPortTypeMock.getTechnologyFull(technologyFullRequest) >> technologyFullResponse
		0 * _
		technologyFullResponse == technologyFullResponseOutput
	}

}

