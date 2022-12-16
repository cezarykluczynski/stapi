package com.cezarykluczynski.stapi.client.api.soap

import com.cezarykluczynski.stapi.client.v1.soap.SeriesBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.SeriesBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.SeriesFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.SeriesFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.SeriesPortType
import spock.lang.Specification

class SeriesTest extends Specification {

	private SeriesPortType seriesPortTypeMock

	private Series series

	void setup() {
		seriesPortTypeMock = Mock()
		series = new Series(seriesPortTypeMock)
	}

	void "gets single entity"() {
		given:
		SeriesBaseRequest seriesBaseRequest = Mock()
		SeriesBaseResponse seriesBaseResponse = Mock()

		when:
		SeriesBaseResponse seriesBaseResponseOutput = series.search(seriesBaseRequest)

		then:
		1 * seriesPortTypeMock.getSeriesBase(seriesBaseRequest) >> seriesBaseResponse
		0 * _
		seriesBaseResponse == seriesBaseResponseOutput
	}

	void "searches entities"() {
		given:
		SeriesFullRequest seriesFullRequest = Mock()
		SeriesFullResponse seriesFullResponse = Mock()

		when:
		SeriesFullResponse seriesFullResponseOutput = series.get(seriesFullRequest)

		then:
		1 * seriesPortTypeMock.getSeriesFull(seriesFullRequest) >> seriesFullResponse
		0 * _
		seriesFullResponse == seriesFullResponseOutput
	}

}
