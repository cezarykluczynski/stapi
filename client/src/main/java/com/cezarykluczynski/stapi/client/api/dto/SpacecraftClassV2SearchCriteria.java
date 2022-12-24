package com.cezarykluczynski.stapi.client.api.dto;

public class SpacecraftClassV2SearchCriteria extends AbstractPageSortBaseCriteria {

	private String name;

	private Boolean warpCapable;

	private Boolean mirror;

	private Boolean alternateReality;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getWarpCapable() {
		return warpCapable;
	}

	public void setWarpCapable(Boolean warpCapable) {
		this.warpCapable = warpCapable;
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
