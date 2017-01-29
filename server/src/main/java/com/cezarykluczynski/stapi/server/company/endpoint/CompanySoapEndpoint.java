package com.cezarykluczynski.stapi.server.company.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.CompanyPortType;
import com.cezarykluczynski.stapi.client.v1.soap.CompanyRequest;
import com.cezarykluczynski.stapi.client.v1.soap.CompanyResponse;
import com.cezarykluczynski.stapi.server.company.reader.CompanySoapReader;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public class CompanySoapEndpoint implements CompanyPortType {

	private CompanySoapReader seriesSoapReader;

	public CompanySoapEndpoint(CompanySoapReader seriesSoapReader) {
		this.seriesSoapReader = seriesSoapReader;
	}

	@Override
	public CompanyResponse getCompanies(@WebParam(partName = "request", name = "CompanyRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/company") CompanyRequest request) {
		return seriesSoapReader.read(request);
	}

}
