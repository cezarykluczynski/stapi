package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.api.dto.OrganizationSearchCriteria;
import com.cezarykluczynski.stapi.client.rest.api.OrganizationApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.OrganizationBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.OrganizationFullResponse;

@SuppressWarnings("ParameterNumber")
public class Organization {

	private final OrganizationApi organizationApi;

	public Organization(OrganizationApi organizationApi) {
		this.organizationApi = organizationApi;
	}

	public OrganizationFullResponse get(String uid) throws ApiException {
		return organizationApi.v1GetOrganization(uid);
	}

	@Deprecated
	public OrganizationBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, Boolean government,
			Boolean intergovernmentalOrganization, Boolean researchOrganization, Boolean sportOrganization, Boolean medicalOrganization,
			Boolean militaryOrganization, Boolean militaryUnit, Boolean governmentAgency, Boolean lawEnforcementAgency, Boolean prisonOrPenalColony,
			Boolean mirror, Boolean alternateReality) throws ApiException {
		return organizationApi.v1SearchOrganizations(pageNumber, pageSize, sort, name, government, intergovernmentalOrganization,
				researchOrganization, sportOrganization, medicalOrganization, militaryOrganization, militaryUnit, governmentAgency,
				lawEnforcementAgency, prisonOrPenalColony, mirror, alternateReality);
	}

	public OrganizationBaseResponse search(OrganizationSearchCriteria organizationSearchCriteria) throws ApiException {
		return organizationApi.v1SearchOrganizations(organizationSearchCriteria.getPageNumber(), organizationSearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(organizationSearchCriteria.getSort()), organizationSearchCriteria.getName(),
				organizationSearchCriteria.getGovernment(), organizationSearchCriteria.getIntergovernmentalOrganization(),
				organizationSearchCriteria.getResearchOrganization(), organizationSearchCriteria.getSportOrganization(),
				organizationSearchCriteria.getMedicalOrganization(), organizationSearchCriteria.getMilitaryOrganization(),
				organizationSearchCriteria.getMilitaryUnit(), organizationSearchCriteria.getGovernmentAgency(),
				organizationSearchCriteria.getLawEnforcementAgency(), organizationSearchCriteria.getPrisonOrPenalColony(),
				organizationSearchCriteria.getMirror(), organizationSearchCriteria.getAlternateReality());
	}

}
