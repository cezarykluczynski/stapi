package com.cezarykluczynski.stapi.client.api.dto;

public class OrganizationSearchCriteria extends AbstractPageSortBaseCriteria {

	private String name;

	private Boolean government;

	private Boolean intergovernmentalOrganization;

	private Boolean researchOrganization;

	private Boolean sportOrganization;

	private Boolean medicalOrganization;

	private Boolean militaryOrganization;

	private Boolean militaryUnit;

	private Boolean governmentAgency;

	private Boolean lawEnforcementAgency;

	private Boolean prisonOrPenalColony;

	private Boolean mirror;

	private Boolean alternateReality;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getGovernment() {
		return government;
	}

	public void setGovernment(Boolean government) {
		this.government = government;
	}

	public Boolean getIntergovernmentalOrganization() {
		return intergovernmentalOrganization;
	}

	public void setIntergovernmentalOrganization(Boolean intergovernmentalOrganization) {
		this.intergovernmentalOrganization = intergovernmentalOrganization;
	}

	public Boolean getResearchOrganization() {
		return researchOrganization;
	}

	public void setResearchOrganization(Boolean researchOrganization) {
		this.researchOrganization = researchOrganization;
	}

	public Boolean getSportOrganization() {
		return sportOrganization;
	}

	public void setSportOrganization(Boolean sportOrganization) {
		this.sportOrganization = sportOrganization;
	}

	public Boolean getMedicalOrganization() {
		return medicalOrganization;
	}

	public void setMedicalOrganization(Boolean medicalOrganization) {
		this.medicalOrganization = medicalOrganization;
	}

	public Boolean getMilitaryOrganization() {
		return militaryOrganization;
	}

	public void setMilitaryOrganization(Boolean militaryOrganization) {
		this.militaryOrganization = militaryOrganization;
	}

	public Boolean getMilitaryUnit() {
		return militaryUnit;
	}

	public void setMilitaryUnit(Boolean militaryUnit) {
		this.militaryUnit = militaryUnit;
	}

	public Boolean getGovernmentAgency() {
		return governmentAgency;
	}

	public void setGovernmentAgency(Boolean governmentAgency) {
		this.governmentAgency = governmentAgency;
	}

	public Boolean getLawEnforcementAgency() {
		return lawEnforcementAgency;
	}

	public void setLawEnforcementAgency(Boolean lawEnforcementAgency) {
		this.lawEnforcementAgency = lawEnforcementAgency;
	}

	public Boolean getPrisonOrPenalColony() {
		return prisonOrPenalColony;
	}

	public void setPrisonOrPenalColony(Boolean prisonOrPenalColony) {
		this.prisonOrPenalColony = prisonOrPenalColony;
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
