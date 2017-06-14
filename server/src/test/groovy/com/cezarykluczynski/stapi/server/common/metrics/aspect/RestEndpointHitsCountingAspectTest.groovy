package com.cezarykluczynski.stapi.server.common.metrics.aspect

import com.cezarykluczynski.stapi.server.astronomical_object.endpoint.AstronomicalObjectRestEndpoint
import com.cezarykluczynski.stapi.server.common.metrics.service.EndpointHitsCountingService
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.Signature
import spock.lang.Specification

class RestEndpointHitsCountingAspectTest extends Specification {

	private EndpointHitsCountingService endpointHitsCountingServiceMock

	private RestEndpointHitsCountingAspect restEndpointHitsCountingAspect

	void setup() {
		endpointHitsCountingServiceMock = Mock()
		restEndpointHitsCountingAspect = new RestEndpointHitsCountingAspect(endpointHitsCountingServiceMock)
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
		1 * endpointHitsCountingServiceMock.recordEndpointHit(AstronomicalObjectRestEndpoint.simpleName, methodName)
		0 * _
	}

}
