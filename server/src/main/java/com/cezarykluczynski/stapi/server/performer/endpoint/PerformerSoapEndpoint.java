package com.cezarykluczynski.stapi.server.performer.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.PerformerBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.PerformerBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.PerformerFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.PerformerFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.PerformerPortType;
import com.cezarykluczynski.stapi.server.performer.reader.PerformerSoapReader;
import org.springframework.stereotype.Service;

import javax.jws.WebParam;

@Service
public class PerformerSoapEndpoint implements PerformerPortType {

	public static final String ADDRESS = "/v1/soap/performer";

	private final PerformerSoapReader performerSoapReader;

	public PerformerSoapEndpoint(PerformerSoapReader performerSoapReader) {
		this.performerSoapReader = performerSoapReader;
	}

	@Override
	public PerformerBaseResponse getPerformerBase(@WebParam(partName = "request", name = "PerformerBaseRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/performer") PerformerBaseRequest request) {
		return performerSoapReader.readBase(request);
	}

	@Override
	public PerformerFullResponse getPerformerFull(@WebParam(partName = "request", name = "PerformerFullRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/performer") PerformerFullRequest request) {
		return performerSoapReader.readFull(request);
	}

}
