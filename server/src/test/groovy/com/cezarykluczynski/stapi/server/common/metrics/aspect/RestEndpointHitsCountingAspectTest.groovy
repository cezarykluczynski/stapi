package com.cezarykluczynski.stapi.server.common.metrics.aspect

import com.cezarykluczynski.stapi.server.astronomical_object.endpoint.AstronomicalObjectRestEndpoint
import com.cezarykluczynski.stapi.server.common.metrics.filter.RestEndpointApiBrowserFilter
import com.cezarykluczynski.stapi.server.common.metrics.service.EndpointHitsCountingService
import org.apache.commons.lang3.RandomUtils
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.Signature
import spock.lang.Specification

class RestEndpointHitsCountingAspectTest extends Specification {

	private static final boolean API_BROWSER = RandomUtils.nextBoolean()

	private EndpointHitsCountingService endpointHitsCountingServiceMock

	private RestEndpointApiBrowserFilter restEndpointApiBrowserFilterMock

	private RestEndpointHitsCountingAspect restEndpointHitsCountingAspect

	void setup() {
		endpointHitsCountingServiceMock = Mock()
		restEndpointApiBrowserFilterMock = Mock()
		restEndpointHitsCountingAspect = new RestEndpointHitsCountingAspect(endpointHitsCountingServiceMock, restEndpointApiBrowserFilterMock)
	}

	void "passes endpoint name and method name to EndpointHitsCountingService"() {
		given:
		String methodName = 'searchAstronomicalObject'
		JoinPoint joinPoint = Mock()
		Signature signature = Mock()

		when:
		restEndpointHitsCountingAspect.restEndpointAdvice(joinPoint)

		then:
		1 * joinPoint.signature >> signature
		1 * signature.declaringType >> AstronomicalObjectRestEndpoint
		1 * signature.name >> methodName
		1 * restEndpointApiBrowserFilterMock.apiBrowser >> API_BROWSER
		1 * endpointHitsCountingServiceMock.recordEndpointHit(AstronomicalObjectRestEndpoint.simpleName, methodName, API_BROWSER)
		0 * _
	}

}
