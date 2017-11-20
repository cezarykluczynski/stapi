package com.cezarykluczynski.stapi.client.api.soap

import com.cezarykluczynski.stapi.client.v1.soap.LiteratureBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.LiteratureBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.LiteratureFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.LiteratureFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.LiteraturePortType
import spock.lang.Specification

class LiteratureTest extends Specification {

	private LiteraturePortType literaturePortTypeMock

	private ApiKeySupplier apiKeySupplierMock

	private Literature literature

	void setup() {
		literaturePortTypeMock = Mock()
		apiKeySupplierMock = Mock()
		literature = new Literature(literaturePortTypeMock, apiKeySupplierMock)
	}

	void "gets single entity"() {
		given:
		LiteratureBaseRequest literatureBaseRequest = Mock()
		LiteratureBaseResponse literatureBaseResponse = Mock()

		when:
		LiteratureBaseResponse literatureBaseResponseOutput = literature.search(literatureBaseRequest)

		then:
		1 * apiKeySupplierMock.supply(literatureBaseRequest)
		1 * literaturePortTypeMock.getLiteratureBase(literatureBaseRequest) >> literatureBaseResponse
		0 * _
		literatureBaseResponse == literatureBaseResponseOutput
	}

	void "searches entities"() {
		given:
		LiteratureFullRequest literatureFullRequest = Mock()
		LiteratureFullResponse literatureFullResponse = Mock()

		when:
		LiteratureFullResponse literatureFullResponseOutput = literature.get(literatureFullRequest)

		then:
		1 * apiKeySupplierMock.supply(literatureFullRequest)
		1 * literaturePortTypeMock.getLiteratureFull(literatureFullRequest) >> literatureFullResponse
		0 * _
		literatureFullResponse == literatureFullResponseOutput
	}

}
