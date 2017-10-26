package com.cezarykluczynski.stapi.server.literature.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.LiteratureBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.LiteratureBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.LiteratureFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.LiteratureFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.LiteraturePortType;
import com.cezarykluczynski.stapi.server.literature.reader.LiteratureSoapReader;
import org.springframework.stereotype.Service;

import javax.jws.WebParam;

@Service
public class LiteratureSoapEndpoint implements LiteraturePortType {

	public static final String ADDRESS = "/v1/soap/literature";

	private final LiteratureSoapReader seriesSoapReader;

	public LiteratureSoapEndpoint(LiteratureSoapReader seriesSoapReader) {
		this.seriesSoapReader = seriesSoapReader;
	}

	@Override
	public LiteratureBaseResponse getLiteratureBase(@WebParam(partName = "request", name = "LiteratureBaseRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/literature") LiteratureBaseRequest request) {
		return seriesSoapReader.readBase(request);
	}

	@Override
	public LiteratureFullResponse getLiteratureFull(@WebParam(partName = "request", name = "LiteratureFullRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/literature") LiteratureFullRequest request) {
		return seriesSoapReader.readFull(request);
	}

}
