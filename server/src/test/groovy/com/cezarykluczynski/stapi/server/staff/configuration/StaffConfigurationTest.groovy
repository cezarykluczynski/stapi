package com.cezarykluczynski.stapi.server.staff.configuration

import com.cezarykluczynski.stapi.server.staff.endpoint.StaffRestEndpoint
import com.cezarykluczynski.stapi.server.staff.endpoint.StaffSoapEndpoint
import com.cezarykluczynski.stapi.server.staff.mapper.StaffBaseRestMapper
import com.cezarykluczynski.stapi.server.staff.mapper.StaffBaseSoapMapper
import com.cezarykluczynski.stapi.server.staff.mapper.StaffFullRestMapper
import com.cezarykluczynski.stapi.server.staff.mapper.StaffFullSoapMapper
import com.cezarykluczynski.stapi.server.staff.reader.StaffRestReader
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

	void "StaffBaseSoapMapper is created"() {
		when:
		StaffBaseSoapMapper staffBaseSoapMapper = staffConfiguration.staffBaseSoapMapper()

		then:
		staffBaseSoapMapper != null
	}
	void "StaffFullSoapMapper is created"() {
		when:
		StaffFullSoapMapper staffFullSoapMapper = staffConfiguration.staffFullSoapMapper()

		then:
		staffFullSoapMapper != null
	}

	void "StaffBaseRestMapper is created"() {
		when:
		StaffBaseRestMapper staffBaseRestMapper = staffConfiguration.staffBaseRestMapper()

		then:
		staffBaseRestMapper != null
	}

	void "StaffFullRestMapper is created"() {
		when:
		StaffFullRestMapper staffFullRestMapper = staffConfiguration.staffFullRestMapper()

		then:
		staffFullRestMapper != null
	}

}
