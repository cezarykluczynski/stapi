package com.cezarykluczynski.stapi.server.organization.dto;

import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;

import javax.ws.rs.FormParam;

public class OrganizationRestBeanParams extends PageSortBeanParams {

	private String uid;

	@FormParam("name")
	private String name;

	@FormParam("government")
	private Boolean government;

	@FormParam("intergovernmentalOrganization")
	private Boolean intergovernmentalOrganization;

	@FormParam("researchOrganization")
	private Boolean researchOrganization;

	@FormParam("sportOrganization")
	private Boolean sportOrganization;

	@FormParam("medicalOrganization")
	private Boolean medicalOrganization;

	@FormParam("militaryOrganization")
	private Boolean militaryOrganization;

	@FormParam("militaryUnit")
	private Boolean militaryUnit;

	@FormParam("governmentAgency")
	private Boolean governmentAgency;

	@FormParam("lawEnforcementAgency")
	private Boolean lawEnforcementAgency;

	@FormParam("prisonOrPenalColony")
	private Boolean prisonOrPenalColony;

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

	public Boolean getGovernment() {
		return government;
	}

	public Boolean getIntergovernmentalOrganization() {
		return intergovernmentalOrganization;
	}

	public Boolean getResearchOrganization() {
		return researchOrganization;
	}

	public Boolean getSportOrganization() {
		return sportOrganization;
	}

	public Boolean getMedicalOrganization() {
		return medicalOrganization;
	}

	public Boolean getMilitaryOrganization() {
		return militaryOrganization;
	}

	public Boolean getMilitaryUnit() {
		return militaryUnit;
	}

	public Boolean getGovernmentAgency() {
		return governmentAgency;
	}

	public Boolean getLawEnforcementAgency() {
		return lawEnforcementAgency;
	}

	public Boolean getPrisonOrPenalColony() {
		return prisonOrPenalColony;
	}

	public Boolean getMirror() {
		return mirror;
	}

	public Boolean getAlternateReality() {
		return alternateReality;
	}

	public static OrganizationRestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		OrganizationRestBeanParams organizationRestBeanParams = new OrganizationRestBeanParams();
		organizationRestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		organizationRestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		organizationRestBeanParams.setSort(pageSortBeanParams.getSort());
		return organizationRestBeanParams;
	}

}
