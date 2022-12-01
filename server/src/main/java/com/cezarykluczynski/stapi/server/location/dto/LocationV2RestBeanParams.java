package com.cezarykluczynski.stapi.server.location.dto;

import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;

import javax.ws.rs.FormParam;

public class LocationV2RestBeanParams extends PageSortBeanParams {

	private String uid;

	@FormParam("name")
	private String name;

	@FormParam("earthlyLocation")
	private Boolean earthlyLocation;

	@FormParam("qonosLocation")
	private Boolean qonosLocation;

	@FormParam("fictionalLocation")
	private Boolean fictionalLocation;

	@FormParam("mythologicalLocation")
	private Boolean mythologicalLocation;

	@FormParam("religiousLocation")
	private Boolean religiousLocation;

	@FormParam("geographicalLocation")
	private Boolean geographicalLocation;

	@FormParam("bodyOfWater")
	private Boolean bodyOfWater;

	@FormParam("country")
	private Boolean country;

	@FormParam("subnationalEntity")
	private Boolean subnationalEntity;

	@FormParam("settlement")
	private Boolean settlement;

	@FormParam("usSettlement")
	private Boolean usSettlement;

	@FormParam("bajoranSettlement")
	private Boolean bajoranSettlement;

	@FormParam("colony")
	private Boolean colony;

	@FormParam("landform")
	private Boolean landform;

	@FormParam("road")
	private Boolean road;

	@FormParam("structure")
	private Boolean structure;

	@FormParam("shipyard")
	private Boolean shipyard;

	@FormParam("buildingInterior")
	private Boolean buildingInterior;

	@FormParam("establishment")
	private Boolean establishment;

	@FormParam("medicalEstablishment")
	private Boolean medicalEstablishment;

	@FormParam("ds9Establishment")
	private Boolean ds9Establishment;

	@FormParam("school")
	private Boolean school;

	@FormParam("restaurant")
	private Boolean restaurant;

	@FormParam("residence")
	private Boolean residence;

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

	public Boolean getEarthlyLocation() {
		return earthlyLocation;
	}

	public Boolean getQonosLocation() {
		return qonosLocation;
	}

	public Boolean getFictionalLocation() {
		return fictionalLocation;
	}

	public Boolean getMythologicalLocation() {
		return mythologicalLocation;
	}

	public Boolean getReligiousLocation() {
		return religiousLocation;
	}

	public Boolean getGeographicalLocation() {
		return geographicalLocation;
	}

	public Boolean getBodyOfWater() {
		return bodyOfWater;
	}

	public Boolean getCountry() {
		return country;
	}

	public Boolean getSubnationalEntity() {
		return subnationalEntity;
	}

	public Boolean getSettlement() {
		return settlement;
	}

	public Boolean getUsSettlement() {
		return usSettlement;
	}

	public Boolean getBajoranSettlement() {
		return bajoranSettlement;
	}

	public Boolean getColony() {
		return colony;
	}

	public Boolean getLandform() {
		return landform;
	}

	public Boolean getRoad() {
		return road;
	}

	public Boolean getStructure() {
		return structure;
	}

	public Boolean getShipyard() {
		return shipyard;
	}

	public Boolean getBuildingInterior() {
		return buildingInterior;
	}

	public Boolean getEstablishment() {
		return establishment;
	}

	public Boolean getMedicalEstablishment() {
		return medicalEstablishment;
	}

	public Boolean getDs9Establishment() {
		return ds9Establishment;
	}

	public Boolean getSchool() {
		return school;
	}

	public Boolean getRestaurant() {
		return restaurant;
	}

	public Boolean getResidence() {
		return residence;
	}

	public Boolean getMirror() {
		return mirror;
	}

	public Boolean getAlternateReality() {
		return alternateReality;
	}

	public static LocationV2RestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		LocationV2RestBeanParams locationV2RestBeanParams = new LocationV2RestBeanParams();
		locationV2RestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		locationV2RestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		locationV2RestBeanParams.setSort(pageSortBeanParams.getSort());
		return locationV2RestBeanParams;
	}

}
