package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.rest.api.WeaponApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.WeaponV2BaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.WeaponV2FullResponse;
import com.cezarykluczynski.stapi.client.rest.model.WeaponV2SearchCriteria;

public class Weapon {

	private final WeaponApi weaponApi;

	public Weapon(WeaponApi weaponApi) {
		this.weaponApi = weaponApi;
	}

	public WeaponV2FullResponse getV2(String uid) throws ApiException {
		return weaponApi.v2GetWeapon(uid);
	}

	public WeaponV2BaseResponse searchV2(WeaponV2SearchCriteria weaponV2SearchCriteria) throws ApiException {
		return weaponApi.v2SearchWeapons(weaponV2SearchCriteria.getPageNumber(), weaponV2SearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(weaponV2SearchCriteria.getSort()), weaponV2SearchCriteria.getName(),
				weaponV2SearchCriteria.getHandHeldWeapon(), weaponV2SearchCriteria.getLaserTechnology(), weaponV2SearchCriteria.getPlasmaTechnology(),
				weaponV2SearchCriteria.getPhotonicTechnology(), weaponV2SearchCriteria.getPhaserTechnology(),
				weaponV2SearchCriteria.getDirectedEnergyWeapon(), weaponV2SearchCriteria.getExplosiveWeapon(),
				weaponV2SearchCriteria.getProjectileWeapon(), weaponV2SearchCriteria.getFictionalWeapon(), weaponV2SearchCriteria.getMirror(),
				weaponV2SearchCriteria.getAlternateReality());
	}

}
