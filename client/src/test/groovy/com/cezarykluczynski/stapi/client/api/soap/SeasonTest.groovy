package com.cezarykluczynski.stapi.client.api.soap

import com.cezarykluczynski.stapi.client.v1.soap.SeasonBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.SeasonBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.SeasonFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.SeasonFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.SeasonPortType
import spock.lang.Specification

class SeasonTest extends Specification {

	private SeasonPortType seasonPortTypeMock

	private ApiKeySupplier apiKeySupplierMock

	private Season season

	void setup() {
		seasonPortTypeMock = Mock()
		apiKeySupplierMock = Mock()
		season = new Season(seasonPortTypeMock, apiKeySupplierMock)
	}

	void "gets single entity"() {
		given:
		SeasonBaseRequest seasonBaseRequest = Mock()
		SeasonBaseResponse seasonBaseResponse = Mock()

		when:
		SeasonBaseResponse seasonBaseResponseOutput = season.search(seasonBaseRequest)

		then:
		1 * apiKeySupplierMock.supply(seasonBaseRequest)
		1 * seasonPortTypeMock.getSeasonBase(seasonBaseRequest) >> seasonBaseResponse
		0 * _
		seasonBaseResponse == seasonBaseResponseOutput
	}

	void "searches entities"() {
		given:
		SeasonFullRequest seasonFullRequest = Mock()
		SeasonFullResponse seasonFullResponse = Mock()

		when:
		SeasonFullResponse seasonFullResponseOutput = season.get(seasonFullRequest)

		then:
		1 * apiKeySupplierMock.supply(seasonFullRequest)
		1 * seasonPortTypeMock.getSeasonFull(seasonFullRequest) >> seasonFullResponse
		0 * _
		seasonFullResponse == seasonFullResponseOutput
	}

}
