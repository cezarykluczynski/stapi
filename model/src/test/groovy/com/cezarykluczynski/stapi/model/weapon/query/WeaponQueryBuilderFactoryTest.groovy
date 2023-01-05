package com.cezarykluczynski.stapi.model.weapon.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class WeaponQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private WeaponQueryBuilderFactory weaponQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
	}

	void "WeaponQueryBuilderFactory is created"() {
		when:
		weaponQueryBuilderFactory = new WeaponQueryBuilderFactory(jpaContextMock)

		then:
		weaponQueryBuilderFactory != null
	}

}
