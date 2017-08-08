package com.cezarykluczynski.stapi.server.platform.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.Platform as RestPlatform
import com.cezarykluczynski.stapi.model.platform.entity.Platform
import org.mapstruct.factory.Mappers
import spock.lang.Specification

class PlatformRestMapperTest extends Specification {

	private static final String UID = 'UID'
	private static final String NAME = 'NAME'

	private PlatformRestMapper platformRestMapper

	void setup() {
		platformRestMapper = Mappers.getMapper(PlatformRestMapper)
	}

	void "maps db entity to REST entity"() {
		given:
		Platform platform = new Platform(
				uid: UID,
				name: NAME)

		when:
		RestPlatform restPlatform = platformRestMapper.map(platform)

		then:
		restPlatform.uid == UID
		restPlatform.name == NAME
	}

}
