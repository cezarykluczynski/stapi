package com.cezarykluczynski.stapi.server.character.configuration

import com.cezarykluczynski.stapi.server.character.endpoint.CharacterRestEndpoint
import com.cezarykluczynski.stapi.server.character.endpoint.CharacterSoapEndpoint
import com.cezarykluczynski.stapi.server.character.mapper.CharacterBaseRestMapper
import com.cezarykluczynski.stapi.server.character.mapper.CharacterBaseSoapMapper
import com.cezarykluczynski.stapi.server.character.mapper.CharacterFullRestMapper
import com.cezarykluczynski.stapi.server.character.mapper.CharacterFullSoapMapper
import com.cezarykluczynski.stapi.server.character.reader.CharacterRestReader
import com.cezarykluczynski.stapi.server.character.reader.CharacterSoapReader
import org.apache.cxf.bus.spring.SpringBus
import org.apache.cxf.jaxws.EndpointImpl
import org.springframework.context.ApplicationContext
import spock.lang.Specification

import javax.xml.ws.Endpoint

class CharacterConfigurationTest extends Specification {

	private ApplicationContext applicationContextMock

	private CharacterConfiguration characterConfiguration

	void setup() {
		applicationContextMock = Mock(ApplicationContext)
		characterConfiguration = new CharacterConfiguration(applicationContext: applicationContextMock)
	}

	void "Character SOAP endpoint is created"() {
		given:
		SpringBus springBus = new SpringBus()
		CharacterSoapReader characterSoapReaderMock = Mock(CharacterSoapReader)

		when:
		Endpoint characterSoapEndpoint = characterConfiguration.characterSoapEndpoint()

		then:
		1 * applicationContextMock.getBean(SpringBus) >> springBus
		1 * applicationContextMock.getBean(CharacterSoapReader) >> characterSoapReaderMock
		0 * _
		characterSoapEndpoint != null
		((EndpointImpl) characterSoapEndpoint).implementor instanceof CharacterSoapEndpoint
		((EndpointImpl) characterSoapEndpoint).bus == springBus
		characterSoapEndpoint.published
	}

	void "CharacterRestEndpoint is created"() {
		given:
		CharacterRestReader characterRestMapper = Mock(CharacterRestReader)

		when:
		CharacterRestEndpoint characterRestEndpoint = characterConfiguration.characterRestEndpoint()

		then:
		1 * applicationContextMock.getBean(CharacterRestReader) >> characterRestMapper
		0 * _
		characterRestEndpoint != null
		characterRestEndpoint.characterRestReader == characterRestMapper
	}

	void "CharacterBaseSoapMapper is created"() {
		when:
		CharacterBaseSoapMapper characterBaseSoapMapper = characterConfiguration.characterBaseSoapMapper()

		then:
		characterBaseSoapMapper != null
	}

	void "CharacterFullSoapMapper is created"() {
		when:
		CharacterFullSoapMapper characterFullSoapMapper = characterConfiguration.characterFullSoapMapper()

		then:
		characterFullSoapMapper != null
	}

	void "CharacterBaseRestMapper is created"() {
		when:
		CharacterBaseRestMapper characterBaseRestMapper = characterConfiguration.characterBaseRestMapper()

		then:
		characterBaseRestMapper != null
	}

	void "CharacterFullRestMapper is created"() {
		when:
		CharacterFullRestMapper characterFullRestMapper = characterConfiguration.characterFullRestMapper()

		then:
		characterFullRestMapper != null
	}

}
