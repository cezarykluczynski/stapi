package com.cezarykluczynski.stapi.client.api.dto;

public class TitleV2SearchCriteria extends AbstractPageSortBaseCriteria {

	private String name;

	private Boolean militaryRank;

	private Boolean fleetRank;

	private Boolean religiousTitle;

	private Boolean educationTitle;

	private Boolean mirror;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getMilitaryRank() {
		return militaryRank;
	}

	public void setMilitaryRank(Boolean militaryRank) {
		this.militaryRank = militaryRank;
	}

	public Boolean getFleetRank() {
		return fleetRank;
	}

	public void setFleetRank(Boolean fleetRank) {
		this.fleetRank = fleetRank;
	}

	public Boolean getReligiousTitle() {
		return religiousTitle;
	}

	public void setReligiousTitle(Boolean religiousTitle) {
		this.religiousTitle = religiousTitle;
	}

	public Boolean getEducationTitle() {
		return educationTitle;
	}

	public void setEducationTitle(Boolean educationTitle) {
		this.educationTitle = educationTitle;
	}

	public Boolean getMirror() {
		return mirror;
	}

	public void setMirror(Boolean mirror) {
		this.mirror = mirror;
	}

}
