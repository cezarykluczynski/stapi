package com.cezarykluczynski.stapi.server.location.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.LocationBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.LocationBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.LocationFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.LocationFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.LocationPortType;
import com.cezarykluczynski.stapi.server.location.reader.LocationSoapReader;
import org.springframework.stereotype.Service;

import javax.jws.WebParam;

@Service
public class LocationSoapEndpoint implements LocationPortType {

	public static final String ADDRESS = "/v1/soap/location";

	private final LocationSoapReader seriesSoapReader;

	public LocationSoapEndpoint(LocationSoapReader seriesSoapReader) {
		this.seriesSoapReader = seriesSoapReader;
	}

	@Override
	public LocationBaseResponse getLocationBase(@WebParam(partName = "request", name = "LocationBaseRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/location") LocationBaseRequest request) {
		return seriesSoapReader.readBase(request);
	}

	@Override
	public LocationFullResponse getLocationFull(@WebParam(partName = "request", name = "LocationFullRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/location") LocationFullRequest request) {
		return seriesSoapReader.readFull(request);
	}

}
