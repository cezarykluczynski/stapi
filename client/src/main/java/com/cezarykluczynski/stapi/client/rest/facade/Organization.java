package com.cezarykluczynski.stapi.client.rest.facade;

import com.cezarykluczynski.stapi.client.rest.util.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.rest.api.OrganizationApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.OrganizationBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.OrganizationFullResponse;
import com.cezarykluczynski.stapi.client.rest.model.OrganizationSearchCriteria;

public class Organization {

	private final OrganizationApi organizationApi;

	public Organization(OrganizationApi organizationApi) {
		this.organizationApi = organizationApi;
	}

	public OrganizationFullResponse get(String uid) throws ApiException {
		return organizationApi.v1GetOrganization(uid);
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
