package com.cezarykluczynski.stapi.server.series.common

import com.cezarykluczynski.stapi.server.Application
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest(
		classes = [Application.class],
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("default")
class EndpointIntegrationTest extends Specification {
}
