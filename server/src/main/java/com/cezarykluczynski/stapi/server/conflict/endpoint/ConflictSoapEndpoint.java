package com.cezarykluczynski.stapi.server.conflict.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.ConflictBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ConflictBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ConflictFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ConflictFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ConflictPortType;
import com.cezarykluczynski.stapi.server.conflict.reader.ConflictSoapReader;
import org.springframework.stereotype.Service;

import javax.jws.WebParam;

@Service
public class ConflictSoapEndpoint implements ConflictPortType {

	public static final String ADDRESS = "/v1/soap/conflict";

	private final ConflictSoapReader conflictSoapReader;

	public ConflictSoapEndpoint(ConflictSoapReader conflictSoapReader) {
		this.conflictSoapReader = conflictSoapReader;
	}

	@Override
	public ConflictBaseResponse getConflictBase(@WebParam(partName = "request", name = "ConflictBaseRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/conflict") ConflictBaseRequest request) {
		return conflictSoapReader.readBase(request);
	}

	@Override
	public ConflictFullResponse getConflictFull(@WebParam(partName = "request", name = "ConflictFullRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/conflict") ConflictFullRequest request) {
		return conflictSoapReader.readFull(request);
	}

}
