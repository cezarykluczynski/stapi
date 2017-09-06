package com.cezarykluczynski.stapi.server.spacecraft_type.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftType as RestSpacecraftType
import com.cezarykluczynski.stapi.model.spacecraft_type.entity.SpacecraftType
import org.mapstruct.factory.Mappers
import spock.lang.Specification

class SpacecraftTypeRestMapperTest extends Specification {

	private static final String UID = 'UID'
	private static final String NAME = 'NAME'

	private SpacecraftTypeRestMapper spacecraftTypeRestMapper

	void setup() {
		spacecraftTypeRestMapper = Mappers.getMapper(SpacecraftTypeRestMapper)
	}

	void "maps db entity to REST entity"() {
		given:
		SpacecraftType spacecraftType = new SpacecraftType(
				uid: UID,
				name: NAME)

		when:
		RestSpacecraftType restSpacecraftType = spacecraftTypeRestMapper.map(spacecraftType)

		then:
		restSpacecraftType.uid == UID
		restSpacecraftType.name == NAME
	}

}
