package com.cezarykluczynski.stapi.server.magazine.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.MagazineBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.MagazineBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.MagazineFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.MagazineFullResponse
import com.cezarykluczynski.stapi.server.magazine.reader.MagazineSoapReader
import spock.lang.Specification

class MagazineSoapEndpointTest extends Specification {

	private MagazineSoapReader magazineSoapReaderMock

	private MagazineSoapEndpoint magazineSoapEndpoint

	void setup() {
		magazineSoapReaderMock = Mock()
		magazineSoapEndpoint = new MagazineSoapEndpoint(magazineSoapReaderMock)
	}

	void "passes base call to MagazineSoapReader"() {
		given:
		MagazineBaseRequest magazineBaseRequest = Mock()
		MagazineBaseResponse magazineBaseResponse = Mock()

		when:
		MagazineBaseResponse magazineResponseResult = magazineSoapEndpoint.getMagazineBase(magazineBaseRequest)

		then:
		1 * magazineSoapReaderMock.readBase(magazineBaseRequest) >> magazineBaseResponse
		magazineResponseResult == magazineBaseResponse
	}

	void "passes full call to MagazineSoapReader"() {
		given:
		MagazineFullRequest magazineFullRequest = Mock()
		MagazineFullResponse magazineFullResponse = Mock()

		when:
		MagazineFullResponse magazineResponseResult = magazineSoapEndpoint.getMagazineFull(magazineFullRequest)

		then:
		1 * magazineSoapReaderMock.readFull(magazineFullRequest) >> magazineFullResponse
		magazineResponseResult == magazineFullResponse
	}

}
