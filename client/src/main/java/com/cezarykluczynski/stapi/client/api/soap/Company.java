package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.CompanyBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.CompanyBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.CompanyFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.CompanyFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.CompanyPortType;

public class Company {

	private final CompanyPortType companyPortType;

	private final ApiKeySupplier apiKeySupplier;

	public Company(CompanyPortType companyPortType, ApiKeySupplier apiKeySupplier) {
		this.companyPortType = companyPortType;
		this.apiKeySupplier = apiKeySupplier;
	}

	public CompanyFullResponse get(CompanyFullRequest request) {
		apiKeySupplier.supply(request);
		return companyPortType.getCompanyFull(request);
	}

	public CompanyBaseResponse search(CompanyBaseRequest request) {
		apiKeySupplier.supply(request);
		return companyPortType.getCompanyBase(request);
	}

}
