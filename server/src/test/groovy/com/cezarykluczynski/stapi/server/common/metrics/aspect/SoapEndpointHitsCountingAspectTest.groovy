package com.cezarykluczynski.stapi.server.common.metrics.aspect

import com.cezarykluczynski.stapi.server.astronomical_object.endpoint.AstronomicalObjectSoapEndpoint
import com.cezarykluczynski.stapi.server.common.metrics.service.EndpointHitsCountingService
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.Signature
import spock.lang.Specification

class SoapEndpointHitsCountingAspectTest extends Specification {

	private EndpointHitsCountingService endpointHitsCountingServiceMock

	private SoapEndpointHitsCountingAspect soapEndpointHitsCountingAspect

	void setup() {
		endpointHitsCountingServiceMock = Mock()
		soapEndpointHitsCountingAspect = new SoapEndpointHitsCountingAspect(endpointHitsCountingServiceMock)
	}

	void "passes endpoint name and method name to EndpointHitsCountingService"() {
		given:
		String methodName = 'getAstronomicalObjectFull'
		JoinPoint joinPoint = Mock()
		Signature signature = Mock()

		when:
		soapEndpointHitsCountingAspect.soapEndpointAdvice(joinPoint)

		then:
		1 * joinPoint.signature >> signature
		1 * signature.declaringType >> AstronomicalObjectSoapEndpoint
		1 * signature.name >> methodName
		1 * endpointHitsCountingServiceMock.recordEndpointHit(AstronomicalObjectSoapEndpoint.simpleName, methodName)
		0 * _
	}

}
