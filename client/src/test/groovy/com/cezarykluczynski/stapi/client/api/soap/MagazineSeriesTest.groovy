package com.cezarykluczynski.stapi.client.api.soap

import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesPortType
import spock.lang.Specification

class MagazineSeriesTest extends Specification {

	private MagazineSeriesPortType magazineSeriesPortTypeMock

	private ApiKeySupplier apiKeySupplierMock

	private MagazineSeries magazineSeries

	void setup() {
		magazineSeriesPortTypeMock = Mock()
		apiKeySupplierMock = Mock()
		magazineSeries = new MagazineSeries(magazineSeriesPortTypeMock, apiKeySupplierMock)
	}

	void "gets single entity"() {
		given:
		MagazineSeriesBaseRequest magazineSeriesBaseRequest = Mock()
		MagazineSeriesBaseResponse magazineSeriesBaseResponse = Mock()

		when:
		MagazineSeriesBaseResponse magazineSeriesBaseResponseOutput = magazineSeries.search(magazineSeriesBaseRequest)

		then:
		1 * apiKeySupplierMock.supply(magazineSeriesBaseRequest)
		1 * magazineSeriesPortTypeMock.getMagazineSeriesBase(magazineSeriesBaseRequest) >> magazineSeriesBaseResponse
		0 * _
		magazineSeriesBaseResponse == magazineSeriesBaseResponseOutput
	}

	void "searches entities"() {
		given:
		MagazineSeriesFullRequest magazineSeriesFullRequest = Mock()
		MagazineSeriesFullResponse magazineSeriesFullResponse = Mock()

		when:
		MagazineSeriesFullResponse magazineSeriesFullResponseOutput = magazineSeries.get(magazineSeriesFullRequest)

		then:
		1 * apiKeySupplierMock.supply(magazineSeriesFullRequest)
		1 * magazineSeriesPortTypeMock.getMagazineSeriesFull(magazineSeriesFullRequest) >> magazineSeriesFullResponse
		0 * _
		magazineSeriesFullResponse == magazineSeriesFullResponseOutput
	}

}
