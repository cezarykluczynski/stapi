package com.cezarykluczynski.stapi.server.common.endpoint

import com.cezarykluczynski.stapi.server.common.converter.LocalDateRestParamConverterProvider
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDExceptionMapper
import com.cezarykluczynski.stapi.server.configuration.CxfRestPrettyPrintContainerResponseFilter
import com.cezarykluczynski.stapi.server.series.endpoint.SeriesRestEndpoint
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.cxf.Bus
import org.apache.cxf.bus.spring.SpringBus
import org.apache.cxf.endpoint.Server
import org.apache.cxf.endpoint.ServerImpl
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharingFilter
import org.springframework.context.ApplicationContext
import spock.lang.Specification

class EndpointFactoryTest extends Specification {

	private ApplicationContext applicationContextMock

	private EndpointFactory endpointFactory

	void setup() {
		applicationContextMock = Mock()
		endpointFactory = new EndpointFactory(applicationContext: applicationContextMock)
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
		MissingUIDExceptionMapper missingUIDExceptionMapper = Mock()
		CrossOriginResourceSharingFilter crossOriginResourceSharingFilter = Mock()

		when:
		Server server = endpointFactory.createRestEndpoint(seriesRestEndpointClass, SeriesRestEndpoint.ADDRESS)

		then:
		1 * applicationContextMock.getBean(SeriesRestEndpoint) >> seriesRestEndpoint
		1 * applicationContextMock.getBean(SpringBus) >> bus
		1 * applicationContextMock.getBean(ObjectMapper) >> objectMapper
		1 * applicationContextMock.getBean(CxfRestPrettyPrintContainerResponseFilter) >> cxfRestPrettyPrintContainerResponseFilter
		1 * applicationContextMock.getBean(LocalDateRestParamConverterProvider) >> localDateRestParamConverterProvider
		1 * applicationContextMock.getBean(CrossOriginResourceSharingFilter) >> crossOriginResourceSharingFilter
		1 * applicationContextMock.getBean(MissingUIDExceptionMapper) >> missingUIDExceptionMapper
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
