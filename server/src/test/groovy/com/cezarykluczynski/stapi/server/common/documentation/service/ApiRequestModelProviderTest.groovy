package com.cezarykluczynski.stapi.server.common.documentation.service

import com.cezarykluczynski.stapi.contract.documentation.dto.ApiRequestModelDTO
import com.cezarykluczynski.stapi.server.astronomicalObject.endpoint.AstronomicalObjectRestEndpoint
import com.cezarykluczynski.stapi.server.book.endpoint.BookSoapEndpoint
import com.cezarykluczynski.stapi.server.comics.endpoint.ComicsSoapEndpoint
import com.cezarykluczynski.stapi.server.common.endpoint.CommonRestEndpoint
import com.cezarykluczynski.stapi.server.common.throttle.rest.RestExceptionMapper
import com.google.common.collect.Maps
import org.springframework.context.ApplicationContext
import spock.lang.Specification

import javax.jws.WebService
import javax.ws.rs.Produces

class ApiRequestModelProviderTest extends Specification {

	private ApplicationContext applicationContextMock

	private ApiRequestModelProvider apiRequestModelProvider

	void setup() {
		applicationContextMock = Mock()
		apiRequestModelProvider = new ApiRequestModelProvider(applicationContextMock)
	}

	void "provides ApiRequestModelDTO"() {
		given:
		Map<String, Object> restEndpoints = Maps.newHashMap()
		Map<String, Object> soapEndpoints = Maps.newHashMap()
		CommonRestEndpoint commonRestEndpoint = Mock()
		AstronomicalObjectRestEndpoint astronomicalObjectRestEndpoint = Mock()
		RestExceptionMapper restExceptionMapper = Mock()
		BookSoapEndpoint bookSoapEndpoint = Mock()
		ComicsSoapEndpoint comicsSoapEndpoint = Mock()
		restEndpoints.put('CommonRestEndpoint', commonRestEndpoint)
		restEndpoints.put('AstronomicalObjectRestEndpoint', astronomicalObjectRestEndpoint)
		restEndpoints.put('RestExceptionMapper', restExceptionMapper)
		soapEndpoints.put('BookSoapEndpoint', bookSoapEndpoint)
		soapEndpoints.put('ComicsSoapEndpoint$$EnhancerBySpringCGLIB$$1135c91a', comicsSoapEndpoint)

		when:
		ApiRequestModelDTO apiRequestModelDTO = apiRequestModelProvider.provide()

		then:
		1 * applicationContextMock.getBeansWithAnnotation(Produces) >> restEndpoints
		1 * applicationContextMock.getBeansWithAnnotation(WebService) >> soapEndpoints
		apiRequestModelDTO.restRequests.size() == 1
		apiRequestModelDTO.restRequests.contains(AstronomicalObjectRestEndpoint)
		apiRequestModelDTO.soapRequests.size() == 2
		apiRequestModelDTO.soapRequests.contains(BookSoapEndpoint)
		apiRequestModelDTO.soapRequests.contains(ComicsSoapEndpoint)
	}

}
