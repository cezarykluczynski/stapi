package com.cezarykluczynski.stapi.server.astronomical_object.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectPortType;
import com.cezarykluczynski.stapi.server.astronomical_object.reader.AstronomicalObjectSoapReader;
import org.springframework.stereotype.Service;

import javax.jws.WebParam;

@Service
public class AstronomicalObjectSoapEndpoint implements AstronomicalObjectPortType {

	public static final String ADDRESS = "/v1/soap/astronomicalObject";

	private final AstronomicalObjectSoapReader seriesSoapReader;

	public AstronomicalObjectSoapEndpoint(AstronomicalObjectSoapReader seriesSoapReader) {
		this.seriesSoapReader = seriesSoapReader;
	}

	@Override
	public AstronomicalObjectBaseResponse getAstronomicalObjectBase(@WebParam(partName = "request", name = "AstronomicalObjectBaseRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/astronomicalObject") AstronomicalObjectBaseRequest request) {
		return seriesSoapReader.readBase(request);
	}

	@Override
	public AstronomicalObjectFullResponse getAstronomicalObjectFull(@WebParam(partName = "request", name = "AstronomicalObjectFullRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/astronomicalObject") AstronomicalObjectFullRequest request) {
		return seriesSoapReader.readFull(request);
	}

}
