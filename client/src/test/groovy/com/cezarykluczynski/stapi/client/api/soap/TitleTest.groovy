package com.cezarykluczynski.stapi.client.api.soap

import com.cezarykluczynski.stapi.client.v1.soap.TitleBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.TitleBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.TitleFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.TitleFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.TitlePortType
import spock.lang.Specification

class TitleTest extends Specification {

	private TitlePortType titlePortTypeMock

	private ApiKeySupplier apiKeySupplierMock

	private Title title

	void setup() {
		titlePortTypeMock = Mock()
		apiKeySupplierMock = Mock()
		title = new Title(titlePortTypeMock, apiKeySupplierMock)
	}

	void "gets single entity"() {
		given:
		TitleBaseRequest titleBaseRequest = Mock()
		TitleBaseResponse titleBaseResponse = Mock()

		when:
		TitleBaseResponse titleBaseResponseOutput = title.search(titleBaseRequest)

		then:
		1 * apiKeySupplierMock.supply(titleBaseRequest)
		1 * titlePortTypeMock.getTitleBase(titleBaseRequest) >> titleBaseResponse
		0 * _
		titleBaseResponse == titleBaseResponseOutput
	}

	void "searches entities"() {
		given:
		TitleFullRequest titleFullRequest = Mock()
		TitleFullResponse titleFullResponse = Mock()

		when:
		TitleFullResponse titleFullResponseOutput = title.get(titleFullRequest)

		then:
		1 * apiKeySupplierMock.supply(titleFullRequest)
		1 * titlePortTypeMock.getTitleFull(titleFullRequest) >> titleFullResponse
		0 * _
		titleFullResponse == titleFullResponseOutput
	}

}
