package com.cezarykluczynski.stapi.etl.weapon.creation.processor

import com.cezarykluczynski.stapi.model.weapon.entity.Weapon
import com.cezarykluczynski.stapi.model.weapon.repository.WeaponRepository
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter
import com.google.common.collect.Lists
import spock.lang.Specification

class WeaponWriterTest extends Specification {

	private WeaponRepository weaponRepositoryMock

	private DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessorMock

	private WeaponWriter weaponWriterMock

	void setup() {
		weaponRepositoryMock = Mock()
		duplicateFilteringPreSavePageAwareProcessorMock = Mock()
		weaponWriterMock = new WeaponWriter(weaponRepositoryMock, duplicateFilteringPreSavePageAwareProcessorMock)
	}

	void "filters all entities using pre save processor, then writes all entities using repository"() {
		given:
		Weapon weapon = new Weapon()
		List<Weapon> weaponList = Lists.newArrayList(weapon)

		when:
		weaponWriterMock.write(weaponList)

		then:
		1 * duplicateFilteringPreSavePageAwareProcessorMock.process(_, Weapon) >> { args ->
			assert args[0][0] == weapon
			weaponList
		}
		1 * weaponRepositoryMock.save(weaponList)
		0 * _
	}

}
