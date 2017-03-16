package com.cezarykluczynski.stapi.server.performer.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.PerformerBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.PerformerBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.PerformerFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.PerformerFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.PerformerPortType;
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
	public PerformerBaseResponse getPerformerBase(@WebParam(partName = "request", name = "PerformerBaseRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/performer") PerformerBaseRequest request) {
		return seriesSoapReader.readBase(request);
	}

	@Override
	public PerformerFullResponse getPerformerFull(@WebParam(partName = "request", name = "PerformerFullRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/performer") PerformerFullRequest request) {
		return seriesSoapReader.readFull(request);
	}
}
