package com.cezarykluczynski.stapi.server.common.endpoint

import com.cezarykluczynski.stapi.server.configuration.interceptor.ApiThrottlingInterceptor
import com.cezarykluczynski.stapi.server.series.endpoint.SeriesSoapEndpoint
import org.apache.cxf.Bus
import org.apache.cxf.bus.spring.SpringBus
import org.apache.cxf.jaxws.EndpointImpl
import org.springframework.context.ApplicationContext
import spock.lang.Specification

import javax.xml.ws.Endpoint

class EndpointFactoryTest extends Specification {

	private static final String ADDRESS = '/v1/soap/address'

	private ApplicationContext applicationContextMock

	private EndpointFactory endpointFactory

	void setup() {
		applicationContextMock = Mock()
		endpointFactory = new EndpointFactory(applicationContext: applicationContextMock)
	}

	void "creates endpoint"() {
		given:
		Bus bus = new SpringBus()
		Class<SeriesSoapEndpoint> seriesSoapEndpointClass = SeriesSoapEndpoint
		SeriesSoapEndpoint seriesSoapEndpoint = Mock()
		ApiThrottlingInterceptor apiThrottlingInterceptor = Mock()

		when:
		Endpoint seriesSoapEndpointOutput = endpointFactory.createSoapEndpoint(seriesSoapEndpointClass, ADDRESS)

		then:
		1 * applicationContextMock.getBean(SpringBus) >> bus
		1 * applicationContextMock.getBean(seriesSoapEndpointClass) >> seriesSoapEndpoint
		1 * applicationContextMock.getBean(ApiThrottlingInterceptor) >> apiThrottlingInterceptor
		seriesSoapEndpointOutput != null
		((EndpointImpl) seriesSoapEndpointOutput).implementor == seriesSoapEndpoint
		((EndpointImpl) seriesSoapEndpointOutput).bus == bus
		seriesSoapEndpointOutput.published
	}

}
