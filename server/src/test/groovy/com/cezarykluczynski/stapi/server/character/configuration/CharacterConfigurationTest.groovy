package com.cezarykluczynski.stapi.server.character.configuration

import com.cezarykluczynski.stapi.server.character.endpoint.CharacterSoapEndpoint
import com.cezarykluczynski.stapi.server.character.mapper.CharacterRestMapper
import com.cezarykluczynski.stapi.server.character.mapper.CharacterSoapMapper
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

	void "character soap endpoint is created"() {
		given:
		SpringBus springBus = new SpringBus()
		CharacterSoapReader characterSoapReaderMock = Mock(CharacterSoapReader)

		when:
		Endpoint characterSoapEndpoint = characterConfiguration.characterSoapEndpoint()

		then:
		1 * applicationContextMock.getBean(SpringBus) >> springBus
		1 * applicationContextMock.getBean(CharacterSoapReader) >> characterSoapReaderMock
		characterSoapEndpoint != null
		((EndpointImpl) characterSoapEndpoint).implementor instanceof CharacterSoapEndpoint
		((EndpointImpl) characterSoapEndpoint).bus == springBus
		characterSoapEndpoint.published
	}

	void "CharacterSoapMapper is created"() {
		when:
		CharacterSoapMapper characterSoapMapper = characterConfiguration.characterSoapMapper()

		then:
		characterSoapMapper != null
	}

	void "CharacterRestMapper is created"() {
		when:
		CharacterRestMapper characterRestMapper = characterConfiguration.characterRestMapper()

		then:
		characterRestMapper != null
	}

}
