package com.cezarykluczynski.stapi.server.weapon.dto;

import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import jakarta.ws.rs.FormParam;

public class WeaponV2RestBeanParams extends PageSortBeanParams {

	private String uid;

	@FormParam("name")
	private String name;

	@FormParam("handHeldWeapon")
	private Boolean handHeldWeapon;

	@FormParam("laserTechnology")
	private Boolean laserTechnology;

	@FormParam("plasmaTechnology")
	private Boolean plasmaTechnology;

	@FormParam("photonicTechnology")
	private Boolean photonicTechnology;

	@FormParam("phaserTechnology")
	private Boolean phaserTechnology;

	@FormParam("directedEnergyWeapon")
	private Boolean directedEnergyWeapon;

	@FormParam("explosiveWeapon")
	private Boolean explosiveWeapon;

	@FormParam("projectileWeapon")
	private Boolean projectileWeapon;

	@FormParam("fictionalWeapon")
	private Boolean fictionalWeapon;

	@FormParam("mirror")
	private Boolean mirror;

	@FormParam("alternateReality")
	private Boolean alternateReality;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public Boolean getHandHeldWeapon() {
		return handHeldWeapon;
	}

	public Boolean getLaserTechnology() {
		return laserTechnology;
	}

	public Boolean getPlasmaTechnology() {
		return plasmaTechnology;
	}

	public Boolean getPhotonicTechnology() {
		return photonicTechnology;
	}

	public Boolean getPhaserTechnology() {
		return phaserTechnology;
	}

	public Boolean getDirectedEnergyWeapon() {
		return directedEnergyWeapon;
	}

	public Boolean getExplosiveWeapon() {
		return explosiveWeapon;
	}

	public Boolean getProjectileWeapon() {
		return projectileWeapon;
	}

	public Boolean getFictionalWeapon() {
		return fictionalWeapon;
	}

	public Boolean getMirror() {
		return mirror;
	}

	public Boolean getAlternateReality() {
		return alternateReality;
	}

	public static WeaponV2RestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		WeaponV2RestBeanParams weaponV2RestBeanParams = new WeaponV2RestBeanParams();
		weaponV2RestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		weaponV2RestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		weaponV2RestBeanParams.setSort(pageSortBeanParams.getSort());
		return weaponV2RestBeanParams;
	}

}
