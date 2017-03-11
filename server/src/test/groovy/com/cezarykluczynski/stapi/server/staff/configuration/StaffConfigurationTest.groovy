package com.cezarykluczynski.stapi.server.staff.configuration

import com.cezarykluczynski.stapi.server.staff.endpoint.StaffRestEndpoint
import com.cezarykluczynski.stapi.server.staff.reader.StaffRestReader
import com.cezarykluczynski.stapi.server.staff.endpoint.StaffSoapEndpoint
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

	void setup() {
		applicationContextMock = Mock(ApplicationContext)
		staffConfiguration = new StaffConfiguration(applicationContext: applicationContextMock)
	}

	void "Staff SOAP endpoint is created"() {
		given:
		SpringBus springBus = new SpringBus()
		StaffSoapReader staffSoapReaderMock = Mock(StaffSoapReader)

		when:
		Endpoint staffSoapEndpoint = staffConfiguration.staffSoapEndpoint()

		then:
		1 * applicationContextMock.getBean(SpringBus) >> springBus
		1 * applicationContextMock.getBean(StaffSoapReader) >> staffSoapReaderMock
		0 * _
		staffSoapEndpoint != null
		((EndpointImpl) staffSoapEndpoint).implementor instanceof StaffSoapEndpoint
		((EndpointImpl) staffSoapEndpoint).bus == springBus
		staffSoapEndpoint.published
	}

	void "StaffRestEndpoint is created"() {
		given:
		StaffRestReader staffRestMapper = Mock(StaffRestReader)

		when:
		StaffRestEndpoint staffRestEndpoint = staffConfiguration.staffRestEndpoint()

		then:
		1 * applicationContextMock.getBean(StaffRestReader) >> staffRestMapper
		0 * _
		staffRestEndpoint != null
		staffRestEndpoint.staffRestReader == staffRestMapper
	}

	void "StaffSoapMapper is created"() {
		when:
		StaffSoapMapper staffSoapMapper = staffConfiguration.staffSoapMapper()

		then:
		staffSoapMapper != null
	}

	void "StaffRestMapper is created"() {
		when:
		StaffRestMapper staffRestMapper = staffConfiguration.staffRestMapper()

		then:
		staffRestMapper != null
	}

}
