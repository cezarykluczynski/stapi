package com.cezarykluczynski.stapi.client.api.dto;

import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectType;

public class AstronomicalObjectV2SearchCriteria extends AbstractPageSortBaseCriteria {

	private String name;

	private AstronomicalObjectType astronomicalObjectType;

	private String locationUid;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AstronomicalObjectType getAstronomicalObjectType() {
		return astronomicalObjectType;
	}

	public void setAstronomicalObjectType(AstronomicalObjectType astronomicalObjectType) {
		this.astronomicalObjectType = astronomicalObjectType;
	}

	public String getLocationUid() {
		return locationUid;
	}

	public void setLocationUid(String locationUid) {
		this.locationUid = locationUid;
	}

}
