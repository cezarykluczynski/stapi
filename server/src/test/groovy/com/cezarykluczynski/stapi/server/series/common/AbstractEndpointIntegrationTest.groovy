package com.cezarykluczynski.stapi.server.series.common

import com.cezarykluczynski.stapi.client.api.StapiRestClient
import com.cezarykluczynski.stapi.client.api.StapiSoapClient
import com.cezarykluczynski.stapi.server.Application
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest(
		classes = [Application],
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles('default,custom')
abstract class AbstractEndpointIntegrationTest extends Specification {

	@LocalServerPort
	protected Integer localServerPost

	protected StapiRestClient stapiRestClient

	protected StapiSoapClient stapiSoapClient

	protected StapiRestClient createRestClient() {
		stapiRestClient = new StapiRestClient("http://localhost:${localServerPost}/stapi/")
	}

	protected StapiSoapClient createSoapClient() {
		stapiSoapClient = new StapiSoapClient("http://localhost:${localServerPost}/stapi/")
	}

}
