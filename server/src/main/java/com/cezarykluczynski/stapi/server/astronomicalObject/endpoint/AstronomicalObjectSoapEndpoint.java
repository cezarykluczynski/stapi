package com.cezarykluczynski.stapi.server.astronomicalObject.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectPortType;
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectRequest;
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectResponse;
import com.cezarykluczynski.stapi.server.astronomicalObject.reader.AstronomicalObjectSoapReader;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public class AstronomicalObjectSoapEndpoint implements AstronomicalObjectPortType {

	private AstronomicalObjectSoapReader seriesSoapReader;

	public AstronomicalObjectSoapEndpoint(AstronomicalObjectSoapReader seriesSoapReader) {
		this.seriesSoapReader = seriesSoapReader;
	}

	@Override
	public AstronomicalObjectResponse getAstronomicalObjects(@WebParam(partName = "request", name = "AstronomicalObjectRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/astronomicalObject") AstronomicalObjectRequest request) {
		return seriesSoapReader.read(request);
	}

}
