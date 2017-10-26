package com.cezarykluczynski.stapi.server.animal.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.AnimalBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.AnimalBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.AnimalFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.AnimalFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.AnimalPortType;
import com.cezarykluczynski.stapi.server.animal.reader.AnimalSoapReader;
import org.springframework.stereotype.Service;

import javax.jws.WebParam;

@Service
public class AnimalSoapEndpoint implements AnimalPortType {

	public static final String ADDRESS = "/v1/soap/animal";

	private final AnimalSoapReader seriesSoapReader;

	public AnimalSoapEndpoint(AnimalSoapReader seriesSoapReader) {
		this.seriesSoapReader = seriesSoapReader;
	}

	@Override
	public AnimalBaseResponse getAnimalBase(@WebParam(partName = "request", name = "AnimalBaseRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/animal") AnimalBaseRequest request) {
		return seriesSoapReader.readBase(request);
	}

	@Override
	public AnimalFullResponse getAnimalFull(@WebParam(partName = "request", name = "AnimalFullRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/animal") AnimalFullRequest request) {
		return seriesSoapReader.readFull(request);
	}

}
