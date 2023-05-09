package com.cezarykluczynski.stapi.etl.weapon.creation.processor

import com.cezarykluczynski.stapi.model.weapon.entity.Weapon
import com.cezarykluczynski.stapi.model.weapon.repository.WeaponRepository
import com.google.common.collect.Lists
import org.springframework.batch.item.Chunk
import spock.lang.Specification

class WeaponWriterTest extends Specification {

	private WeaponRepository weaponRepositoryMock

	private WeaponWriter weaponWriterMock

	void setup() {
		weaponRepositoryMock = Mock()
		weaponWriterMock = new WeaponWriter(weaponRepositoryMock)
	}

	void "writes all entities using repository"() {
		given:
		Weapon weapon = new Weapon()
		List<Weapon> weaponList = Lists.newArrayList(weapon)

		when:
		weaponWriterMock.write(new Chunk(weaponList))

		then:
		1 * weaponRepositoryMock.saveAll(weaponList)
		0 * _
	}

}
