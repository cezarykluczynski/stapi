package com.cezarykluczynski.stapi.server.staff.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.StaffPortType;
import com.cezarykluczynski.stapi.client.v1.soap.StaffRequest;
import com.cezarykluczynski.stapi.client.v1.soap.StaffResponse;
import com.cezarykluczynski.stapi.server.staff.reader.StaffSoapReader;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public class StaffSoapEndpoint implements StaffPortType {

	private StaffSoapReader seriesSoapReader;

	public StaffSoapEndpoint(StaffSoapReader seriesSoapReader) {
		this.seriesSoapReader = seriesSoapReader;
	}

	@Override
	public StaffResponse getStaff(@WebParam(partName = "request", name = "StaffRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/staff") StaffRequest request) {
		return seriesSoapReader.read(request);
	}

}
