package com.cezarykluczynski.stapi.server.common.endpoint

import com.cezarykluczynski.stapi.client.api.StapiRestClient
import com.cezarykluczynski.stapi.server.Application
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import spock.lang.Specification

@SpringBootTest(
		classes = [Application],
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles('default,stapi-custom')
@TestPropertySource('classpath:application-test.properties')
abstract class AbstractEndpointIntegrationTest extends Specification {

	private final Object lock = new Object()

	@LocalServerPort
	protected Integer localServerPost

	protected StapiRestClient stapiRestClient

	protected StapiRestClient createRestClient() {
		synchronized (lock) {
			if (stapiRestClient == null) {
				stapiRestClient = new StapiRestClient("http://localhost:${localServerPost}/")
			}
		}
	}

}
