package com.cezarykluczynski.stapi.etl.template.common.factory

import com.cezarykluczynski.stapi.etl.platform.service.PlatformCodeToNameMapper
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.platform.entity.Platform
import com.cezarykluczynski.stapi.model.platform.repository.PlatformRepository
import spock.lang.Specification

class PlatformFactoryTest extends Specification {

	private static final String CODE = 'CODE'
	private static final String NAME = 'NAME'
	private static final String UID = 'UID'

	private PlatformCodeToNameMapper platformCodeToNameMapperMock

	private PlatformRepository platformRepositoryMock

	private UidGenerator uidGeneratorMock

	private PlatformFactory platformFactory

	void setup() {
		platformCodeToNameMapperMock = Mock()
		platformRepositoryMock = Mock()
		uidGeneratorMock = Mock()
		platformFactory = new PlatformFactory(platformCodeToNameMapperMock, platformRepositoryMock, uidGeneratorMock)
	}

	void "returns existing entity when it can be found"() {
		given:
		Platform platform = Mock()

		when:
		Optional<Platform> platformOptional = platformFactory.createForCode(CODE)

		then:
		1 * platformCodeToNameMapperMock.map(CODE) >> NAME
		1 * platformRepositoryMock.findByName(NAME) >> Optional.of(platform)
		0 * _
		platformOptional.isPresent()
		platformOptional.get() == platform
	}

	void "returns new entity when language name is name of an existing language"() {
		when:
		Optional<Platform> platformOptional = platformFactory.createForCode(CODE)

		then:
		1 * platformCodeToNameMapperMock.map(CODE) >> NAME
		1 * platformRepositoryMock.findByName(NAME) >> Optional.empty()
		1 * uidGeneratorMock.generateForPlatform(CODE) >> UID
		1 * platformRepositoryMock.save(_ as Platform) >> { Platform platformInput ->
			assert platformInput.name == NAME
			assert platformInput.uid == UID
		}
		0 * _
		platformOptional.isPresent()
		platformOptional.get().name == NAME
		platformOptional.get().uid == UID
	}

	void "returns empty optional when platform name could not be found by code"() {
		when:
		Optional<Platform> platformOptional = platformFactory.createForCode(CODE)

		then:
		1 * platformCodeToNameMapperMock.map(CODE) >> null
		0 * _
		!platformOptional.isPresent()
	}

}
