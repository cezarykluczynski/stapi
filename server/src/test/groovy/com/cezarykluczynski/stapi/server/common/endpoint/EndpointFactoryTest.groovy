package com.cezarykluczynski.stapi.server.common.endpoint

import com.cezarykluczynski.stapi.server.common.converter.LocalDateRestParamConverterProvider
import com.cezarykluczynski.stapi.server.common.throttle.rest.RestExceptionMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDExceptionMapper
import com.cezarykluczynski.stapi.server.configuration.CxfRestPrettyPrintContainerResponseFilter
import com.cezarykluczynski.stapi.server.configuration.interceptor.ApiThrottleLimitHeadersBindingInterceptor
import com.cezarykluczynski.stapi.server.configuration.interceptor.ApiThrottlingInterceptor
import com.cezarykluczynski.stapi.server.series.endpoint.SeriesRestEndpoint
import com.cezarykluczynski.stapi.server.series.endpoint.SeriesSoapEndpoint
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.cxf.Bus
import org.apache.cxf.bus.spring.SpringBus
import org.apache.cxf.endpoint.Server
import org.apache.cxf.endpoint.ServerImpl
import org.apache.cxf.jaxws.EndpointImpl
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharingFilter
import org.springframework.context.ApplicationContext
import spock.lang.Specification

import javax.xml.ws.Endpoint

class EndpointFactoryTest extends Specification {

	private ApplicationContext applicationContextMock

	private EndpointFactory endpointFactory

	void setup() {
		applicationContextMock = Mock()
		endpointFactory = new EndpointFactory(applicationContext: applicationContextMock)
	}

	void "creates SOAP endpoint"() {
		given:
		Bus bus = new SpringBus()
		Class<SeriesSoapEndpoint> seriesSoapEndpointClass = SeriesSoapEndpoint
		SeriesSoapEndpoint seriesSoapEndpoint = Mock()
		ApiThrottlingInterceptor apiThrottlingInterceptor = Mock()
		ApiThrottleLimitHeadersBindingInterceptor apiThrottleLimitHeadersBindingInterceptor = Mock()

		when:
		Endpoint seriesSoapEndpointOutput = endpointFactory.createSoapEndpoint(seriesSoapEndpointClass, SeriesSoapEndpoint.ADDRESS)

		then:
		1 * applicationContextMock.getBean(SpringBus) >> bus
		1 * applicationContextMock.getBean(seriesSoapEndpointClass) >> seriesSoapEndpoint
		1 * applicationContextMock.getBean(ApiThrottlingInterceptor) >> apiThrottlingInterceptor
		1 * applicationContextMock.getBean(ApiThrottleLimitHeadersBindingInterceptor) >> apiThrottleLimitHeadersBindingInterceptor
		0 * _
		seriesSoapEndpointOutput != null
		((EndpointImpl) seriesSoapEndpointOutput).implementor == seriesSoapEndpoint
		((EndpointImpl) seriesSoapEndpointOutput).bus == bus
		seriesSoapEndpointOutput.published
	}

	@SuppressWarnings(['EmptyCatchBlock', 'CatchException'])
	void "creates REST endpoint"() {
		given:
		Bus bus = new SpringBus()
		Class<SeriesRestEndpoint> seriesRestEndpointClass = SeriesRestEndpoint
		SeriesRestEndpoint seriesRestEndpoint = Mock()
		ObjectMapper objectMapper = Mock()
		CxfRestPrettyPrintContainerResponseFilter cxfRestPrettyPrintContainerResponseFilter = Mock()
		LocalDateRestParamConverterProvider localDateRestParamConverterProvider = Mock()
		RestExceptionMapper  restExceptionMapper = Mock()
		MissingUIDExceptionMapper missingUIDExceptionMapper = Mock()
		ApiThrottlingInterceptor apiThrottlingInterceptor = Mock()
		ApiThrottleLimitHeadersBindingInterceptor apiThrottleLimitHeadersBindingInterceptor = Mock()
		CrossOriginResourceSharingFilter crossOriginResourceSharingFilter = Mock()

		when:
		Server server = endpointFactory.createRestEndpoint(seriesRestEndpointClass, SeriesRestEndpoint.ADDRESS)

		then:
		1 * applicationContextMock.getBean(SeriesRestEndpoint) >> seriesRestEndpoint
		1 * applicationContextMock.getBean(SpringBus) >> bus
		1 * applicationContextMock.getBean(ObjectMapper) >> objectMapper
		1 * applicationContextMock.getBean(CxfRestPrettyPrintContainerResponseFilter) >> cxfRestPrettyPrintContainerResponseFilter
		1 * applicationContextMock.getBean(LocalDateRestParamConverterProvider) >> localDateRestParamConverterProvider
		1 * applicationContextMock.getBean(RestExceptionMapper) >> restExceptionMapper
		1 * applicationContextMock.getBean(CrossOriginResourceSharingFilter) >> crossOriginResourceSharingFilter
		1 * applicationContextMock.getBean(MissingUIDExceptionMapper) >> missingUIDExceptionMapper
		1 * applicationContextMock.getBean(ApiThrottlingInterceptor) >> apiThrottlingInterceptor
		1 * applicationContextMock.getBean(ApiThrottleLimitHeadersBindingInterceptor) >> apiThrottleLimitHeadersBindingInterceptor
		0 * _
		server != null
		((ServerImpl) server).bus == bus

		cleanup:
		try {
			server.destroy()
		} catch (Exception e) {
		}
	}

}
