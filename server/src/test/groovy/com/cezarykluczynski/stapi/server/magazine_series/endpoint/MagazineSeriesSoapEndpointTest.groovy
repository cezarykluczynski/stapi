package com.cezarykluczynski.stapi.server.magazine_series.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesFullResponse
import com.cezarykluczynski.stapi.server.magazine_series.reader.MagazineSeriesSoapReader
import spock.lang.Specification

class MagazineSeriesSoapEndpointTest extends Specification {

	private MagazineSeriesSoapReader magazineSeriesSoapReaderMock

	private MagazineSeriesSoapEndpoint magazineSeriesSoapEndpoint

	void setup() {
		magazineSeriesSoapReaderMock = Mock()
		magazineSeriesSoapEndpoint = new MagazineSeriesSoapEndpoint(magazineSeriesSoapReaderMock)
	}

	void "passes base call to MagazineSeriesSoapReader"() {
		given:
		MagazineSeriesBaseRequest magazineSeriesBaseRequest = Mock()
		MagazineSeriesBaseResponse magazineSeriesBaseResponse = Mock()

		when:
		MagazineSeriesBaseResponse magazineSeriesResponseResult = magazineSeriesSoapEndpoint.getMagazineSeriesBase(magazineSeriesBaseRequest)

		then:
		1 * magazineSeriesSoapReaderMock.readBase(magazineSeriesBaseRequest) >> magazineSeriesBaseResponse
		magazineSeriesResponseResult == magazineSeriesBaseResponse
	}

	void "passes full call to MagazineSeriesSoapReader"() {
		given:
		MagazineSeriesFullRequest magazineSeriesFullRequest = Mock()
		MagazineSeriesFullResponse magazineSeriesFullResponse = Mock()

		when:
		MagazineSeriesFullResponse magazineSeriesResponseResult = magazineSeriesSoapEndpoint.getMagazineSeriesFull(magazineSeriesFullRequest)

		then:
		1 * magazineSeriesSoapReaderMock.readFull(magazineSeriesFullRequest) >> magazineSeriesFullResponse
		magazineSeriesResponseResult == magazineSeriesFullResponse
	}

}
