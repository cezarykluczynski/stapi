package com.cezarykluczynski.stapi.server.organization.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.OrganizationBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.OrganizationBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.OrganizationFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.OrganizationFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.OrganizationPortType;
import com.cezarykluczynski.stapi.server.organization.reader.OrganizationSoapReader;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public class OrganizationSoapEndpoint implements OrganizationPortType {

	private OrganizationSoapReader seriesSoapReader;

	public OrganizationSoapEndpoint(OrganizationSoapReader seriesSoapReader) {
		this.seriesSoapReader = seriesSoapReader;
	}

	@Override
	public OrganizationBaseResponse getCompaniesBase(@WebParam(partName = "request", name = "OrganizationBaseRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/organization") OrganizationBaseRequest request) {
		return seriesSoapReader.readBase(request);
	}

	@Override
	public OrganizationFullResponse getOrganizationFull(@WebParam(partName = "request", name = "OrganizationFullRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/organization") OrganizationFullRequest request) {
		return seriesSoapReader.readFull(request);
	}

}
