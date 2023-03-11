package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.api.dto.WeaponV2SearchCriteria;
import com.cezarykluczynski.stapi.client.v1.rest.api.WeaponApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.WeaponBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.WeaponFullResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.WeaponV2BaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.WeaponV2FullResponse;

@SuppressWarnings("ParameterNumber")
public class Weapon {

	private final WeaponApi weaponApi;

	public Weapon(WeaponApi weaponApi) {
		this.weaponApi = weaponApi;
	}

	@Deprecated
	public WeaponFullResponse get(String uid) throws ApiException {
		return weaponApi.v1RestWeaponGet(uid);
	}

	public WeaponV2FullResponse getV2(String uid) throws ApiException {
		return weaponApi.v2RestWeaponGet(uid);
	}

	@Deprecated
	public WeaponBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, Boolean handHeldWeapon, Boolean laserTechnology,
			Boolean plasmaTechnology, Boolean photonicTechnology, Boolean phaserTechnology, Boolean mirror, Boolean alternateReality)
			throws ApiException {
		return weaponApi.v1RestWeaponSearchPost(pageNumber, pageSize, sort, name, handHeldWeapon, laserTechnology, plasmaTechnology,
				photonicTechnology, phaserTechnology, mirror, alternateReality);
	}

	@Deprecated
	public WeaponV2BaseResponse searchV2(Integer pageNumber, Integer pageSize, String sort, String name, Boolean handHeldWeapon,
			Boolean laserTechnology, Boolean plasmaTechnology, Boolean photonicTechnology, Boolean phaserTechnology, Boolean directedEnergyWeapon,
			Boolean explosiveWeapon, Boolean projectileWeapon, Boolean fictionalWeapon, Boolean mirror, Boolean alternateReality)
			throws ApiException {
		return weaponApi.v2RestWeaponSearchPost(pageNumber, pageSize, sort, name, handHeldWeapon, laserTechnology, plasmaTechnology,
				photonicTechnology, phaserTechnology, directedEnergyWeapon, explosiveWeapon, projectileWeapon, fictionalWeapon, mirror,
				alternateReality);
	}

	public WeaponV2BaseResponse searchV2(WeaponV2SearchCriteria weaponV2SearchCriteria) throws ApiException {
		return weaponApi.v2RestWeaponSearchPost(weaponV2SearchCriteria.getPageNumber(), weaponV2SearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(weaponV2SearchCriteria.getSort()), weaponV2SearchCriteria.getName(),
				weaponV2SearchCriteria.getHandHeldWeapon(), weaponV2SearchCriteria.getLaserTechnology(), weaponV2SearchCriteria.getPlasmaTechnology(),
				weaponV2SearchCriteria.getPhotonicTechnology(), weaponV2SearchCriteria.getPhaserTechnology(),
				weaponV2SearchCriteria.getDirectedEnergyWeapon(), weaponV2SearchCriteria.getExplosiveWeapon(),
				weaponV2SearchCriteria.getProjectileWeapon(), weaponV2SearchCriteria.getFictionalWeapon(), weaponV2SearchCriteria.getMirror(),
				weaponV2SearchCriteria.getAlternateReality());
	}

}
