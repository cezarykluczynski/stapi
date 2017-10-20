package com.cezarykluczynski.stapi.server.technology.dto;

import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;

import javax.ws.rs.FormParam;

public class TechnologyRestBeanParams extends PageSortBeanParams {

	private String uid;

	@FormParam("name")
	private String name;

	@FormParam("borgTechnology")
	private Boolean borgTechnology;

	@FormParam("borgComponent")
	private Boolean borgComponent;

	@FormParam("communicationsTechnology")
	private Boolean communicationsTechnology;

	@FormParam("computerTechnology")
	private Boolean computerTechnology;

	@FormParam("computerProgramming")
	private Boolean computerProgramming;

	@FormParam("subroutine")
	private Boolean subroutine;

	@FormParam("database")
	private Boolean database;

	@FormParam("energyTechnology")
	private Boolean energyTechnology;

	@FormParam("fictionalTechnology")
	private Boolean fictionalTechnology;

	@FormParam("holographicTechnology")
	private Boolean holographicTechnology;

	@FormParam("identificationTechnology")
	private Boolean identificationTechnology;

	@FormParam("lifeSupportTechnology")
	private Boolean lifeSupportTechnology;

	@FormParam("sensorTechnology")
	private Boolean sensorTechnology;

	@FormParam("shieldTechnology")
	private Boolean shieldTechnology;

	@FormParam("tool")
	private Boolean tool;

	@FormParam("culinaryTool")
	private Boolean culinaryTool;

	@FormParam("engineeringTool")
	private Boolean engineeringTool;

	@FormParam("householdTool")
	private Boolean householdTool;

	@FormParam("medicalEquipment")
	private Boolean medicalEquipment;

	@FormParam("transporterTechnology")
	private Boolean transporterTechnology;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public Boolean getBorgTechnology() {
		return borgTechnology;
	}

	public Boolean getBorgComponent() {
		return borgComponent;
	}

	public Boolean getCommunicationsTechnology() {
		return communicationsTechnology;
	}

	public Boolean getComputerTechnology() {
		return computerTechnology;
	}

	public Boolean getComputerProgramming() {
		return computerProgramming;
	}

	public Boolean getSubroutine() {
		return subroutine;
	}

	public Boolean getDatabase() {
		return database;
	}

	public Boolean getEnergyTechnology() {
		return energyTechnology;
	}

	public Boolean getFictionalTechnology() {
		return fictionalTechnology;
	}

	public Boolean getHolographicTechnology() {
		return holographicTechnology;
	}

	public Boolean getIdentificationTechnology() {
		return identificationTechnology;
	}

	public Boolean getLifeSupportTechnology() {
		return lifeSupportTechnology;
	}

	public Boolean getSensorTechnology() {
		return sensorTechnology;
	}

	public Boolean getShieldTechnology() {
		return shieldTechnology;
	}

	public Boolean getTool() {
		return tool;
	}

	public Boolean getCulinaryTool() {
		return culinaryTool;
	}

	public Boolean getEngineeringTool() {
		return engineeringTool;
	}

	public Boolean getHouseholdTool() {
		return householdTool;
	}

	public Boolean getMedicalEquipment() {
		return medicalEquipment;
	}

	public Boolean getTransporterTechnology() {
		return transporterTechnology;
	}

	public static TechnologyRestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		TechnologyRestBeanParams technologyRestBeanParams = new TechnologyRestBeanParams();
		technologyRestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		technologyRestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		technologyRestBeanParams.setSort(pageSortBeanParams.getSort());
		return technologyRestBeanParams;
	}

}
