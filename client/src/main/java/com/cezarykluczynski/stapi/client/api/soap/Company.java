package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.CompanyBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.CompanyBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.CompanyFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.CompanyFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.CompanyPortType;

public class Company {

	private final CompanyPortType companyPortType;

	public Company(CompanyPortType companyPortType) {
		this.companyPortType = companyPortType;
	}

	@Deprecated
	public CompanyFullResponse get(CompanyFullRequest request) {
		return companyPortType.getCompanyFull(request);
	}

	@Deprecated
	public CompanyBaseResponse search(CompanyBaseRequest request) {
		return companyPortType.getCompanyBase(request);
	}

}
