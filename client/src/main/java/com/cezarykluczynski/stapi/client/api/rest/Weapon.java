package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.WeaponApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.WeaponBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.WeaponFullResponse;

@SuppressWarnings("ParameterNumber")
public class Weapon {

	private final WeaponApi weaponApi;

	private final String apiKey;

	public Weapon(WeaponApi weaponApi, String apiKey) {
		this.weaponApi = weaponApi;
		this.apiKey = apiKey;
	}

	public WeaponFullResponse get(String uid) throws ApiException {
		return weaponApi.weaponGet(uid, apiKey);
	}

	public WeaponBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, Boolean handHeldWeapon, Boolean laserTechnology,
			Boolean plasmaTechnology, Boolean photonicTechnology, Boolean phaserTechnology, Boolean mirror, Boolean alternateReality)
			throws ApiException {
		return weaponApi.weaponSearchPost(pageNumber, pageSize, sort, apiKey, name, handHeldWeapon, laserTechnology, plasmaTechnology,
				photonicTechnology, phaserTechnology, mirror, alternateReality);
	}

}
