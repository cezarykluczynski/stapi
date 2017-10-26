package com.cezarykluczynski.stapi.server.season.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.SeasonBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SeasonBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.SeasonFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SeasonFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.SeasonPortType;
import com.cezarykluczynski.stapi.server.season.reader.SeasonSoapReader;
import org.springframework.stereotype.Service;

import javax.jws.WebParam;

@Service
public class SeasonSoapEndpoint implements SeasonPortType {

	public static final String ADDRESS = "/v1/soap/season";

	private final SeasonSoapReader seriesSoapReader;

	public SeasonSoapEndpoint(SeasonSoapReader seriesSoapReader) {
		this.seriesSoapReader = seriesSoapReader;
	}

	@Override
	public SeasonBaseResponse getSeasonBase(@WebParam(partName = "request", name = "SeasonBaseRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/season") SeasonBaseRequest request) {
		return seriesSoapReader.readBase(request);
	}

	@Override
	public SeasonFullResponse getSeasonFull(@WebParam(partName = "request", name = "SeasonFullRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/season") SeasonFullRequest request) {
		return seriesSoapReader.readFull(request);
	}

}
