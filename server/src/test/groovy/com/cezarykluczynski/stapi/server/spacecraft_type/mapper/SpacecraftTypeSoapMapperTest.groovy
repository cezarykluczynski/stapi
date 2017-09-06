package com.cezarykluczynski.stapi.server.spacecraft_type.mapper

import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftType as SoapSpacecraftType
import com.cezarykluczynski.stapi.model.spacecraft_type.entity.SpacecraftType
import org.mapstruct.factory.Mappers
import spock.lang.Specification

class SpacecraftTypeSoapMapperTest extends Specification {

	private static final String UID = 'UID'
	private static final String NAME = 'NAME'

	private SpacecraftTypeSoapMapper spacecraftTypeSoapMapper

	void setup() {
		spacecraftTypeSoapMapper = Mappers.getMapper(SpacecraftTypeSoapMapper)
	}

	void "maps db entity to REST entity"() {
		given:
		SpacecraftType spacecraftType = new SpacecraftType(
				uid: UID,
				name: NAME)

		when:
		SoapSpacecraftType soapSpacecraftType = spacecraftTypeSoapMapper.map(spacecraftType)

		then:
		soapSpacecraftType.uid == UID
		soapSpacecraftType.name == NAME
	}

}
