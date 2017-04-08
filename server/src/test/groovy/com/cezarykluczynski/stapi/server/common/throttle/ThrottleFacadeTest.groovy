package com.cezarykluczynski.stapi.server.common.throttle

import com.cezarykluczynski.stapi.server.common.throttle.rest.RestException
import org.apache.cxf.binding.soap.SoapFault
import org.apache.cxf.message.Message
import spock.lang.Specification

class ThrottleFacadeTest extends Specification {

	private static final ThrottleReason THROTTLE_REASON = ThrottleReason.HOURLY_IP_LIMIT_EXCEEDED

	private ThrottleValidator throttleValidatorMock

	private ThrottleFacade throttleFacade

	void setup() {
		throttleValidatorMock = Mock()
		throttleFacade = new ThrottleFacade(throttleValidatorMock)
	}

	void "does not throw exception when no throttle should be performed"() {
		given:
		Message message = Mock()

		when:
		throttleFacade.validate(message)

		then:
		1 * throttleValidatorMock.validate(message) >> new ThrottleResult(throttle: false)
		0 * _
		notThrown(RuntimeException)
	}

	void "throws SoapFault for SOAP endpoint"() {
		given:
		Message message = Mock()

		when:
		throttleFacade.validate(message)

		then:
		1 * throttleValidatorMock.validate(message) >> new ThrottleResult(throttle: true, throttleReason: THROTTLE_REASON)
		1 * message.get(ThrottleFacade.PATH_INFO) >> 'some/soap/endpoint'
		0 * _
		SoapFault soapFault = thrown(SoapFault)
		soapFault.faultCode.localPart == THROTTLE_REASON.name()
	}

	void "throws RestException for SOAP endpoint"() {
		given:
		Message message = Mock()

		when:
		throttleFacade.validate(message)

		then:
		1 * throttleValidatorMock.validate(message) >> new ThrottleResult(throttle: true, throttleReason: THROTTLE_REASON)
		2 * message.get(ThrottleFacade.PATH_INFO) >> 'some/rest/endpoint'
		0 * _
		RestException restException = thrown(RestException)
		restException.throttleReason == THROTTLE_REASON
	}

}
