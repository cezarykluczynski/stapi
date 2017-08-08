package com.cezarykluczynski.stapi.server.platform.mapper

import com.cezarykluczynski.stapi.client.v1.soap.Platform as SoapPlatform
import com.cezarykluczynski.stapi.model.platform.entity.Platform
import org.mapstruct.factory.Mappers
import spock.lang.Specification

class PlatformSoapMapperTest extends Specification {

	private static final String UID = 'UID'
	private static final String NAME = 'NAME'

	private PlatformSoapMapper platformSoapMapper

	void setup() {
		platformSoapMapper = Mappers.getMapper(PlatformSoapMapper)
	}

	void "maps db entity to SOAP entity"() {
		given:
		Platform platform = new Platform(
				uid: UID,
				name: NAME)

		when:
		SoapPlatform soapPlatform = platformSoapMapper.map(platform)

		then:
		soapPlatform.uid == UID
		soapPlatform.name == NAME
	}

}
