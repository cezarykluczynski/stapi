package com.cezarykluczynski.stapi.server.technology.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.TechnologyBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.TechnologyBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.TechnologyFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.TechnologyFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.TechnologyPortType;
import com.cezarykluczynski.stapi.server.technology.reader.TechnologySoapReader;
import org.springframework.stereotype.Service;

import javax.jws.WebParam;

@Service
public class TechnologySoapEndpoint implements TechnologyPortType {

	public static final String ADDRESS = "/v1/soap/technology";

	private final TechnologySoapReader seriesSoapReader;

	public TechnologySoapEndpoint(TechnologySoapReader seriesSoapReader) {
		this.seriesSoapReader = seriesSoapReader;
	}

	@Override
	public TechnologyBaseResponse getTechnologyBase(@WebParam(partName = "request", name = "TechnologyBaseRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/technology") TechnologyBaseRequest request) {
		return seriesSoapReader.readBase(request);
	}

	@Override
	public TechnologyFullResponse getTechnologyFull(@WebParam(partName = "request", name = "TechnologyFullRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/technology") TechnologyFullRequest request) {
		return seriesSoapReader.readFull(request);
	}

}
