package com.cezarykluczynski.stapi.server.species.configuration

import com.cezarykluczynski.stapi.server.species.endpoint.SpeciesRestEndpoint
import com.cezarykluczynski.stapi.server.species.endpoint.SpeciesSoapEndpoint
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesBaseRestMapper
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesBaseSoapMapper
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesFullRestMapper
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesFullSoapMapper
import com.cezarykluczynski.stapi.server.species.reader.SpeciesRestReader
import com.cezarykluczynski.stapi.server.species.reader.SpeciesSoapReader
import org.apache.cxf.bus.spring.SpringBus
import org.apache.cxf.jaxws.EndpointImpl
import org.springframework.context.ApplicationContext
import spock.lang.Specification

import javax.xml.ws.Endpoint

class SpeciesConfigurationTest extends Specification {

	private ApplicationContext applicationContextMock

	private SpeciesConfiguration speciesConfiguration

	void setup() {
		applicationContextMock = Mock(ApplicationContext)
		speciesConfiguration = new SpeciesConfiguration(applicationContext: applicationContextMock)
	}

	void "Species SOAP endpoint is created"() {
		given:
		SpringBus springBus = new SpringBus()
		SpeciesSoapReader speciesSoapReaderMock = Mock(SpeciesSoapReader)

		when:
		Endpoint speciesSoapEndpoint = speciesConfiguration.speciesSoapEndpoint()

		then:
		1 * applicationContextMock.getBean(SpringBus) >> springBus
		1 * applicationContextMock.getBean(SpeciesSoapReader) >> speciesSoapReaderMock
		0 * _
		speciesSoapEndpoint != null
		((EndpointImpl) speciesSoapEndpoint).implementor instanceof SpeciesSoapEndpoint
		((EndpointImpl) speciesSoapEndpoint).bus == springBus
		speciesSoapEndpoint.published
	}

	void "SpeciesRestEndpoint is created"() {
		given:
		SpeciesRestReader speciesRestMapper = Mock(SpeciesRestReader)

		when:
		SpeciesRestEndpoint speciesRestEndpoint = speciesConfiguration.speciesRestEndpoint()

		then:
		1 * applicationContextMock.getBean(SpeciesRestReader) >> speciesRestMapper
		0 * _
		speciesRestEndpoint != null
		speciesRestEndpoint.speciesRestReader == speciesRestMapper
	}

	void "SpeciesBaseSoapMapper is created"() {
		when:
		SpeciesBaseSoapMapper speciesBaseSoapMapper = speciesConfiguration.speciesBaseSoapMapper()

		then:
		speciesBaseSoapMapper != null
	}

	void "SpeciesFullSoapMapper is created"() {
		when:
		SpeciesFullSoapMapper speciesFullSoapMapper = speciesConfiguration.speciesFullSoapMapper()

		then:
		speciesFullSoapMapper != null
	}

	void "SpeciesBaseRestMapper is created"() {
		when:
		SpeciesBaseRestMapper speciesBaseRestMapper = speciesConfiguration.speciesBaseRestMapper()

		then:
		speciesBaseRestMapper != null
	}

	void "SpeciesFullRestMapper is created"() {
		when:
		SpeciesFullRestMapper speciesFullRestMapper = speciesConfiguration.speciesFullRestMapper()

		then:
		speciesFullRestMapper != null
	}

}
