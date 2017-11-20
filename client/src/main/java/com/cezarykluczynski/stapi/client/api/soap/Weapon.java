package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.WeaponBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.WeaponBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.WeaponFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.WeaponFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.WeaponPortType;

public class Weapon {

	private final WeaponPortType weaponPortType;

	private final ApiKeySupplier apiKeySupplier;

	public Weapon(WeaponPortType weaponPortType, ApiKeySupplier apiKeySupplier) {
		this.weaponPortType = weaponPortType;
		this.apiKeySupplier = apiKeySupplier;
	}

	public WeaponFullResponse get(WeaponFullRequest request) {
		apiKeySupplier.supply(request);
		return weaponPortType.getWeaponFull(request);
	}

	public WeaponBaseResponse search(WeaponBaseRequest request) {
		apiKeySupplier.supply(request);
		return weaponPortType.getWeaponBase(request);
	}

}

