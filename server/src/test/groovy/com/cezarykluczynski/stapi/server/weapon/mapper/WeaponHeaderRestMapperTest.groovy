package com.cezarykluczynski.stapi.server.weapon.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.WeaponHeader
import com.cezarykluczynski.stapi.model.weapon.entity.Weapon
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class WeaponHeaderRestMapperTest extends AbstractWeaponMapperTest {

	private WeaponHeaderRestMapper weaponHeaderRestMapper

	void setup() {
		weaponHeaderRestMapper = Mappers.getMapper(WeaponHeaderRestMapper)
	}

	void "maps DB entity to REST header"() {
		given:
		Weapon weapon = new Weapon(
				uid: UID,
				name: NAME)

		when:
		WeaponHeader weaponHeader = weaponHeaderRestMapper.map(Lists.newArrayList(weapon))[0]

		then:
		weaponHeader.uid == UID
		weaponHeader.name == NAME
	}

}
