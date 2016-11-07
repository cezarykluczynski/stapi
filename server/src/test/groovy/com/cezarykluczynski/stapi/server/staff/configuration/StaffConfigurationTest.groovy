package com.cezarykluczynski.stapi.server.staff.configuration

import com.cezarykluczynski.stapi.server.staff.endpoint.StaffSoapEndpoint
import com.cezarykluczynski.stapi.server.staff.mapper.StaffRequestMapper
import com.cezarykluczynski.stapi.server.staff.mapper.StaffRestMapper
import com.cezarykluczynski.stapi.server.staff.mapper.StaffSoapMapper
import com.cezarykluczynski.stapi.server.staff.reader.StaffSoapReader
import org.apache.cxf.bus.spring.SpringBus
import org.apache.cxf.jaxws.EndpointImpl
import org.springframework.context.ApplicationContext
import spock.lang.Specification

import javax.xml.ws.Endpoint

class StaffConfigurationTest extends Specification {

	private ApplicationContext applicationContextMock

	private StaffConfiguration staffConfiguration

	def setup() {
		applicationContextMock = Mock(ApplicationContext)
		staffConfiguration = new StaffConfiguration()
		staffConfiguration.applicationContext = applicationContextMock
	}

	def "staff soap endpoint is created"() {
		given:
		SpringBus springBus = new SpringBus()
		StaffSoapReader staffSoapReaderMock = Mock(StaffSoapReader)

		when:
		Endpoint staffSoapEndpoint = staffConfiguration.staffSoapEndpoint()

		then:
		1 * applicationContextMock.getBean(SpringBus.class) >> springBus
		1 * applicationContextMock.getBean(StaffSoapReader.class) >> staffSoapReaderMock
		staffSoapEndpoint != null
		((EndpointImpl) staffSoapEndpoint).implementor instanceof StaffSoapEndpoint
		((EndpointImpl) staffSoapEndpoint).bus == springBus
		staffSoapEndpoint.published
	}

	def "StaffRequestMapper is created"() {
		when:
		StaffRequestMapper staffRequestMapper = staffConfiguration.staffRequestMapper()

		then:
		staffRequestMapper != null
	}

	def "StaffSoapMapper is created"() {
		when:
		StaffSoapMapper staffSoapMapper = staffConfiguration.staffSoapMapper()

		then:
		staffSoapMapper != null
	}

	def "StaffRestMapper is created"() {
		when:
		StaffRestMapper staffRestMapper = staffConfiguration.staffRestMapper()

		then:
		staffRestMapper != null
	}

}
