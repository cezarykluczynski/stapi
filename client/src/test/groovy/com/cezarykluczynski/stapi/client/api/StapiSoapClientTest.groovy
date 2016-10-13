package com.cezarykluczynski.stapi.client.api

import spock.lang.Specification

class StapiSoapClientTest extends Specification {

	def "soap client is constructed using port types provider"() {
		when:
		new StapiSoapClient(Mock(StapiSoapPortTypesProvider))

		then:
		notThrown(Throwable)
	}

}