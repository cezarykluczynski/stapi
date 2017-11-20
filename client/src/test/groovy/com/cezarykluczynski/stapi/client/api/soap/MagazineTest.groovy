package com.cezarykluczynski.stapi.client.api.soap

import com.cezarykluczynski.stapi.client.v1.soap.MagazineBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.MagazineBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.MagazineFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.MagazineFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.MagazinePortType
import spock.lang.Specification

class MagazineTest extends Specification {

	private MagazinePortType magazinePortTypeMock

	private ApiKeySupplier apiKeySupplierMock

	private Magazine magazine

	void setup() {
		magazinePortTypeMock = Mock()
		apiKeySupplierMock = Mock()
		magazine = new Magazine(magazinePortTypeMock, apiKeySupplierMock)
	}

	void "gets single entity"() {
		given:
		MagazineBaseRequest magazineBaseRequest = Mock()
		MagazineBaseResponse magazineBaseResponse = Mock()

		when:
		MagazineBaseResponse magazineBaseResponseOutput = magazine.search(magazineBaseRequest)

		then:
		1 * apiKeySupplierMock.supply(magazineBaseRequest)
		1 * magazinePortTypeMock.getMagazineBase(magazineBaseRequest) >> magazineBaseResponse
		0 * _
		magazineBaseResponse == magazineBaseResponseOutput
	}

	void "searches entities"() {
		given:
		MagazineFullRequest magazineFullRequest = Mock()
		MagazineFullResponse magazineFullResponse = Mock()

		when:
		MagazineFullResponse magazineFullResponseOutput = magazine.get(magazineFullRequest)

		then:
		1 * apiKeySupplierMock.supply(magazineFullRequest)
		1 * magazinePortTypeMock.getMagazineFull(magazineFullRequest) >> magazineFullResponse
		0 * _
		magazineFullResponse == magazineFullResponseOutput
	}

}
