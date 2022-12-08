package com.cezarykluczynski.stapi.server.technology.dto;

import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;

import javax.ws.rs.FormParam;

public class TechnologyV2RestBeanParams extends PageSortBeanParams {

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

	@FormParam("securityTechnology")
	private Boolean securityTechnology;

	@FormParam("propulsionTechnology")
	private Boolean propulsionTechnology;

	@FormParam("spacecraftComponent")
	private Boolean spacecraftComponent;

	@FormParam("warpTechnology")
	private Boolean warpTechnology;

	@FormParam("transwarpTechnology")
	private Boolean transwarpTechnology;

	@FormParam("timeTravelTechnology")
	private Boolean timeTravelTechnology;

	@FormParam("militaryTechnology")
	private Boolean militaryTechnology;

	@FormParam("victualTechnology")
	private Boolean victualTechnology;

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

	@FormParam("transportationTechnology")
	private Boolean transportationTechnology;

	@FormParam("weaponComponent")
	private Boolean weaponComponent;

	@FormParam("artificialLifeformComponent")
	private Boolean artificialLifeformComponent;

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

	public Boolean getSecurityTechnology() {
		return securityTechnology;
	}

	public Boolean getPropulsionTechnology() {
		return propulsionTechnology;
	}

	public Boolean getSpacecraftComponent() {
		return spacecraftComponent;
	}

	public Boolean getWarpTechnology() {
		return warpTechnology;
	}

	public Boolean getTranswarpTechnology() {
		return transwarpTechnology;
	}

	public Boolean getTimeTravelTechnology() {
		return timeTravelTechnology;
	}

	public Boolean getMilitaryTechnology() {
		return militaryTechnology;
	}

	public Boolean getVictualTechnology() {
		return victualTechnology;
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

	public Boolean getTransportationTechnology() {
		return transportationTechnology;
	}

	public Boolean getWeaponComponent() {
		return weaponComponent;
	}

	public Boolean getArtificialLifeformComponent() {
		return artificialLifeformComponent;
	}

	public static TechnologyV2RestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		TechnologyV2RestBeanParams technologyV2RestBeanParams = new TechnologyV2RestBeanParams();
		technologyV2RestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		technologyV2RestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		technologyV2RestBeanParams.setSort(pageSortBeanParams.getSort());
		return technologyV2RestBeanParams;
	}

}
