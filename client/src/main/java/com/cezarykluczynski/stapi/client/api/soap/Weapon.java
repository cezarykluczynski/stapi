package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.WeaponBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.WeaponBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.WeaponFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.WeaponFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.WeaponPortType;

public class Weapon {

	private final WeaponPortType weaponPortType;

	public Weapon(WeaponPortType weaponPortType) {
		this.weaponPortType = weaponPortType;
	}

	@Deprecated
	public WeaponFullResponse get(WeaponFullRequest request) {
		return weaponPortType.getWeaponFull(request);
	}

	@Deprecated
	public WeaponBaseResponse search(WeaponBaseRequest request) {
		return weaponPortType.getWeaponBase(request);
	}

}

