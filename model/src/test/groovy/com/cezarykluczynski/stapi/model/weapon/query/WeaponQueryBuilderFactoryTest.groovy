package com.cezarykluczynski.stapi.model.weapon.query

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class WeaponQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private WeaponQueryBuilderFactory weaponQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
		cachingStrategyMock = Mock()
	}

	void "WeaponQueryBuilderFactory is created"() {
		when:
		weaponQueryBuilderFactory = new WeaponQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		weaponQueryBuilderFactory != null
	}

}
