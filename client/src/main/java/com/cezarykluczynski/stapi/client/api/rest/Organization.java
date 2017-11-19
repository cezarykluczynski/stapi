package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.OrganizationApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.OrganizationBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.OrganizationFullResponse;

@SuppressWarnings("ParameterNumber")
public class Organization {

	private final OrganizationApi organizationApi;

	private final String apiKey;

	public Organization(OrganizationApi organizationApi, String apiKey) {
		this.organizationApi = organizationApi;
		this.apiKey = apiKey;
	}

	public OrganizationFullResponse get(String uid) throws ApiException {
		return organizationApi.organizationGet(uid, apiKey);
	}

	public OrganizationBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, Boolean government,
			Boolean intergovernmentalOrganization, Boolean researchOrganization, Boolean sportOrganization, Boolean medicalOrganization,
			Boolean militaryOrganization, Boolean militaryUnit, Boolean governmentAgency, Boolean lawEnforcementAgency, Boolean prisonOrPenalColony,
			Boolean mirror, Boolean alternateReality) throws ApiException {
		return organizationApi.organizationSearchPost(pageNumber, pageSize, sort, apiKey, name, government, intergovernmentalOrganization,
				researchOrganization, sportOrganization, medicalOrganization, militaryOrganization, militaryUnit, governmentAgency,
				lawEnforcementAgency, prisonOrPenalColony, mirror, alternateReality);
	}

}
