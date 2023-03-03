package com.cezarykluczynski.stapi.server.common.endpoint

import com.cezarykluczynski.stapi.client.api.StapiRestClient
import com.cezarykluczynski.stapi.server.Application
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import spock.lang.Specification

@SpringBootTest(
		classes = [Application],
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ContextConfiguration
@ActiveProfiles('default')
@TestPropertySource(
		// additional properties, like custom DB URL from application-stapi-custom.properties, can be put into this git-ignored file
		locations = 'classpath:application-test.properties'
)
@TestPropertySource(
		properties = ['spring.batch.job.enabled=false', 'spring.liquibase.enabled=false']
)
class AbstractEndpointIntegrationTest extends Specification {

	private final Object lock = new Object()

	@LocalServerPort
	protected Integer localServerPost

	protected StapiRestClient stapiRestClient

	void setup() {
		synchronized (lock) {
			if (stapiRestClient == null) {
				stapiRestClient = new StapiRestClient("http://localhost:${localServerPost}/")
				stapiRestClient.apiClient.setConnectTimeout(60000)
				stapiRestClient
			}
		}
	}

}
