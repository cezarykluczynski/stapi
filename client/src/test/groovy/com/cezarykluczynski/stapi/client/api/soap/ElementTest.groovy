package com.cezarykluczynski.stapi.client.api.soap

import com.cezarykluczynski.stapi.client.v1.soap.ElementBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.ElementBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.ElementFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.ElementFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.ElementPortType
import spock.lang.Specification

class ElementTest extends Specification {

	private ElementPortType elementPortTypeMock

	private ApiKeySupplier apiKeySupplierMock

	private Element element

	void setup() {
		elementPortTypeMock = Mock()
		apiKeySupplierMock = Mock()
		element = new Element(elementPortTypeMock, apiKeySupplierMock)
	}

	void "gets single entity"() {
		given:
		ElementBaseRequest elementBaseRequest = Mock()
		ElementBaseResponse elementBaseResponse = Mock()

		when:
		ElementBaseResponse elementBaseResponseOutput = element.search(elementBaseRequest)

		then:
		1 * apiKeySupplierMock.supply(elementBaseRequest)
		1 * elementPortTypeMock.getElementBase(elementBaseRequest) >> elementBaseResponse
		0 * _
		elementBaseResponse == elementBaseResponseOutput
	}

	void "searches entities"() {
		given:
		ElementFullRequest elementFullRequest = Mock()
		ElementFullResponse elementFullResponse = Mock()

		when:
		ElementFullResponse elementFullResponseOutput = element.get(elementFullRequest)

		then:
		1 * apiKeySupplierMock.supply(elementFullRequest)
		1 * elementPortTypeMock.getElementFull(elementFullRequest) >> elementFullResponse
		0 * _
		elementFullResponse == elementFullResponseOutput
	}

}
