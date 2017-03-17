package com.cezarykluczynski.stapi.server.staff.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.StaffBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.StaffBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.StaffFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.StaffFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.StaffPortType;
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
	public StaffBaseResponse getStaffBase(@WebParam(partName = "request", name = "StaffBaseRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/staff") StaffBaseRequest request) {
		return seriesSoapReader.readBase(request);
	}

	@Override
	public StaffFullResponse getStaffFull(@WebParam(partName = "request", name = "StaffFullRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/staff") StaffFullRequest request) {
		return seriesSoapReader.readFull(request);
	}

}
