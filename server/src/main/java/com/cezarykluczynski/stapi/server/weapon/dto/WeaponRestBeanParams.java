package com.cezarykluczynski.stapi.server.weapon.dto;

import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;

import javax.ws.rs.FormParam;

public class WeaponRestBeanParams extends PageSortBeanParams {

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

	public Boolean getMirror() {
		return mirror;
	}

	public Boolean getAlternateReality() {
		return alternateReality;
	}

	public static WeaponRestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		WeaponRestBeanParams weaponRestBeanParams = new WeaponRestBeanParams();
		weaponRestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		weaponRestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		weaponRestBeanParams.setSort(pageSortBeanParams.getSort());
		return weaponRestBeanParams;
	}

}
