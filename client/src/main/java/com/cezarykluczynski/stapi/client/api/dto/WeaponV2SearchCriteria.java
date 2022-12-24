package com.cezarykluczynski.stapi.client.api.dto;

public class WeaponV2SearchCriteria extends AbstractPageSortBaseCriteria {

	private String name;

	private Boolean handHeldWeapon;

	private Boolean laserTechnology;

	private Boolean plasmaTechnology;

	private Boolean photonicTechnology;

	private Boolean phaserTechnology;

	private Boolean directedEnergyWeapon;

	private Boolean explosiveWeapon;

	private Boolean projectileWeapon;

	private Boolean fictionalWeapon;

	private Boolean mirror;

	private Boolean alternateReality;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getHandHeldWeapon() {
		return handHeldWeapon;
	}

	public void setHandHeldWeapon(Boolean handHeldWeapon) {
		this.handHeldWeapon = handHeldWeapon;
	}

	public Boolean getLaserTechnology() {
		return laserTechnology;
	}

	public void setLaserTechnology(Boolean laserTechnology) {
		this.laserTechnology = laserTechnology;
	}

	public Boolean getPlasmaTechnology() {
		return plasmaTechnology;
	}

	public void setPlasmaTechnology(Boolean plasmaTechnology) {
		this.plasmaTechnology = plasmaTechnology;
	}

	public Boolean getPhotonicTechnology() {
		return photonicTechnology;
	}

	public void setPhotonicTechnology(Boolean photonicTechnology) {
		this.photonicTechnology = photonicTechnology;
	}

	public Boolean getPhaserTechnology() {
		return phaserTechnology;
	}

	public void setPhaserTechnology(Boolean phaserTechnology) {
		this.phaserTechnology = phaserTechnology;
	}

	public Boolean getDirectedEnergyWeapon() {
		return directedEnergyWeapon;
	}

	public void setDirectedEnergyWeapon(Boolean directedEnergyWeapon) {
		this.directedEnergyWeapon = directedEnergyWeapon;
	}

	public Boolean getExplosiveWeapon() {
		return explosiveWeapon;
	}

	public void setExplosiveWeapon(Boolean explosiveWeapon) {
		this.explosiveWeapon = explosiveWeapon;
	}

	public Boolean getProjectileWeapon() {
		return projectileWeapon;
	}

	public void setProjectileWeapon(Boolean projectileWeapon) {
		this.projectileWeapon = projectileWeapon;
	}

	public Boolean getFictionalWeapon() {
		return fictionalWeapon;
	}

	public void setFictionalWeapon(Boolean fictionalWeapon) {
		this.fictionalWeapon = fictionalWeapon;
	}

	public Boolean getMirror() {
		return mirror;
	}

	public void setMirror(Boolean mirror) {
		this.mirror = mirror;
	}

	public Boolean getAlternateReality() {
		return alternateReality;
	}

	public void setAlternateReality(Boolean alternateReality) {
		this.alternateReality = alternateReality;
	}

}
