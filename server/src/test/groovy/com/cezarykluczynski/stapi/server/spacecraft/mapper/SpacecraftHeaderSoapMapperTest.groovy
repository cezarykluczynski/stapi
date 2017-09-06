package com.cezarykluczynski.stapi.server.spacecraft.mapper

import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftHeader
import com.cezarykluczynski.stapi.model.spacecraft.entity.Spacecraft
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class SpacecraftHeaderSoapMapperTest extends AbstractSpacecraftMapperTest {

	private SpacecraftHeaderSoapMapper spacecraftHeaderSoapMapper

	void setup() {
		spacecraftHeaderSoapMapper = Mappers.getMapper(SpacecraftHeaderSoapMapper)
	}

	void "maps DB entity to SOAP header"() {
		given:
		Spacecraft spacecraft = new Spacecraft(
				uid: UID,
				name: NAME)

		when:
		SpacecraftHeader spacecraftHeader = spacecraftHeaderSoapMapper.map(Lists.newArrayList(spacecraft))[0]

		then:
		spacecraftHeader.uid == UID
		spacecraftHeader.name == NAME
	}

}
