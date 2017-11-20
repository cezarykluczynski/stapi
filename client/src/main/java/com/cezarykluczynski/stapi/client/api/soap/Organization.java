package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.OrganizationBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.OrganizationBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.OrganizationFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.OrganizationFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.OrganizationPortType;

public class Organization {

	private final OrganizationPortType organizationPortType;

	private final ApiKeySupplier apiKeySupplier;

	public Organization(OrganizationPortType organizationPortType, ApiKeySupplier apiKeySupplier) {
		this.organizationPortType = organizationPortType;
		this.apiKeySupplier = apiKeySupplier;
	}

	public OrganizationFullResponse get(OrganizationFullRequest request) {
		apiKeySupplier.supply(request);
		return organizationPortType.getOrganizationFull(request);
	}

	public OrganizationBaseResponse search(OrganizationBaseRequest request) {
		apiKeySupplier.supply(request);
		return organizationPortType.getOrganizationBase(request);
	}

}
