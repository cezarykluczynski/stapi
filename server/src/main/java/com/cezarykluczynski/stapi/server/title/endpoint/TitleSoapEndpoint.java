package com.cezarykluczynski.stapi.server.title.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.TitleBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.TitleBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.TitleFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.TitleFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.TitlePortType;
import com.cezarykluczynski.stapi.server.title.reader.TitleSoapReader;
import org.springframework.stereotype.Service;

import javax.jws.WebParam;

@Service
public class TitleSoapEndpoint implements TitlePortType {

	public static final String ADDRESS = "/v1/soap/title";

	private final TitleSoapReader seriesSoapReader;

	public TitleSoapEndpoint(TitleSoapReader seriesSoapReader) {
		this.seriesSoapReader = seriesSoapReader;
	}

	@Override
	public TitleBaseResponse getTitleBase(@WebParam(partName = "request", name = "TitleBaseRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/title") TitleBaseRequest request) {
		return seriesSoapReader.readBase(request);
	}

	@Override
	public TitleFullResponse getTitleFull(@WebParam(partName = "request", name = "TitleFullRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/title") TitleFullRequest request) {
		return seriesSoapReader.readFull(request);
	}

}
