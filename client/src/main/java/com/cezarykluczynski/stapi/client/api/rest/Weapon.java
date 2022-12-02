package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.WeaponApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.WeaponBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.WeaponFullResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.WeaponV2BaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.WeaponV2FullResponse;

@SuppressWarnings("ParameterNumber")
public class Weapon {

	private final WeaponApi weaponApi;

	private final String apiKey;

	public Weapon(WeaponApi weaponApi, String apiKey) {
		this.weaponApi = weaponApi;
		this.apiKey = apiKey;
	}

	@Deprecated
	public WeaponFullResponse get(String uid) throws ApiException {
		return weaponApi.v1RestWeaponGet(uid, apiKey);
	}

	public WeaponV2FullResponse getV2(String uid) throws ApiException {
		return weaponApi.v2RestWeaponGet(uid, apiKey);
	}

	@Deprecated
	public WeaponBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, Boolean handHeldWeapon, Boolean laserTechnology,
			Boolean plasmaTechnology, Boolean photonicTechnology, Boolean phaserTechnology, Boolean mirror, Boolean alternateReality)
			throws ApiException {
		return weaponApi.v1RestWeaponSearchPost(pageNumber, pageSize, sort, apiKey, name, handHeldWeapon, laserTechnology, plasmaTechnology,
				photonicTechnology, phaserTechnology, mirror, alternateReality);
	}

	public WeaponV2BaseResponse searchV2(Integer pageNumber, Integer pageSize, String sort, String name, Boolean handHeldWeapon,
			Boolean laserTechnology, Boolean plasmaTechnology, Boolean photonicTechnology, Boolean phaserTechnology, Boolean directedEnergyWeapon,
			Boolean explosiveWeapon, Boolean projectileWeapon, Boolean fictionalWeapon, Boolean mirror, Boolean alternateReality)
			throws ApiException {
		return weaponApi.v2RestWeaponSearchPost(pageNumber, pageSize, sort, apiKey, name, handHeldWeapon, laserTechnology, plasmaTechnology,
				photonicTechnology, phaserTechnology, directedEnergyWeapon, explosiveWeapon, projectileWeapon, fictionalWeapon, mirror,
				alternateReality);
	}

}
