package com.cezarykluczynski.stapi.server.weapon.mapper

import com.cezarykluczynski.stapi.client.v1.soap.WeaponHeader
import com.cezarykluczynski.stapi.model.weapon.entity.Weapon
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class WeaponHeaderSoapMapperTest extends AbstractWeaponMapperTest {

	private WeaponHeaderSoapMapper weaponHeaderSoapMapper

	void setup() {
		weaponHeaderSoapMapper = Mappers.getMapper(WeaponHeaderSoapMapper)
	}

	void "maps DB entity to SOAP header"() {
		given:
		Weapon weapon = new Weapon(
				uid: UID,
				name: NAME)

		when:
		WeaponHeader weaponHeader = weaponHeaderSoapMapper.map(Lists.newArrayList(weapon))[0]

		then:
		weaponHeader.uid == UID
		weaponHeader.name == NAME
	}

}
