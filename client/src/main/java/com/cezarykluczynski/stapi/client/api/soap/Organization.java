package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.OrganizationBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.OrganizationBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.OrganizationFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.OrganizationFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.OrganizationPortType;

public class Organization {

	private final OrganizationPortType organizationPortType;

	public Organization(OrganizationPortType organizationPortType) {
		this.organizationPortType = organizationPortType;
	}

	@Deprecated
	public OrganizationFullResponse get(OrganizationFullRequest request) {
		return organizationPortType.getOrganizationFull(request);
	}

	@Deprecated
	public OrganizationBaseResponse search(OrganizationBaseRequest request) {
		return organizationPortType.getOrganizationBase(request);
	}

}
