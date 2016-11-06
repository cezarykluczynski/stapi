package com.cezarykluczynski.stapi.server.performer.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.PerformerPortType;
import com.cezarykluczynski.stapi.client.v1.soap.PerformerRequest;
import com.cezarykluczynski.stapi.client.v1.soap.PerformerResponse;
import com.cezarykluczynski.stapi.server.performer.reader.PerformerSoapReader;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public class PerformerSoapEndpoint implements PerformerPortType {

	private PerformerSoapReader seriesSoapReader;

	public PerformerSoapEndpoint(PerformerSoapReader seriesSoapReader) {
		this.seriesSoapReader = seriesSoapReader;
	}

	@Override
	public PerformerResponse getPerformers(@WebParam(partName = "request", name = "PerformerRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/performer") PerformerRequest request) {
		return seriesSoapReader.read(request);
	}

}
